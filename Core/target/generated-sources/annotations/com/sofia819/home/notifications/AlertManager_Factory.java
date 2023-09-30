package com.sofia819.home.notifications;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AlertManager_Factory implements Factory<AlertManager> {
  @Override
  public AlertManager get() {
    return newInstance();
  }

  public static AlertManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AlertManager newInstance() {
    return new AlertManager();
  }

  private static final class InstanceHolder {
    private static final AlertManager_Factory INSTANCE = new AlertManager_Factory();
  }
}
