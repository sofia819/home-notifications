package com.sofia819.home.notifications;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component
public interface ManagerComponent {

  AlertManager alertManager();

  DoorManager doorMAnager();
}
