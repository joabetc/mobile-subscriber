package com.vodaphone.codechallenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    try {
      return mobileSubscriberService.getMobileSubscriberByNumber(msisdn);
    } catch (Exception e) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, e.getMessage(), e);
    }
  }

  @GetMapping("/subscribers/owner/{customerIdOwner}")
  public List<MobileSubscriber> getByCustomerIdOwner(@PathVariable Integer customerIdOwner) {
    return mobileSubscriberService.getMobileSubscriberByCustomerIdOwner(customerIdOwner);
  }
  
  @GetMapping("/subscribers/user/{customerIdUser}")
  public List<MobileSubscriber> getByCustomerIdUser(@PathVariable Integer customerIdUser) {
    return mobileSubscriberService.getMobileSubscriberByCustomerIdUser(customerIdUser);
  }
  
  @PutMapping("/subscribers/{msisdn}/plan")
  public MobileSubscriber changePlan(@PathVariable String msisdn) {
    try {
      return mobileSubscriberService.changeMobileSubscriberPlan(msisdn);
    } catch (Exception e) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, e.getMessage(), e);
    }
  }
  
  @DeleteMapping("/subscribers/{msisdn}")
  public void deleteNumber(@PathVariable String msisdn) {
    try {
      mobileSubscriberService.deleteByNumber(msisdn);
    } catch (Exception e) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, e.getMessage(), e);
    }
  }
  
  @PostMapping("/subscribers")
  public ResponseEntity<MobileSubscriber> createMobileSubscriber(@RequestBody MobileSubscriber mobileSubscriber) {
    try {
      MobileSubscriber mobileSubscriberSaved = mobileSubscriberService.createMobileSubscriber(mobileSubscriber);
      return new ResponseEntity<MobileSubscriber>(mobileSubscriberSaved, HttpStatus.CREATED);
    } catch (Exception e) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, e.getMessage(), e);
    }
  }
}
