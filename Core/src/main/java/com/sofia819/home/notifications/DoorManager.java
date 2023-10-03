package com.sofia819.home.notifications;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class DoorManager {

  private static final Logger LOG = LoggerFactory.getLogger(DoorManager.class);

  private Instant doorOpenedSince;
  private boolean isDoorOpened;
  private AlertManager alertManager;
  private Clock clock;

  DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("America/New_York"));

  @Inject
  public DoorManager(AlertManager alertManager) {
    this.doorOpenedSince = Instant.MIN;
    this.isDoorOpened = false;
    this.clock = Clock.systemUTC();
    this.alertManager = alertManager;
  }

  public void updateIsDoorOpened(boolean isDoorOpened) {
    this.isDoorOpened = isDoorOpened;
    LOG.info("Door is {}", isDoorOpened ? "OPENED" : "CLOSED");

    // If the door is closed, nothing to do
    if (!this.isDoorOpened) {
      doorOpenedSince = Instant.MIN;
      return;
    }

    // Door state changed, update timestamp then return
    if (doorOpenedSince == Instant.MIN) {
      doorOpenedSince = clock.instant();
      return;
    }

    // Door has been opened for a while, check how long
    Instant currentTime = clock.instant();
    Duration timeBetween = Duration.between(doorOpenedSince, currentTime);
    if (timeBetween.getSeconds() > Long.parseLong(System.getenv("DOOR_TIME_LIMIT"))) {
      alertManager.sendTextAlert();
    }
  }

  public boolean getIsDoorOpened() {
    return isDoorOpened;
  }

  public String getDoorOpenedSince() {
    return formatter.format(doorOpenedSince);
  }
}
