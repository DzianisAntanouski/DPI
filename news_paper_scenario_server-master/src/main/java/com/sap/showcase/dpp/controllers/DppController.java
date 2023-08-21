package com.sap.showcase.dpp.controllers;

import com.sap.showcase.common.security.auditlog.AuditLoggingServiceImpl;
import com.sap.showcase.dpp.model.*;
import com.sap.showcase.dpp.service.DppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

import static com.sap.showcase.dpp.controllers.ControllerUtil.*;

@RestController
public class DppController {
    private static final Logger log = LoggerFactory.getLogger(AuditLoggingServiceImpl.class);

    @Autowired
    private DppService dppService;

    @GetMapping(DPP_METADATA_PATH)
    public ResponseEntity getDppMetadata() throws IOException {
        log.info("DPP Controller Request: Metadata");
        return ResponseEntity.ok(dppService.getMetadata());
    }

    @GetMapping(DPP_CUSTOMERS_PATH)
    public ResponseEntity getCustomers(@RequestParam Map<String, String> searchParameter) {
        log.info("DPP Controller Request: Customers, searchParameter: {}", searchParameter);
        return ResponseEntity.ok(dppService.getCustomers(searchParameter));
    }

    @GetMapping(DPP_SPECIFIC_CUSTOMERS_PATH)
    public ResponseEntity getCustomerByNumber(@PathVariable("customerNumber") String customerNumber) {
        log.info("DPP Controller Request: Customer: {}", customerNumber);
        return ResponseEntity.ok(dppService.getCustomerByNumber((long)Double.parseDouble(customerNumber)));
    }

    @GetMapping(DPP_SUBSCRIPTIONS_PATH)
    public ResponseEntity getSubscriptions(@RequestParam(defaultValue = "") String customerID) {
        log.info("DPP Controller Request: Subscriptions for customerID: {}", customerID);
        if(customerID.isEmpty()) {
            return ResponseEntity.ok(dppService.getSubscriptions());
        } else {
            return ResponseEntity.ok(dppService.getSubscriptionsForCustomerNumber((long)Double.parseDouble(customerID)));
        }
    }

    @GetMapping(DPP_ORDERS_PATH)
    public ResponseEntity getOrders(@RequestParam(defaultValue = "") String customerID) {
        log.info("DPP Controller Request: Orders for customerID: {}", customerID);
        if(customerID.isEmpty()) {
            return ResponseEntity.ok(dppService.getOrders());
        } else {
            return ResponseEntity.ok(dppService.getOrdersForCustomerNumber((long)Double.parseDouble(customerID)));
        }
    }

    @PostMapping(DPP_RM_DELETE_DATA_SUBJECT)
    public ResponseEntity getDeleteDataSubject(@RequestBody DeleteDataSubjectRequest request) {
        dppService.getDeleteDataSubject(request);
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping(DPP_RM_LEGAL_ENTITY_VALUE_HELP)
    public ResponseEntity getLegaEntityValueHelp(@PathVariable("dataSubjectRole") String dataSubjectRole ) {
        return ResponseEntity.ok(dppService.getLegalEntityByDataSubjectRole(dataSubjectRole));
    }
    
    


    @PostMapping(DPP_RM_DATA_SUBSCRIPTION_SUBJECT_END_OF_BUSINESS)
    public ResponseEntity getDataSubjectEndOfBusinessSubscriptions(@RequestBody EOBRequest eobRequest) {
        return ResponseEntity.ok(dppService.getDataSubjectEndOfBusinessSubscriptions(eobRequest));
    }

    @PostMapping(DPP_RM_DATA_SUBSCRIPTION_SUBJECTS_END_OF_RESIDENCE)
    public ResponseEntity getEndOfResidenceDataSubjectsSubscriptions(@RequestBody EORDataSubjectRequest eorRequest) {
        return ResponseEntity.ok(dppService.getEndOfResidenceDataSubjectsSubscriptions(eorRequest));
    }

    @PostMapping(DPP_RM_DATA_SUBSCRIPTION_SUBJECT_LEGAL_ENTITIES)
    public ResponseEntity getDataSubjectLegalEntitiesSubscriptions(@RequestBody LegalEntitiesRequest request) {
        return ResponseEntity.ok(dppService.getDataSubjectLegalEntitiesSubscriptions(request));
    }

    @PostMapping(DPP_RM_DATA_SUBSCRIPTION_SUBJECTS_END_OF_RESIDENCE_CONFIRMATION)
    public ResponseEntity dataSubjectsEndOfResidenceConfirmationSubscriptions(@RequestBody EORDataSubjectConfirmationRequest eorConfirmationRequest) {
        return ResponseEntity.ok(dppService.dataSubjectsEndOfResidenceConfirmationSubscriptions(eorConfirmationRequest));
    }

    @PostMapping(DPP_RM_DATA_SUBSCRIPTION_SUBJECT_LEGAL_GROUND_DELETION)
    public ResponseEntity deleteDataSubjectAtLegalGroundSubscriptions(@RequestBody LegalGroundDeletionRequest request) {
        dppService.deleteDataSubjectAtLegalGroundSubscriptions(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(DPP_RM_DATA_SUBSCRIPTION_SUBJECT_LAST_RETENTION_START_DATES)
    public ResponseEntity getDataSubjectRetentionStartDateSubscriptions(@RequestBody RetentionStartDateRequest request) {
        return ResponseEntity.ok(dppService.getDataSubjectRetentionStartDateSubscriptions(request));
    }

    @PostMapping(DPP_RM_DATA_ORDER_SUBJECT_END_OF_BUSINESS)
    public ResponseEntity getDataSubjectEndOfBusinessOrders(@RequestBody EOBRequest eobRequest) {
        return ResponseEntity.ok(dppService.getDataSubjectEndOfBusinessOrders(eobRequest));
    }

    @PostMapping(DPP_RM_DATA_ORDER_SUBJECTS_END_OF_RESIDENCE)
    public ResponseEntity getEndOfResidenceDataSubjectsOrders(@RequestBody EORDataSubjectRequest eorRequest) {
        return ResponseEntity.ok(dppService.getEndOfResidenceDataSubjectsOrders(eorRequest));
    }

    @PostMapping(DPP_RM_DATA_ORDER_SUBJECT_LEGAL_ENTITIES)
    public ResponseEntity getDataSubjectLegalEntitiesOrders(@RequestBody LegalEntitiesRequest request) {
        return ResponseEntity.ok(dppService.getDataSubjectLegalEntitiesOrders(request));
    }

    @PostMapping(DPP_RM_DATA_ORDER_SUBJECTS_END_OF_RESIDENCE_CONFIRMATION)
    public ResponseEntity dataSubjectsEndOfResidenceConfirmationOrders(@RequestBody EORDataSubjectConfirmationRequest eorConfirmationRequest) {
        return ResponseEntity.ok(dppService.dataSubjectsEndOfResidenceConfirmationOrders(eorConfirmationRequest));
    }

    @PostMapping(DPP_RM_DATA_ORDER_SUBJECT_LEGAL_GROUND_DELETION)
    public ResponseEntity deleteDataSubjectAtLegalGroundOrders(@RequestBody LegalGroundDeletionRequest request) {
        dppService.deleteDataSubjectAtLegalGroundOrders(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(DPP_RM_DATA_ORDER_SUBJECT_LAST_RETENTION_START_DATES)
    public ResponseEntity getDataSubjectRetentionStartDateOrders(@RequestBody RetentionStartDateRequest request) {
        return ResponseEntity.ok(dppService.getDataSubjectRetentionStartDateOrders(request));
    }
}
