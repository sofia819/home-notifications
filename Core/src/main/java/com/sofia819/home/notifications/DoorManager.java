package com.sofia819.home.notifications;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class DoorManager {

  private static final Logger LOG = LoggerFactory.getLogger(DoorManager.class);

  private Optional<Instant> doorOpenedSince;
  private boolean isDoorOpened;
  private AlertManager alertManager;
  private Clock clock;

  DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("America/New_York"));

  @Inject
  public DoorManager(AlertManager alertManager) {
    this.doorOpenedSince = Optional.empty();
    this.isDoorOpened = false;
    this.clock = Clock.systemUTC();
    this.alertManager = alertManager;
  }

  public void updateIsDoorOpened(boolean isDoorOpened, boolean overrideTimeframe) {
    this.isDoorOpened = isDoorOpened;
    LOG.info("Door is {}", isDoorOpened ? "OPENED" : "CLOSED");

    // Door is closed
    if (!this.isDoorOpened) {
      alertManager.resetAlertStatus(overrideTimeframe);
      doorOpenedSince = Optional.empty();
      return;
    }

    // Door was closed, but now opened, update timestamp
    if (doorOpenedSince.isEmpty()) {
      doorOpenedSince = Optional.of(clock.instant());
      return;
    }

    // Check how long the door has been opened
    Instant currentTime = clock.instant();
    Duration timeBetween = Duration.between(doorOpenedSince.get(), currentTime);
    if (timeBetween.getSeconds() > Long.parseLong(System.getenv("DOOR_TIME_LIMIT"))) {
      alertManager.sendAlert(overrideTimeframe);
    }
  }

  public boolean getIsDoorOpened() {
    return isDoorOpened;
  }

  public String getDoorOpenedSince() {
    return doorOpenedSince.isEmpty() ? "" : formatter.format(doorOpenedSince.get());
  }
}
