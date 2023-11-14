package org.learning.guide.client;

import jakarta.servlet.http.HttpServletRequest;

public class SessionHelperForTesting extends SessionHelper {
  public SessionHelperForTesting(HttpServletRequest httpServletRequest) {
    super(httpServletRequest);
  }

  @Override
  public String getSessionId() {
    return "sessionIdForTesting";
  }
}
