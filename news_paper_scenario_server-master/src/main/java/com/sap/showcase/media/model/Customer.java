package com.sap.showcase.media.model;

import com.sap.showcase.common.security.pii.PersonalData;
import com.sap.showcase.common.security.pii.PersonalData.LogType;
import com.sap.showcase.common.security.pii.PersonalData.MaskMode;
import com.sap.showcase.common.security.pii.PersonalData.PersInfoType;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@PersonalData(PIType = PersInfoType.PII, LogType = LogType.Read, MaskMode = MaskMode.Off )
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerID")
	private Long customerID;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String address;
	private Date birthday;

	@Column(nullable = true)
	private Boolean isBlocked = false;

	public Customer() {
	}

	public Customer(String firstName,String lastName, String email, String phone, String address, Date birthday, boolean isBlocked) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.birthday = birthday;
		this.isBlocked = isBlocked;
	}

	public Long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean isBlocked() {
		return isBlocked != null && isBlocked;
	}

	public void setBlocked(boolean blocked) {
		isBlocked = blocked;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"customerID=" + customerID +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", birthday=" + birthday +
				", isBlocked=" + isBlocked +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return isBlocked == customer.isBlocked &&
				Objects.equals(customerID, customer.customerID) &&
				Objects.equals(firstName, customer.firstName) &&
				Objects.equals(lastName, customer.lastName) &&
				Objects.equals(email, customer.email) &&
				Objects.equals(phone, customer.phone) &&
				Objects.equals(address, customer.address) &&
				Objects.equals(birthday, customer.birthday);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerID, firstName, lastName, email, phone, address, birthday, isBlocked);
	}
}
