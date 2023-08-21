package com.sap.showcase.dpp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.showcase.dpp.model.*;
import com.sap.showcase.media.model.Customer;
import com.sap.showcase.media.model.Orders;
import com.sap.showcase.media.model.Subscription;
import com.sap.showcase.media.service.CustomerRepository;
import com.sap.showcase.media.service.OrdersRepository;
import com.sap.showcase.media.service.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

import static com.sap.showcase.dpp.mapping.OrderMapper.mapToDppOrder;
import static com.sap.showcase.dpp.mapping.SubscriptionMapper.mapToDppSubscription;

@Service
public class DppService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private OrdersRepository orderRepository;

    private String datePattern = "yyyy-MM-dd'T'HH:mm:ss";
    private DateTimeFormatter dateTimeFormatter;

    @PostConstruct
    private synchronized void configureDateFormatter() {
        dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern(datePattern)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 23)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 59)
                .parseDefaulting(ChronoField.NANO_OF_SECOND, 999_999_999)
                .toFormatter();
    }

    public String getMetadata() throws IOException {
        Map<String, Object> metadata = getJsonFromFileAsMap("dpp/pdmMetadata.json");
        return new ObjectMapper().writeValueAsString(metadata);
    }

    public List<Customer> getCustomers(Map<String, String> searchParameter) {
        List<Customer> customers = new ArrayList<>();

        if (searchParameter.isEmpty()) {
            customerRepository.findByIsBlockedOrIsBlockedIsNull(false).forEach(customers::add);
        } else {
            String customerNumber = searchParameter.get("customerID");
            String firstName = searchParameter.get("firstName");
            String lastName = searchParameter.get("lastName");
            String email = searchParameter.get("email");

            if (customerNumber != null) {
        		Customer customer = customerRepository.findById((long) Double.parseDouble(customerNumber)).get();
        		if(customer!=null && !customer.isBlocked())
        			customers.add(customer);
            }
            if (firstName != null) {
                customers.addAll(customerRepository.findByFirstName(firstName));
            }
            if (lastName != null) {
                customers.addAll(customerRepository.findByLastName(lastName));
            }
            if (email != null) {
                customers.addAll(customerRepository.findByEmail(email));
            }

            customers = customers.stream().distinct().collect(Collectors.toList());
        }

        return customers;
    }
    
    /**
     * 
     * @param customerNumber
     * @return
     */

    public Customer getCustomerByNumber(long customerNumber) {
		Customer customer = customerRepository.findById(customerNumber).get();
		if(!customer.isBlocked())
			return customer;
		else
			return null;

    }

    public List<DppSubscription> getSubscriptionsForCustomerNumber(Long customerNumber) {
        return mapToDppSubscription(subscriptionRepository.findByCustomerCustomerID(customerNumber));
    }

    public List<DppSubscription> getSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptionRepository.findByIsBlockedOrIsBlockedIsNull(false).forEach(subscriptions::add);
        return mapToDppSubscription(subscriptions);
    }

    public List<DppOrder> getOrdersForCustomerNumber(Long customerNumber) {
        return mapToDppOrder(orderRepository.findByCustomerCustomerID(customerNumber));
    }

    public List<DppOrder> getOrders() {
        List<Orders> orders = new ArrayList<>();
        orderRepository.findByIsBlockedOrIsBlockedIsNull(false).forEach(orders::add);
        return mapToDppOrder(orders);
    }

    private static Map getJsonFromFileAsMap(String name) throws IOException {
        Resource resource = new ClassPathResource(name);
        InputStream is = resource.getInputStream();
        return new ObjectMapper().readValue(is, new TypeReference<Map<String, Object>>() {
        });
    }

    @Transactional
    public void getDeleteDataSubject(DeleteDataSubjectRequest request) {
        Instant today = Instant.now();
        Long customerId = Long.parseLong(request.getDataSubjectID());

        Instant maxDeletionDate = LocalDateTime.parse(request.getMaxDeletionDate(), dateTimeFormatter).atZone(ZoneId.of("UTC")).toInstant();

        if (today.compareTo(maxDeletionDate) >= 0) {
            //delete customer
            customerRepository.deleteById(customerId);
        } else {
            //block customer
            Customer customer = customerRepository.findByCustomerID(customerId).get(0);
            customer.setBlocked(true);
            customerRepository.save(customer);
        }

    }

    public EOBResponse getDataSubjectEndOfBusinessSubscriptions(EOBRequest eobRequest) {
        EOBResponse eobResponse = new EOBResponse();
        Long customerId = Long.parseLong(eobRequest.getDataSubjectID());

        List<Subscription> subscriptionsForDataSubject = subscriptionRepository.findByCustomerCustomerID(customerId);
        for (Subscription subscription : subscriptionsForDataSubject) {
            if (subscription.getEndDate().toInstant().isAfter(Instant.now())) {
                eobResponse.setDataSubjectExpired(false);
            }
        }

        if (eobResponse.getDataSubjectExpired() == null) {
            eobResponse.setDataSubjectExpired(true);
        }

        return eobResponse;
    }

    public EORDataSubjectResponse getEndOfResidenceDataSubjectsSubscriptions(EORDataSubjectRequest eorRequest) {
        Set<Customer> deletionCandidates = new HashSet<>();

        for (Customer customer : customerRepository.findByIsBlockedOrIsBlockedIsNull(false)) {
            deletionCandidates.add(customer);
        }

        String sDate = eorRequest.getLegalEntitiesResidenceRules().get(0).getResidenceRules().get(0).getResidenceDate();
        Instant residenceDate = LocalDateTime.parse(sDate, dateTimeFormatter).atZone(ZoneId.of("UTC")).toInstant();

        for (Subscription subscription : subscriptionRepository.findByIsBlockedOrIsBlockedIsNull(false)) {
            if (subscription.getEndDate().toInstant().isAfter(residenceDate)) {
                deletionCandidates.remove(subscription.getCustomer());
            }
        }

        EORDataSubjectResponse eorDataSubjectResponse = new EORDataSubjectResponse();
        eorDataSubjectResponse.setSuccess(deletionCandidates.stream().map(customer -> new DataSubjectId(customer.getCustomerID().toString())).collect(Collectors.toList()));

        return eorDataSubjectResponse;
    }

    public List<LegalEntity> getDataSubjectLegalEntitiesSubscriptions(LegalEntitiesRequest request) {
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setLegalEntity("8711be76-e8f8-47c6-89f4-16510c5dff28");
        return Collections.singletonList(legalEntity);
    }
	
    /**
     * Note the Legal Entity value is hard code . Needs to be changed for deploying to different Subdomain
     * @param dataSubjectRole
     * @return
     */
    public List<LegalEntity> getLegalEntityByDataSubjectRole(String dataSubjectRole) {
    	//Use role when required and fetch tenant in case of multitenants
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setValue("8711be76-e8f8-47c6-89f4-16510c5dff28");        
        legalEntity.setValueDesc("Tenant Dpp-showcase");
        legalEntity.setLegalEntity("Dpp-showcase");
        return Collections.singletonList(legalEntity);
    }

    public Set<DataSubjectId> dataSubjectsEndOfResidenceConfirmationSubscriptions(EORDataSubjectConfirmationRequest eorConfirmationRequest) {
        EORDataSubjectRequest eorRequest = new EORDataSubjectRequest();
        eorRequest.setDataSubjectRole(eorConfirmationRequest.getDataSubjectRole());
        eorRequest.setLegalEntitiesResidenceRules(eorConfirmationRequest.getLegalEntitiesResidenceRules());
        eorRequest.setLegalGround(eorConfirmationRequest.getLegalGround());
        eorRequest.setStartTime(eorConfirmationRequest.getStartTime());

        EORDataSubjectResponse eorDataSubjectResponse = getEndOfResidenceDataSubjectsSubscriptions(eorRequest);
        Collection<DataSubjectId> deletableDataSubjects = eorDataSubjectResponse.getSuccess();

        // pick all relevant data subject ids which are real deletion candidates
        Set<DataSubjectId> set = new HashSet<>();
        for (DataSubjectId dataSubjectId : eorConfirmationRequest.getDataSubjects()) {
            if (deletableDataSubjects.contains(dataSubjectId)) {
                set.add(dataSubjectId);
            }
        }
        return set;
    }

    @Transactional
    public void deleteDataSubjectAtLegalGroundSubscriptions(LegalGroundDeletionRequest request) {
        List<RetentionRule> retentionRules = request.getRetentionRules();
        RetentionRule retentionRule = retentionRules.get(0);
        Instant today = Instant.now();

        Long customerId = Long.parseLong(request.getDataSubjectID());

        List<Subscription> subscriptionsForDataSubject = subscriptionRepository.findByCustomerCustomerID(customerId);
        for (Subscription subscription : subscriptionsForDataSubject) {
            LocalDateTime subscriptionEndData = LocalDateTime.ofInstant(subscription.getEndDate().toInstant(), ZoneId.systemDefault());
            Instant deletionDate = calculateDeletionDate(retentionRule, subscriptionEndData);
            if (today.isAfter(subscription.getEndDate().toInstant())) {
                if (today.compareTo(deletionDate) >= 0) {
                    //delete subscription
                    subscriptionRepository.delete(subscription);
                } else {
                    //block subscription
                    subscription.setBlocked(true);
                    subscriptionRepository.save(subscription);
                }
            }
        }
    }

    private Instant calculateDeletionDate(RetentionRule rule, LocalDateTime deletionDate) {
        RetentionUnit retentionUnit = RetentionUnit.valueOf(rule.getRetentionUnit());
        return deletionDate.plus(rule.getRetentionPeriod(), retentionUnit.getTemporalUnit()).toInstant(ZoneOffset.UTC);
    }

    public List<RetentionStartDate> getDataSubjectRetentionStartDateSubscriptions(RetentionStartDateRequest request) {
        Date maxDate = new Date(0);
        Long customerId = Long.parseLong(request.getDataSubjectID());

        List<Subscription> subscriptionsForDataSubject = subscriptionRepository.findByCustomerCustomerID(customerId);
        for (Subscription subscription : subscriptionsForDataSubject) {
            if (subscription.getEndDate().after(maxDate)) {
                maxDate = subscription.getEndDate();
            }
        }

        RulesConditionSet rulesConditionSet = request.getRulesConditionSet().get(0);
        RetentionStartDate retentionStartDate = new RetentionStartDate();
        retentionStartDate.setRetentionID(rulesConditionSet.getRetentionID());
        String latestEobString = maxDate.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime().format(dateTimeFormatter);
        retentionStartDate.setRetentionStartDate(latestEobString);

        return Collections.singletonList(retentionStartDate);
    }


    public EOBResponse getDataSubjectEndOfBusinessOrders(EOBRequest eobRequest) {
        EOBResponse eobResponse = new EOBResponse();
        Long customerId = Long.parseLong(eobRequest.getDataSubjectID());

        List<Orders> ordersForDataSubject = orderRepository.findByCustomerCustomerID(customerId);
        for (Orders order : ordersForDataSubject) {
            if (order.getCreatedAt().toInstant().isAfter(Instant.now())) {
                eobResponse.setDataSubjectExpired(false);
            }
        }

        if (eobResponse.getDataSubjectExpired() == null) {
            eobResponse.setDataSubjectExpired(true);
        }

        return eobResponse;
    }

    public EORDataSubjectResponse getEndOfResidenceDataSubjectsOrders(EORDataSubjectRequest eorRequest) {
        Set<Customer> deletionCandidates = new HashSet<>();

        for (Customer customer : customerRepository.findByIsBlockedOrIsBlockedIsNull(false)) {
            deletionCandidates.add(customer);
        }

        String sDate = eorRequest.getLegalEntitiesResidenceRules().get(0).getResidenceRules().get(0).getResidenceDate();
        Instant residenceDate = LocalDateTime.parse(sDate, dateTimeFormatter).atZone(ZoneId.of("UTC")).toInstant();

        for (Orders order : orderRepository.findByIsBlockedOrIsBlockedIsNull(false)) {
            if (order.getCreatedAt().toInstant().isAfter(residenceDate)) {
                deletionCandidates.remove(order.getCustomer());
            }
        }

        EORDataSubjectResponse eorDataSubjectResponse = new EORDataSubjectResponse();
        eorDataSubjectResponse.setSuccess(deletionCandidates.stream().map(customer -> new DataSubjectId(customer.getCustomerID().toString())).collect(Collectors.toList()));

        return eorDataSubjectResponse;
    }

    public List<LegalEntity> getDataSubjectLegalEntitiesOrders(LegalEntitiesRequest request) {
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setLegalEntity("8711be76-e8f8-47c6-89f4-16510c5dff28");
        return Collections.singletonList(legalEntity);
    }

    public Set<DataSubjectId> dataSubjectsEndOfResidenceConfirmationOrders(EORDataSubjectConfirmationRequest eorConfirmationRequest) {
        EORDataSubjectRequest eorRequest = new EORDataSubjectRequest();
        eorRequest.setDataSubjectRole(eorConfirmationRequest.getDataSubjectRole());
        eorRequest.setLegalEntitiesResidenceRules(eorConfirmationRequest.getLegalEntitiesResidenceRules());
        eorRequest.setLegalGround(eorConfirmationRequest.getLegalGround());
        eorRequest.setStartTime(eorConfirmationRequest.getStartTime());

        EORDataSubjectResponse eorDataSubjectResponse = getEndOfResidenceDataSubjectsOrders(eorRequest);
        Collection<DataSubjectId> deletableDataSubjects = eorDataSubjectResponse.getSuccess();

        // pick all relevant data subject ids which are real deletion candidates
        Set<DataSubjectId> set = new HashSet<>();
        for (DataSubjectId dataSubjectId : eorConfirmationRequest.getDataSubjects()) {
            if (deletableDataSubjects.contains(dataSubjectId)) {
                set.add(dataSubjectId);
            }
        }
        return set;
    }

    @Transactional
    public void deleteDataSubjectAtLegalGroundOrders(LegalGroundDeletionRequest request) {
        List<RetentionRule> retentionRules = request.getRetentionRules();
        RetentionRule retentionRule = retentionRules.get(0);
        Instant today = Instant.now();

        Long customerId = Long.parseLong(request.getDataSubjectID());

        List<Orders> ordersForDataSubject = orderRepository.findByCustomerCustomerID(customerId);
        for (Orders order : ordersForDataSubject) {
            LocalDateTime orderEndData = LocalDateTime.ofInstant(order.getCreatedAt().toInstant(), ZoneId.systemDefault());
            Instant deletionDate = calculateDeletionDate(retentionRule, orderEndData);
            if (today.isAfter(order.getCreatedAt().toInstant())) {
                if (today.compareTo(deletionDate) >= 0) {
                    //delete order
                    orderRepository.delete(order);
                } else {
                    //block order
                    order.setBlocked(true);
                    orderRepository.save(order);
                }
            }
        }
    }

    public List<RetentionStartDate> getDataSubjectRetentionStartDateOrders(RetentionStartDateRequest request) {
        Date maxDate = new Date(0);
        Long customerId = Long.parseLong(request.getDataSubjectID());

        List<Orders> ordersForDataSubject = orderRepository.findByCustomerCustomerID(customerId);
        for (Orders order : ordersForDataSubject) {
            if (order.getCreatedAt().after(maxDate)) {
                maxDate = order.getCreatedAt();
            }
        }

        RulesConditionSet rulesConditionSet = request.getRulesConditionSet().get(0);
        RetentionStartDate retentionStartDate = new RetentionStartDate();
        retentionStartDate.setRetentionID(rulesConditionSet.getRetentionID());
        String latestEobString = maxDate.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime().format(dateTimeFormatter);
        retentionStartDate.setRetentionStartDate(latestEobString);

        return Collections.singletonList(retentionStartDate);
    }
}
