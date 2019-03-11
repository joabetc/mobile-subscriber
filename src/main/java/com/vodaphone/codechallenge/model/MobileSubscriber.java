package com.vodaphone.codechallenge.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import springfox.documentation.annotations.ApiIgnore;

@Entity
@ApiIgnore
public class MobileSubscriber {

  @Id
  @GeneratedValue
  @Column(name = "Id")
  private Integer id;
  
  @Column(nullable = false, unique = true)
  private String msisdn;
  
  @Column(name = "customer_id_owner", nullable = false)
  private Integer customerIdOwner;
  
  @Column(name = "customer_id_user", nullable = false)
  private Integer customerIdUser;
  
  @Enumerated(EnumType.STRING)
  @Column(name = "service_type", nullable = false)
  private ServiceType serviceType;
  
  @Column(name = "service_start_date", nullable = false)
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
