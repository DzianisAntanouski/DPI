package com.sap.showcase.media.model;

import com.sap.showcase.common.security.pii.PersonalData;
import com.sap.showcase.common.security.pii.PersonalData.LogType;
import com.sap.showcase.common.security.pii.PersonalData.PersInfoType;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@PersonalData(PIType = PersInfoType.SPI, LogType = LogType.Change )
public class Orders{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_ID")
	private Long order_ID;
	private Timestamp createdAt;	
	private String orderTitle;
	
	@ManyToOne
	@JoinColumn(name = "customerID")
	private Customer customer;

	@Column(nullable = true)
	private Boolean isBlocked = false;
	
	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}
	
	public Orders(String orderTitle, long customerID) {
		super();
		this.orderTitle = orderTitle;
		this.customer = new Customer();
		this.customer.setCustomerID(customerID);
	}
	
	public Long getOrder_ID() {
		return order_ID;
	}
			
	public Orders() {
	
	}
		
	public Orders(Long order_ID, Timestamp createdAt, boolean isBlocked) {
		super();
		this.order_ID = order_ID;
		this.createdAt = createdAt;
		this.isBlocked = isBlocked;
	}
	
	public void setCustomerID(Long customerID) {
		customer.setCustomerID(customerID);
	}
	
	public Long getCustomerID() {
		return customer.getCustomerID();
	}

	public Timestamp getCreatedAt() {
		if (createdAt != null) {
			return new Timestamp(createdAt.getTime());
		}
		return null;
	}
		
	public void setOrder_ID(Long order_ID) {
		this.order_ID = order_ID;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
		
	@Override
    public String toString() {
        return new ToStringCreator(this)
        		.append("order_ID", this.order_ID)
        		.append("createdAt", this.createdAt)
        		.append("orderTitle", this.orderTitle)
        		.append("isBlocked",this.isBlocked)
        		.toString();
	}

	@PrePersist // called during INSERT
	protected void onPersist() {
	    setCreatedAt(now());
	}
	
	protected static Timestamp now() {
	    return new Timestamp(new Date().getTime());
	}	
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean getBlocked() {
		return isBlocked != null && isBlocked;
	}

	public void setBlocked(boolean blocked) {
		isBlocked = blocked;
	}
}
