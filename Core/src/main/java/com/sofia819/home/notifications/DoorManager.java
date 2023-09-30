package com.sofia819.home.notifications;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DoorManager {

  private boolean isDoorOpened;

  @Inject
  public DoorManager() {
    this.isDoorOpened = false;
  }

  public void updateIsDoorOpened(boolean isDoorOpened) {
    this.isDoorOpened = isDoorOpened;
  }

  public boolean getIsDoorOpened() {
    return isDoorOpened;
  }
}
