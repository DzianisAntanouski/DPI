package com.sap.showcase.common.security.pii;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;

public class PersonalDataEvent {
	private String operation;
	private Object curObject;
	private Object newObject;	
	private List<? extends Object> listObjects;	
		
	public PersonalDataEvent(String operation, Object curObject, Object newObject) {
		super();
		this.operation = operation;
		this.curObject = curObject;
		this.newObject = newObject;
	}

	public PersonalDataEvent(String operation, Object curObject) {
		super();
		this.operation = operation;
		this.curObject = curObject;
		List<Object> objList = new ArrayList<Object>();
		objList.add(curObject);
		listObjects = objList;		
	}

	public Object getCurObject() {
		return curObject;
	}

	public void setCurObject(Object curObject) {
		this.curObject = curObject;
	}

	public Object getNewObject() {
		return newObject;
	}

	public void setNewObject(Object newObject) {
		this.newObject = newObject;
	}	
	    	
	public PersonalDataEvent(String operation, List<?> listObjects) {
		super();		
		this.operation = operation;
		this.listObjects = listObjects;
	}
	

	public List<?> getListObjects() {
		return listObjects;
	}

	public void setListObjects(List<?> listObjects) {
		this.listObjects = listObjects;
	}
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this)
		.append("operation", this.operation)
		.append("curObject", this.curObject)
		.append("newObject", this.newObject)
		.append("listObjects", this.listObjects)		
		.toString();        
	}
     
}
