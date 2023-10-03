package com.sofia819.home.notifications;

import com.sofia819.home.notifications.response.AlertMessageResponse;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AlertManager {

  private static final Logger LOG = LoggerFactory.getLogger(AlertManager.class);

  private Instant lastMessageSentTime;
  private boolean alertEnabled;

  @Inject
  public AlertManager() {
    this.alertEnabled = false;
  }

  public void toggleAlertEnabled() {
    lastMessageSentTime = Instant.MIN;
    alertEnabled = !alertEnabled;
    LOG.info("Alert is {}", alertEnabled ? "ENABLED" : "DISABLED");
  }

  public boolean shouldSendAlert() {
    if (!alertEnabled) {
      return false;
    }

    LocalTime now = LocalTime.now();
    int startHour = Integer.parseInt(System.getenv("START_HOUR"));
    int endHour = Integer.parseInt(System.getenv("END_HOUR"));
    return now.getHour() >= startHour && now.getHour() <= endHour;
  }

  public AlertMessageResponse sendTextAlert() {
    if (!shouldSendAlert()) {
      LOG.info("Alert is not enabled");
      return new AlertMessageResponse(false, Set.of());
    }

    Instant currentTime = Instant.now();
    if (Duration.between(lastMessageSentTime, currentTime).getSeconds()
        < Long.parseLong(System.getenv("DOOR_TIME_LIMIT"))) {
      LOG.info("Not enough time has passed since last message");
      return new AlertMessageResponse(false, Set.of());
    }

    LOG.info("Attempting to send alert");
    Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
    String sender = System.getenv("TWILIO_SENDER");
    String[] recipients = System.getenv("TWILIO_RECIPIENTS").split("\\+\\d{11}");

    Set<String> messagesSent = new HashSet<>();
    for (String recipient : recipients) {
      if (recipient.length() > 0) {
        Message message =
            Message.creator(
                    new com.twilio.type.PhoneNumber(recipient),
                    new com.twilio.type.PhoneNumber(sender),
                    "Close the door!")
                .create();
        messagesSent.add(message.getSid());
      }
    }

    LOG.info("Messages {} sent", messagesSent);
    lastMessageSentTime = currentTime;
    return new AlertMessageResponse(true, messagesSent);
  }
}
