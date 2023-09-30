package com.sofia819.home.notifications;

import com.codahale.metrics.health.HealthCheck;

public class HealthCheckResource extends HealthCheck {

  @Override
  protected Result check() {
    return Result.healthy();
  }

}
