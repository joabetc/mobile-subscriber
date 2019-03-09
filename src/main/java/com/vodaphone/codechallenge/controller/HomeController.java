package com.vodaphone.codechallenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vodaphone.codechallenge.model.MobileSubscriber;
import com.vodaphone.codechallenge.service.MobileSubscriberService;

@RestController
@RequestMapping("/api")
public class HomeController {

  @Autowired
  private MobileSubscriberService mobileSubscriberService;
  
  @GetMapping("/subscribers")
  public List<MobileSubscriber> getAll() {
    return mobileSubscriberService.getAllMobileSubscribers();
  }
  
  @GetMapping("/subscribers/{msisdn}")
  public MobileSubscriber getByNumber(@PathVariable String msisdn) {
    return mobileSubscriberService.getMobileSubscriberByNumber(msisdn);
  }

  @GetMapping("/subscribers/owner/{customerIdOwner}")
  public List<MobileSubscriber> getByCustomerIdOwner(@PathVariable Integer customerIdOwner) {
    return mobileSubscriberService.getMobileSubscriberByCustomerIdOwner(customerIdOwner);
  }
  
  @GetMapping("/subscribers/user/{customerIdUser}")
  public List<MobileSubscriber> getByCustomerIdUser(@PathVariable Integer customerIdUser) {
    return mobileSubscriberService.getMobileSubscriberByCustomerIdUser(customerIdUser);
  }
}
