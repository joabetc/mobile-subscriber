package com.vodaphone.codechallenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
