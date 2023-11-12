package com.sofia819.home.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.sofia819.home.notifications.request.TextbeltRequest;
import com.sofia819.home.notifications.response.AlertMessageResponse;
import com.sofia819.home.notifications.response.AlertStatusResponse;
import com.sofia819.home.notifications.response.TextbeltResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AlertManager {

  private static final Logger LOG = LoggerFactory.getLogger(AlertManager.class);

  private boolean isAlertEnabled;
  private Optional<Instant> lastMessageSentTime;
  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;
  private final Retryer<TextbeltResponse> retryer;

  @Inject
  public AlertManager() {
    this.isAlertEnabled = false;
    this.lastMessageSentTime = Optional.empty();
    this.httpClient = HttpClients.createMinimal();
    this.objectMapper = new ObjectMapper();

    retryer =
        RetryerBuilder.<TextbeltResponse>newBuilder()
            .retryIfResult(response -> !response.success())
            .retryIfExceptionOfType(Exception.class)
            .withStopStrategy(StopStrategies.stopAfterAttempt(3))
            .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
            .build();
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

    Set<String> messagesSent = sendTextMessages("Close the door");

    return new AlertMessageResponse(true, messagesSent);
  }

  public void resetAlertStatus(boolean overrideTimeframe) {
    if (lastMessageSentTime.isPresent() && shouldSendAlert(overrideTimeframe)) {
      sendTextMessages("Door is closed");
    }
    lastMessageSentTime = Optional.empty();
  }

  private Set<String> sendTextMessages(String content) {
    LOG.info("Attempting to send message with content {}", content);

    String[] recipients = System.getenv("RECIPIENTS").split(",");
    String textbeltApiKey = System.getenv("TEXTBELT_API_KEY");

    Set<String> messagesSent = new HashSet<>();
    for (String recipient : recipients) {
      if (recipient.length() > 0) {
        try {
          TextbeltResponse response =
              retryer.call(
                  () -> sendTextMessage(new TextbeltRequest(recipient, content, textbeltApiKey)));
          messagesSent.add(response.textId());
        } catch (Exception e) {
          LOG.error("Failed to send message for {}", recipient, e);
        }
      }
    }

    LOG.info("Messages {} sent", messagesSent);
    lastMessageSentTime = Optional.of(Instant.now());

    return messagesSent;
  }

  private TextbeltResponse sendTextMessage(TextbeltRequest textbeltRequest) throws IOException {
    HttpPost httpPost = new HttpPost("https://textbelt.com/text");
    httpPost.setEntity(
        new StringEntity(
            objectMapper.writeValueAsString(textbeltRequest), ContentType.APPLICATION_JSON));
    HttpResponse httpResponse = httpClient.execute(httpPost);

    return objectMapper.readValue(
        EntityUtils.toString(httpResponse.getEntity(), Charset.defaultCharset()),
        TextbeltResponse.class);
  }
}
