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
public final class DoorManager_Factory implements Factory<DoorManager> {
  @Override
  public DoorManager get() {
    return newInstance();
  }

  public static DoorManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DoorManager newInstance() {
    return new DoorManager();
  }

  private static final class InstanceHolder {
    private static final DoorManager_Factory INSTANCE = new DoorManager_Factory();
  }
}
