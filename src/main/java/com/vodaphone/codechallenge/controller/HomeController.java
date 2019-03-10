package com.vodaphone.codechallenge.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class HomeController {
  
  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @Autowired
  private MobileSubscriberService mobileSubscriberService;
  
  @ApiOperation(value = "Return all mobile numbers from the database", nickname = "getAll")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = MobileSubscriber.class, responseContainer = "List")
  })
  @GetMapping("/subscribers")
  public List<MobileSubscriber> getAll() {
    return mobileSubscriberService.getAllMobileSubscribers();
  }
  
  @ApiOperation(value = "Return all mobile numbers that match the search number criteria", nickname = "getByNumber")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = MobileSubscriber.class),
      @ApiResponse(code = 404, message = "Not Found")
  })
  @GetMapping("/subscribers/{msisdn}")
  public MobileSubscriber getByNumber(
      @ApiParam(value = "The mobile number in E164", example = "35699123456") @PathVariable String msisdn) {
    
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("msisdn - %s", msisdn));
    }
    
    try {
      return mobileSubscriberService.getMobileSubscriberByNumber(msisdn);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, e.getMessage(), e);
    }
  }

  @ApiOperation(value = "Return all mobile numbers that match the onwer id criteria", nickname = "getByCustomerIdOwner")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = MobileSubscriber.class, responseContainer = "List")
  })
  @GetMapping("/subscribers/owner/{customerIdOwner}")
  public List<MobileSubscriber> getByCustomerIdOwner(
      @ApiParam(value = "The ID referencing the owner of this mobile number") @PathVariable Integer customerIdOwner) {
    
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("customerIdOwner - %d", customerIdOwner));
    }
    
    return mobileSubscriberService.getMobileSubscriberByCustomerIdOwner(customerIdOwner);
  }
  
  @ApiOperation(value = "Return all mobile numbers that match the user id criteria", nickname = "getByCustomerIdUser")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = MobileSubscriber.class, responseContainer = "List")
  })
  @GetMapping("/subscribers/user/{customerIdUser}")
  public List<MobileSubscriber> getByCustomerIdUser(
      @ApiParam(value = "The ID referencing the user of this mobile number") @PathVariable Integer customerIdUser) {
    
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("customerIdUser - %d", customerIdUser));
    }
    
    return mobileSubscriberService.getMobileSubscriberByCustomerIdUser(customerIdUser);
  }
  
  @ApiOperation(value = "Change a mobile number plan from prepaid to postpaid or vice versa", nickname = "changePlan")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = MobileSubscriber.class),
      @ApiResponse(code = 404, message = "Not Found")
  })
  @PutMapping("/subscribers/{msisdn}/plan")
  public MobileSubscriber changePlan(
      @ApiParam(value = "The mobile number in E164", example = "35699123456") @PathVariable String msisdn) {
    
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("msisdn - %s", msisdn));
    }
    
    try {
      return mobileSubscriberService.changeMobileSubscriberPlan(msisdn);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, e.getMessage(), e);
    }
  }
  
  @ApiOperation(value = "Delete a mobile number from the database", nickname = "deleteNumber")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = MobileSubscriber.class),
      @ApiResponse(code = 404, message = "Not Found")
  })
  @DeleteMapping("/subscribers/{msisdn}")
  public void deleteNumber(
      @ApiParam(value = "The mobile number in E164", example = "35699123456") @PathVariable String msisdn) {
    
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("msisdn - %s", msisdn));
    }
    
    try {
      mobileSubscriberService.deleteByNumber(msisdn);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, e.getMessage(), e);
    }
  }
  
  @ApiOperation(value = "Add a mobile number to the database", nickname = "createMobileSubscriber")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Created", response = MobileSubscriber.class, responseContainer = "ResponseEntity"),
      @ApiResponse(code = 409, message = "Conflict")
  })
  @PostMapping("/subscribers")
  public ResponseEntity<MobileSubscriber> createMobileSubscriber(
      @ApiParam(value = "Mobile subscriber") @RequestBody MobileSubscriber mobileSubscriber) {
    
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("mobileSubscriber - %s", mobileSubscriber.toString()));
    }
    
    try {
      MobileSubscriber mobileSubscriberSaved = mobileSubscriberService.createMobileSubscriber(mobileSubscriber);
      return new ResponseEntity<>(mobileSubscriberSaved, HttpStatus.CREATED);
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, e.getMessage(), e);
    }
  }
}
