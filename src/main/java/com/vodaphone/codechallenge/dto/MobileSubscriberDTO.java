package com.vodaphone.codechallenge.dto;

import com.vodaphone.codechallenge.model.ServiceType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Mobile Subscriber<br>")
public class MobileSubscriberDTO extends PlanDTO {

  @ApiModelProperty(value = "Unique Entity ID<br>")
  private Integer id;
  
  @ApiModelProperty(value = "The mobile number<br>", notes = "Must be in E164 format", 
      required = true, example = "35699123456")
  private String msisdn;
  
  @ApiModelProperty(value = "The time this mobile number was created, encoded in Unix Epoch in Milliseconds", 
      required = true)
  private long serviceStartDate;
  
  public MobileSubscriberDTO() {
    super();
  }
  
  public MobileSubscriberDTO(String msisdn, Integer customerIdOwner, Integer customerIdUser, ServiceType serviceType) {
    super(customerIdOwner, customerIdUser, serviceType);
    this.msisdn = msisdn;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMsisdn() {
    return msisdn;
  }

  public void setMsisdn(String msisdn) {
    this.msisdn = msisdn;
  }

  public long getServiceStartDate() {
    return serviceStartDate;
  }

  public void setServiceStartDate(long serviceStartDate) {
    this.serviceStartDate = serviceStartDate;
  }

  @Override
  public String toString() {
    return "MobileSubscriberDTO [id=" + id + ", msisdn=" + msisdn + ", customerIdOwner=" + getCustomerIdOwner()
        + ", customerIdUser=" + getCustomerIdUser() + ", serviceType=" + getServiceType() + ", serviceStartDate="
        + serviceStartDate + "]";
  }
}
