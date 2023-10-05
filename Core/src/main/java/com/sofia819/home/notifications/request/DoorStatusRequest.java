package com.sofia819.home.notifications.request;

public record DoorStatusRequest(boolean isDoorOpened, boolean overrideTimeframe) {
  public DoorStatusRequest(boolean isDoorOpened) {
    this(isDoorOpened, false);
  }
}
