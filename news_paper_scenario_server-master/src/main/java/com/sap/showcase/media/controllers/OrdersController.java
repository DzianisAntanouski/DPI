package com.sap.showcase.media.controllers;

import static com.sap.showcase.media.controllers.ControllerConst.API_CUST_PATH;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.showcase.common.security.pii.PersonalDataEvent;
import com.sap.showcase.exception.NotFoundException;
import com.sap.showcase.exception.ResourceNotFoundException;
import com.sap.showcase.media.model.Customer;
import com.sap.showcase.media.model.OrderDTO;
import com.sap.showcase.media.model.Orders;
import com.sap.showcase.media.service.OrdersService;

@RestController
public class OrdersController {	

	@Autowired
	private OrdersService OrdersService;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request)
    {
       binder.setAllowedFields("order_ID","createdAt","orderTitle","customer");
    }

	@RequestMapping(API_CUST_PATH+"/{custId}/orders/")
	public ResponseEntity<OrderListDto> getAllOrders(@PathVariable Long custId) {
		List<Orders> orders = OrdersService.getOrders(custId);
		if(orders.size()==0){
			throw new ResourceNotFoundException("No Orders found for Customer with id:"+custId);
		}else{
			publisher.publishEvent(new PersonalDataEvent("GET", OrdersService.getOrders(custId)));
			publisher.publishEvent(new PersonalDataEvent("MASK", OrdersService.getOrders(custId)));
		}
		return new ResponseEntity<OrderListDto>(new OrderListDto(orders), HttpStatus.OK);			
	}

	@RequestMapping(API_CUST_PATH+"/{custId}/orders/{ordId}")
	public OrderDTO getOrder(@PathVariable Long ordId) {
		Orders order = OrdersService.getOrder(ordId);
		if(order==null ){
			throw new ResourceNotFoundException("No Order exists with Id:"+ordId);
		}else{
			publisher.publishEvent(new PersonalDataEvent("GET", OrdersService.getOrder(ordId)));
			publisher.publishEvent(new PersonalDataEvent("MASK", OrdersService.getOrder(ordId)));
		}		
		return new OrderDTO(order);
	}
	
	@RequestMapping(method=RequestMethod.POST,value=API_CUST_PATH+"/{custId}/orders/")
	public void addOrders (@RequestBody Orders Orders,  @PathVariable Long custId) {
		Customer customer = new Customer();
		customer.setCustomerID(custId);
		Orders.setCustomer(customer);		
		OrdersService.addOrders(Orders);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value=API_CUST_PATH+"/{custId}/orders/{ordId}")
	public void updateOrders (@RequestBody Orders Orders, @PathVariable Long custId, @PathVariable Long ordId) {
		throwIfNonexisting(ordId);
		Customer customer = new Customer();
		customer.setCustomerID(custId);
		Orders.setCustomer(customer);

		publisher.publishEvent(new PersonalDataEvent("PUT_M", Orders, OrdersService.getOrder(ordId)));
		OrdersService.updateOrders(ordId,Orders);
		publisher.publishEvent(new PersonalDataEvent("PUT_S", Orders, OrdersService.getOrder(ordId)));
	}

	@RequestMapping(method=RequestMethod.DELETE,value=API_CUST_PATH+"/{custId}/orders/{ordId}")
	public void deleteOrders (@PathVariable Long custId, @PathVariable Long ordId) {
		throwIfNonexisting(ordId);
		OrdersService.deleteOrders(ordId);		
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value=API_CUST_PATH+"/{custId}/orders")
	public void deleteAllOrders (@PathVariable Long custId) {
//		throwIfNonexisting(custId);
		OrdersService.deleteAllOrdersByCustomerID(custId);		
	}
	
    public static class OrderListDto {
        @JsonProperty("value")
        public List<OrderDTO> order;

        public OrderListDto(Iterable<Orders> orders) {
            this.order = StreamSupport.stream(orders.spliterator(), false).map(OrderDTO::new)
                    .collect(Collectors.toList());
        }
    }
    
    private void throwIfNonexisting(long id) {
	    if (!OrdersService.exists(id)) {
	        NotFoundException notFoundException = new NotFoundException("Order not not found id:"+id);
	        logger.warn("request failed", notFoundException);
	        throw notFoundException;
	    }
	}
}