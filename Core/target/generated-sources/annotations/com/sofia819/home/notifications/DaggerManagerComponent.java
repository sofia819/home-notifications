package com.sofia819.home.notifications;

import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class DaggerManagerComponent {
  private DaggerManagerComponent() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ManagerComponent create() {
    return new Builder().build();
  }

  public static final class Builder {
    private Builder() {
    }

    public ManagerComponent build() {
      return new ManagerComponentImpl();
    }
  }

  private static final class ManagerComponentImpl implements ManagerComponent {
    private final ManagerComponentImpl managerComponentImpl = this;

    private Provider<AlertManager> alertManagerProvider;

    private Provider<DoorManager> doorManagerProvider;

    private ManagerComponentImpl() {

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.alertManagerProvider = DoubleCheck.provider(AlertManager_Factory.create());
      this.doorManagerProvider = DoubleCheck.provider(DoorManager_Factory.create(alertManagerProvider));
    }

    @Override
    public AlertManager alertManager() {
      return alertManagerProvider.get();
    }

    @Override
    public DoorManager doorMAnager() {
      return doorManagerProvider.get();
    }
  }
}
