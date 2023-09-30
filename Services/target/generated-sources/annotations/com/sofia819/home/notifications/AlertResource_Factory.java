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
public final class AlertResource_Factory implements Factory<AlertResource> {
  private final Provider<AlertManager> alertManagerProvider;

  public AlertResource_Factory(Provider<AlertManager> alertManagerProvider) {
    this.alertManagerProvider = alertManagerProvider;
  }

  @Override
  public AlertResource get() {
    return newInstance(alertManagerProvider.get());
  }

  public static AlertResource_Factory create(Provider<AlertManager> alertManagerProvider) {
    return new AlertResource_Factory(alertManagerProvider);
  }

  public static AlertResource newInstance(AlertManager alertManager) {
    return new AlertResource(alertManager);
  }
}
