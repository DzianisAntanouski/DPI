package com.sap.showcase.dpp.controllers;

public class ControllerUtil {
    public static final String DPP_BASE_PATH = "/api/dpp/v1";
    public static final String DPP_METADATA_PATH = DPP_BASE_PATH + "/metadata";
    public static final String DPP_CUSTOMERS_PATH = DPP_BASE_PATH + "/customers";
    public static final String DPP_SPECIFIC_CUSTOMERS_PATH = DPP_CUSTOMERS_PATH + "/{customerNumber}";
    public static final String DPP_SUBSCRIPTIONS_PATH = DPP_BASE_PATH + "/subscriptions";
    public static final String DPP_ORDERS_PATH = DPP_BASE_PATH + "/orders";

    public static final String DPP_RM_DELETE_DATA_SUBJECT = DPP_CUSTOMERS_PATH + "/deleteDataSubject";
    public static final String DPP_RM_LEGAL_ENTITY_VALUE_HELP = DPP_BASE_PATH + "/tenants/{dataSubjectRole}";

    public static final String DPP_RM_DATA_SUBSCRIPTION_SUBJECT_END_OF_BUSINESS = DPP_SUBSCRIPTIONS_PATH + "/dataSubjectEndOfBusiness";
    public static final String DPP_RM_DATA_SUBSCRIPTION_SUBJECTS_END_OF_RESIDENCE = DPP_SUBSCRIPTIONS_PATH + "/dataSubjectsEndofResidence";
    public static final String DPP_RM_DATA_SUBSCRIPTION_SUBJECT_LEGAL_ENTITIES = DPP_SUBSCRIPTIONS_PATH + "/dataSubjectLegalEntities";
    public static final String DPP_RM_DATA_SUBSCRIPTION_SUBJECTS_END_OF_RESIDENCE_CONFIRMATION = DPP_SUBSCRIPTIONS_PATH + "/dataSubjectsEndofResidenceConfirmation";
    public static final String DPP_RM_DATA_SUBSCRIPTION_SUBJECT_LEGAL_GROUND_DELETION = DPP_SUBSCRIPTIONS_PATH + "/dataSubjectLegalGroundDeletion";
    public static final String DPP_RM_DATA_SUBSCRIPTION_SUBJECT_LAST_RETENTION_START_DATES = DPP_SUBSCRIPTIONS_PATH + "/dataSubjectLastRetentionStartDates";

    public static final String DPP_RM_DATA_ORDER_SUBJECT_END_OF_BUSINESS = DPP_ORDERS_PATH + "/dataSubjectEndOfBusiness";
    public static final String DPP_RM_DATA_ORDER_SUBJECTS_END_OF_RESIDENCE = DPP_ORDERS_PATH + "/dataSubjectsEndofResidence";
    public static final String DPP_RM_DATA_ORDER_SUBJECT_LEGAL_ENTITIES = DPP_ORDERS_PATH + "/dataSubjectLegalEntities";
    public static final String DPP_RM_DATA_ORDER_SUBJECTS_END_OF_RESIDENCE_CONFIRMATION = DPP_ORDERS_PATH + "/dataSubjectsEndofResidenceConfirmation";
    public static final String DPP_RM_DATA_ORDER_SUBJECT_LEGAL_GROUND_DELETION = DPP_ORDERS_PATH + "/dataSubjectLegalGroundDeletion";
    public static final String DPP_RM_DATA_ORDER_SUBJECT_LAST_RETENTION_START_DATES = DPP_ORDERS_PATH + "/dataSubjectLastRetentionStartDates";

}

