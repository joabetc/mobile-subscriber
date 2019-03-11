package com.vodaphone.codechallenge.dto;

import com.vodaphone.codechallenge.model.ServiceType;

import io.swagger.annotations.ApiModelProperty;

public class PlanDTO {
  
  @ApiModelProperty(value = "The ID referencing the owner of the mobile number<br>", 
      required = true)
  private Integer customerIdOwner;
  
  @ApiModelProperty(value= "The ID referencing the user of the mobile number<br>", 
      required = true)
  private Integer customerIdUser;
  
  @ApiModelProperty(value = "An enum defining the type of service<br>")
  private ServiceType serviceType;

  public PlanDTO() {
    super();
  }

  public PlanDTO(Integer customerIdOwner, Integer customerIdUser, ServiceType serviceType) {
    super();
    this.customerIdOwner = customerIdOwner;
    this.customerIdUser = customerIdUser;
    this.serviceType = serviceType;
  }

  public Integer getCustomerIdOwner() {
    return customerIdOwner;
  }

  public void setCustomerIdOwner(Integer customerIdOwner) {
    this.customerIdOwner = customerIdOwner;
  }

  public Integer getCustomerIdUser() {
    return customerIdUser;
  }

  public void setCustomerIdUser(Integer customerIdUser) {
    this.customerIdUser = customerIdUser;
  }

  public ServiceType getServiceType() {
    return serviceType;
  }

  public void setServiceType(ServiceType serviceType) {
    this.serviceType = serviceType;
  }

  @Override
  public String toString() {
    return "PlanDTO [customerIdOwner=" + customerIdOwner + ", customerIdUser=" + customerIdUser + ", serviceType="
        + serviceType + "]";
  }
}
