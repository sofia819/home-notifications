package com.sofia819.home.notifications.response;

import java.util.Set;

public record AlertMessageResponse(boolean isSent, Set<String> messageSids) {

}
