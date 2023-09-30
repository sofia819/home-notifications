package com.sofia819.home.notifications;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DoorManager_Factory implements Factory<DoorManager> {
  private final Provider<AlertManager> alertManagerProvider;

  public DoorManager_Factory(Provider<AlertManager> alertManagerProvider) {
    this.alertManagerProvider = alertManagerProvider;
  }

  @Override
  public DoorManager get() {
    return newInstance(alertManagerProvider.get());
  }

  public static DoorManager_Factory create(Provider<AlertManager> alertManagerProvider) {
    return new DoorManager_Factory(alertManagerProvider);
  }

  public static DoorManager newInstance(AlertManager alertManager) {
    return new DoorManager(alertManager);
  }
}
