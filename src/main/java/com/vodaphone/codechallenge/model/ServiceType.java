package com.vodaphone.codechallenge.model;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum ServiceType {

  MOBILE_PREPAID, MOBILE_POSTPAID;
  
  public static ServiceType getOpposite(ServiceType serviceType) {
    for (ServiceType s : values()) {
      if (!s.equals(serviceType)) {
        return s;
      }
    }
    return serviceType;
  }
}
