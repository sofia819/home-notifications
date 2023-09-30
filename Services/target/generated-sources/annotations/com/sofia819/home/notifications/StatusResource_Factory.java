package com.sofia819.home.notifications;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class StatusResource_Factory implements Factory<StatusResource> {
  private final Provider<AlertManager> alertManagerProvider;

  private final Provider<DoorManager> doorManagerProvider;

  public StatusResource_Factory(Provider<AlertManager> alertManagerProvider,
      Provider<DoorManager> doorManagerProvider) {
    this.alertManagerProvider = alertManagerProvider;
    this.doorManagerProvider = doorManagerProvider;
  }

  @Override
  public StatusResource get() {
    return newInstance(alertManagerProvider.get(), doorManagerProvider.get());
  }

  public static StatusResource_Factory create(Provider<AlertManager> alertManagerProvider,
      Provider<DoorManager> doorManagerProvider) {
    return new StatusResource_Factory(alertManagerProvider, doorManagerProvider);
  }

  public static StatusResource newInstance(AlertManager alertManager, DoorManager doorManager) {
    return new StatusResource(alertManager, doorManager);
  }
}
