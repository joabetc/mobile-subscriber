package com.vodaphone.codechallenge.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "Mobile Subscriber<br>")
public class MobileSubscriber {

  @Id
  @GeneratedValue
  @Column(name = "Id")
  @ApiModelProperty(value = "Unique Entity ID<br>")
  private Integer id;
  
  @Column(nullable = false, unique = true)
  @ApiModelProperty(value = "The mobile number<br>", notes = "Must be in E164 format", required = true, example = "35699123456")
  private String msisdn;
  
  @Column(name = "customer_id_owner", nullable = false)
  @ApiModelProperty(value = "The ID referencing the owner of the mobile number<br>", required = true)
  private Integer customerIdOwner;
  
  @Column(name = "customer_id_user", nullable = false)
  @ApiModelProperty(value= "The ID referencing the user of the mobile number<br>", required = true)
  private Integer customerIdUser;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "service_type", nullable = false)
  @ApiModelProperty(value = "An enum defining the type of service<br>")
  private ServiceType serviceType;
  
  @Column(name = "service_start_date", nullable = false)
  @ApiModelProperty(value = "The time this mobile number was created, encoded in Unix Epoch in Milliseconds", required = true)
  private long serviceStartDate;
  
  public MobileSubscriber() {
    super();
  }
  
  public MobileSubscriber(String msisdn, Integer customerIdOwner, Integer customerIdUser, ServiceType serviceType) {
    super();
    this.msisdn = msisdn;
    this.customerIdOwner = customerIdOwner;
    this.customerIdUser = customerIdUser;
    this.serviceType = serviceType;
    this.setServiceStartDate(Instant.now().toEpochMilli());
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

  public long getServiceStartDate() {
    return serviceStartDate;
  }

  public void setServiceStartDate(long serviceStartDate) {
    this.serviceStartDate = serviceStartDate;
  }

  @Override
  public String toString() {
    return "MobileSubscriber [id=" + id + ", msisdn=" + msisdn + ", customerIdOwner=" + customerIdOwner
        + ", customerIdUser=" + customerIdUser + ", serviceType=" + serviceType + ", serviceStartDate="
        + serviceStartDate + "]";
  }
}
