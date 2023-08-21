package com.sap.showcase.media.model;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class OrderDTO {
	private Long order_ID;
	public String createdAt;
	private String orderTitle;
	Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public OrderDTO(Long order_ID, String createdAt) {
		super();
		this.order_ID = order_ID;
		this.createdAt = createdAt;
	}
	
	public OrderDTO(Orders orders) {
		super();
		this.order_ID = orders.getOrder_ID();
		this.createdAt = convertToDateTime(orders.getCreatedAt());
		this.orderTitle = orders.getOrderTitle();
		this.customer = orders.getCustomer();
	}

	public OrderDTO(Long order_ID, String createdAt, String orderTitle) {
		super();
		this.order_ID = order_ID;
		this.createdAt = createdAt;
		this.orderTitle = orderTitle;
	}

	public Long getorder_ID() {
		return order_ID;
	}

	public void setorder_ID(Long order_ID) {
		this.order_ID = order_ID;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	private String convertToDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
        return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME); // ISO 8601
    }

}
