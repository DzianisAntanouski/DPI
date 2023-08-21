package com.sap.showcase.common.security.auditlog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sap.cloud.security.xsuaa.token.SpringSecurityContext;
import com.sap.xs.audit.api.AuditLogMessageFactory;
import com.sap.xs.audit.api.ConfigurationChangeAuditMessage;
import com.sap.xs.audit.api.DataAccessAuditMessage;
import com.sap.xs.audit.api.DataModificationAuditMessage;
import com.sap.xs.audit.api.SecurityEventAuditMessage;
import com.sap.xs.audit.api.exception.AuditLogException;

@Component
public class AuditLogContext {
	private static final Logger logger = LoggerFactory.getLogger("AuditLogContext");

	private String user;
	private String objectID;
	private String objectValueNew;
	private String objectValueOld;
	private String attribute_1;
	private String attribute_2;
	private String attachmentID;
	private String attachmentName;
	private String ipAddress;
	private String securityEventDescription;

	public AuditLogContext() {
		this.user = "Not initialized";
		this.objectID = "ObjectID";
		this.objectValueNew = "objectValueNew";
		this.objectValueOld = "objectValueOld";
		this.attribute_1 = "MyTitle";
		this.attribute_2 = "Version";
		this.attachmentID = "attachmentID";
		this.attachmentName = "attachmentName";
	}

	public String getUser() {
		return user;
	}

	public void initializeUserContext() {

		this.user = SpringSecurityContext.getToken().getGivenName();

	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	public String getObjectValueNew() {
		return objectValueNew;
	}

	public void setObjectValueNew(String objectValueNew) {
		this.objectValueNew = objectValueNew;
	}

	public String getObjectValueOld() {
		return objectValueOld;
	}

	public void setObjectValueOld(String objectValueOld) {
		this.objectValueOld = objectValueOld;
	}

	public String getAttribute_1() {
		return attribute_1;
	}

	public void setAttribute_1(String attribute_1) {
		this.attribute_1 = attribute_1;
	}

	public String getAttribute_2() {
		return attribute_2;
	}

	public void setAttribute_2(String attribute_2) {
		this.attribute_2 = attribute_2;
	}

	public String getAttachmentID() {
		return attachmentID;
	}

	public void setAttachmentID(String attachmentID) {
		this.attachmentID = attachmentID;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String iPaddress) {
		ipAddress = iPaddress;
	}

	public String getSecurityEventDescription() {
		return securityEventDescription;
	}

	public void setSecurityEventDescription(String securityEventDescription) {
		this.securityEventDescription = securityEventDescription;
	}

	public ConfigurationChangeAuditMessage logConfigurationChange(AuditLogMessageFactory auditLogMessageFactory)
			throws AuditLogException {
		ConfigurationChangeAuditMessage message = auditLogMessageFactory.createConfigurationChangeAuditMessage();

		message.setUser(user);
		message.setObjectId(objectID);
		message.addValue(attribute_1, objectValueOld, objectValueNew);
		message.logSuccess();

		return message;
	}

	public DataAccessAuditMessage logDataAccess(AuditLogMessageFactory auditLogMessageFactory)
			throws AuditLogException {
		DataAccessAuditMessage message = auditLogMessageFactory.createDataAccessAuditMessage();

		message.setUser(user);
		message.setObjectId(objectID);
		message.addAttribute(attribute_1, true);
		message.addAttribute(attribute_2, false);
		message.addAttachment(attachmentID, attachmentName);

		message.log();

		return message;
	}

	public DataModificationAuditMessage logDataModificationPrepare(AuditLogMessageFactory auditLogMessageFactory)
			throws AuditLogException {
		DataModificationAuditMessage message = auditLogMessageFactory.createDataModificationAuditMessage();

		message.setUser(user);
		message.setObjectId(objectID);
		message.addAttribute(attribute_1, objectValueOld, objectValueNew);

		message.logPrepare();

		return message;
	}

	public DataModificationAuditMessage logDataModificationStatus(AuditLogMessageFactory auditLogMessageFactory,
			boolean status) throws AuditLogException {
		DataModificationAuditMessage message = auditLogMessageFactory.createDataModificationAuditMessage();

		message.setUser(user);
		message.setObjectId(objectID);
		message.addAttribute(attribute_1, objectValueOld, objectValueNew);

		if (status) {
			message.logSuccess();
		} else {
			message.logFailure();
		}
		return message;
	}

	public SecurityEventAuditMessage logSecurityEvent(AuditLogMessageFactory auditLogMessageFactory)
			throws AuditLogException {
		SecurityEventAuditMessage message = auditLogMessageFactory.createSecurityEventAuditMessage();

		message.setUser(user);
		message.setIp(ipAddress);
		message.setData(securityEventDescription);

		message.log();

		return message;
	}
}
