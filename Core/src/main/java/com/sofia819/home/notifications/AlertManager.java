package com.sofia819.home.notifications;

import com.sofia819.home.notifications.response.AlertMessageResponse;
import com.sofia819.home.notifications.response.AlertStatusResponse;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AlertManager {

  private static final Logger LOG = LoggerFactory.getLogger(AlertManager.class);

  private boolean isAlertEnabled;
  private Optional<Instant> lastMessageSentTime;

  @Inject
  public AlertManager() {
    this.isAlertEnabled = false;
    this.lastMessageSentTime = Optional.empty();
  }

  public void toggleAlertEnabled() {
    isAlertEnabled = !isAlertEnabled;
    LOG.info("Alert is {}", isAlertEnabled ? "ENABLED" : "DISABLED");
  }

  public AlertStatusResponse getAlertStatus() {
    return new AlertStatusResponse(isAlertEnabled, isWithinTimeFrame());
  }

  public boolean shouldSendAlert(boolean overrideTimeframe) {
    return isAlertEnabled && (overrideTimeframe || isWithinTimeFrame());
  }

  private boolean isWithinTimeFrame() {
    LocalTime now = LocalTime.now(ZoneId.of("America/New_York"));
    int startHour = Integer.parseInt(System.getenv("START_HOUR"));
    int endHour = Integer.parseInt(System.getenv("END_HOUR"));

    return now.getHour() >= startHour && now.getHour() <= endHour;
  }

  public AlertMessageResponse sendAlert(boolean overrideTimeframe) {
    if (!shouldSendAlert(overrideTimeframe)) {
      LOG.info("Alert is not enabled");
      return new AlertMessageResponse(false, Set.of());
    }

    if (lastMessageSentTime.isPresent()) {
      LOG.info("A message has already been sent");
      return new AlertMessageResponse(false, Set.of());
    }

    Set<String> messagesSent = sendTextMessage("Close the door");

    return new AlertMessageResponse(true, messagesSent);
  }

  public void resetAlertStatus(boolean overrideTimeframe) {
    if (lastMessageSentTime.isPresent() && shouldSendAlert(overrideTimeframe)) {
      sendTextMessage("Door is closed");
    }
    lastMessageSentTime = Optional.empty();
  }

  private Set<String> sendTextMessage(String content) {
    LOG.info("Attempting to send message with content {}", content);

    Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
    String sender = System.getenv("TWILIO_SENDER");

    String[] recipients = System.getenv("TWILIO_RECIPIENTS").split(",");

    Set<String> messagesSent = new HashSet<>();
    for (String recipient : recipients) {
      if (recipient.length() > 0) {
        Message message =
            Message.creator(
                    new com.twilio.type.PhoneNumber(recipient),
                    new com.twilio.type.PhoneNumber(sender),
                    content)
                .create();
        messagesSent.add(message.getSid());
      }
    }

    LOG.info("Messages {} sent", messagesSent);
    lastMessageSentTime = Optional.of(Instant.now());

    return messagesSent;
  }
}
