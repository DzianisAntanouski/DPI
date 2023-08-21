# Data Privacy Integration Service - Enablement Guide

## Introduction

This guide provides a sampling of how the [Data Privacy Integration service][dpi] (DPI) helps you accelerate development of the following [Data Privacy Capabilities][dpi-capabilities]:

- Information Retrieval: the Data privacy Integration service is integrated with the [Personal Data Manager][pdm] service to provide capabilities for identification of data subjects, information retrieval and export of personal data to exercise the right to information of data subjects, and capabilities to request deletion of personal data to exercise the data subject's right to be forgotten.
- Business Purpose Management: integrated with the [Business Context Manager][bcm] to support the management of DPP Purposes and Business Context for legitimate processing of personal data.
- Data Deletion: integrated with the [Data Retention Manager][drm] to support the management of retention and residence rules for personal data and to orchestrate blocking and deletion of personal data.

These capabilities are exposed via [application programming interfaces (APIs)][dpi-api] that facilitiate seamless integration.

## The Sample Application

In this guide, we demonstrate integration with the DPI service using a simple media application developed with Java and [Spring Boot][spring-boot]. The [complete show case media app][showcase-app] can be found in the [Media App Code Repository][showcase-app-repo] and it is fully integrated with the DPI service, Audit Logging Service, Business Partner Service, XSUAA service, and other Backing Services like PostgreSQL. However, for the sake of this guide, we have stripped it down to a bare Skeleton Application with minimal dependencies to the SAP Cloud Platform services to enable easy setup for developers.

You can setup the Basic Media App Skeleton Application in your own cloud foundry environment and integrate your deployed application by following the [Sample Application Setup Guide][dpi-enablement-setup]. The rest of this document describes the application.

The domain model of our application is shown below.

![Media App Domain Class Diagram][media-app]

As shown in the class diagram above, a `Customer` can create `Subscriptons` to `Magazines` or place `Orders` to `Books`. The [`Customer`][customer-model] class represents data subjects and the following shows the personal data attributes that are collected and processed.

```java
@Data
public class Customer {
    private String customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
    private LocalDate  birthday;
}

```

The [`Order`][order-model] and [`Subscription`][subscription-model] classes represent transactional objects that are created on behalf of the Customer, and they both contain a reference to the `customerID`.

```java
@Data
public class Order {
    private String customerID;
    private Integer orderID;
    private String bookTitle;
    private LocalDate createdAt;
}
```

```java
@Data
public class Subscription {
    private Integer subscriptionID;
    private String customerID;
    private LocalDate startDate;
    private LocalDate endDate;
    private String magazineTitle;
}
```

The `Customer`, `Subscription`, and `Order` entities are exposed via the following API endpoints to support retrieval of data subject (customer) information and transactional data (also showing their corresponding Controller classes):

| Entity      | API Endpoint | Search Paramters | Entity Controller Class |
| ----------- | ----------------- | -------------| ------------ |
| `Customer`      | [/api/v1/customers][customer-endpoint] | `customerID`, `firstName`, `lastName`, `email`, `birthday` | [CustomerController.java][customer-controller] |
| `Subscription`   | [/api/v1/subscriptions][subscription-endpoint] | `customerID` | [SubscriptionController.java][subscription-controller] |
| `Order`   | [/api/v1/orders][order-endpoint] | `customerID` |  [OrderController.java][order-controller] |

## Annotating Personal Data

To integrate with the Information Retrieval capabilities of the Data Privacy Integration service (and Personal Data Manager service), the entities and attributes have to be annotated and exposed via an API endpoint. ODATA applications can use [PDM ODATA annotations][pdm-odata-annotations], while REST applications must expose a MetaData JSON file via an API.

For the skeleton application, the [PDM Metadata][information-metadata] is exposed via [`/api/v1/dpp/metadata`][skeleton-metadata] in the [DPPController.java][dpp-controller] class. Explanations of the key annotations follow the JSON file below.

```json
{
    "Namespace": "com.sap.dpi.skeleton:dpi-enablement-skeleton",
    "Entities": [
        {
            "Name": "customers",
            "sap:pdm-semantics": "data-subject-identity",
            "sap:pdm-business-object": "Customers",
            "sap:pdm-business-node": "CustomerMaster",
            "sap:label": "Customer",
            "sap:updatable": false,
            "sap:deletable": false,
            "sap:pdm-data-subject-role": "Customer",
            "sap:pdm-data-subject-role-desc": "",
            "Keys": [
                {
                    "Name": "customerID"
                }
            ],
            "Properties": [
                {
                    "Name": "customerID",
                    "Type": "Edm.String",
                    "Nullable": false,
                    "MaxLength": 256,
                    "sap:pdm-semantics": "data-subject-id"
                },
                {
                    "Name": "firstName",
                    "Type": "Edm.String",
                    "Nullable": false,
                    "MaxLength": 256,
                    "sap:pdm-semantics": "personal-data",
                    "sap:pdm-property": "first-name",
                    "sap:label": "First Name"
                },
                {
                    "Name": "lastName",
                    "Type": "Edm.String",
                    "Nullable": false,
                    "MaxLength": 256,
                    "sap:pdm-semantics": "personal-data",
                    "sap:pdm-property": "last-name",
                    "sap:label": "Last Name"
                },
                {
                    "Name": "email",
                    "Type": "Edm.String",
                    "Nullable": true,
                    "MaxLength": 241,
                    "sap:pdm-semantics": "personal-data",
                    "sap:pdm-property": "email",
                    "sap:label": "Email Address"
                },
                {
                    "Name": "phone",
                    "Type": "Edm.String",
                    "Nullable": true,
                    "MaxLength": 50,
                    "sap:pdm-semantics": "personal-data",
                    "sap:pdm-property": "phone",
                    "sap:label": "Phone"
                },
                {
                    "Name": "birthday",
                    "Type": "Edm.DateTime",
                    "Nullable": true,
                    "MaxLength": 256,
                    "sap:pdm-semantics": "personal-data",
                    "sap:pdm-property": "date-of-birth",
                    "sap:label": "Date of Birth"
                },
                {
                    "Name": "street",
                    "Type": "Edm.String",
                    "Nullable": true,
                    "MaxLength": 256,
                    "sap:pdm-semantics": "personal-data",
                    "sap:pdm-property": "address-street",
                    "sap:label": "Address Street"
                },
                {
                    "Name": "houseNumber",
                    "Type": "Edm.String",
                    "Nullable": true,
                    "MaxLength": 256,
                    "sap:pdm-semantics": "personal-data",
                    "sap:pdm-property": "additional-address-house-number",
                    "sap:label": "Address House number"
                },
                {
                    "Name": "postalCode",
                    "Type": "Edm.String",
                    "Nullable": true,
                    "MaxLength": 256,
                    "sap:pdm-semantics": "personal-data",
                    "sap:pdm-property": "address-postal-code",
                    "sap:label": "Adress Postal Code"
                },
                {
                    "Name": "city",
                    "Type": "Edm.String",
                    "Nullable": true,
                    "MaxLength": 256,
                    "sap:pdm-semantics": "personal-data",
                    "sap:pdm-property": "address-city",
                    "sap:label": "Address City"
                },
                {
                    "Name": "country",
                    "Type": "Edm.String",
                    "Label": "Country",
                    "sap:pdm-semantics": "personal-data"
                }
            ]
        },
        {
            "Name": "subscriptions",
            "sap:pdm-semantics": "business-data",
            "sap:pdm-business-object": "Subscriptions",
            "sap:pdm-business-node": "SubscriptionHeader",
            "sap:label": "Subscriptions",
            "sap:updatable": false,
            "sap:deletable": false,
            "Keys": [
                {
                    "Name": "subscriptionID"
                }
            ],
            "Properties": [
                {
                    "Name": "customerID",
                    "Type": "Edm.String",
                    "Nullable": false,
                    "MaxLength": 256,
                    "sap:pdm-property": "data-subject-id",
                    "sap:pdm-display-seq-no": -1
                },
                {
                    "Name": "subscriptionID",
                    "Type": "Edm.Int32",
                    "Nullable": false,
                    "MaxLength": 50,
                    "sap:label": "Subscription ID",
                    "sap:pdm-display-seq-no": 1
                },
                {
                    "Name": "startDate",
                    "Type": "Edm.DateTime",
                    "Nullable": false,
                    "MaxLength": 256,
                    "sap:label": "Start Date",
                    "sap:pdm-display-seq-no": 3
                },
                {
                    "Name": "endDate",
                    "Type": "Edm.DateTime",
                    "Nullable": false,
                    "MaxLength": 256,
                    "sap:label": "End Date",
                    "sap:pdm-display-seq-no": 4
                },
                {
                    "Name": "magazineTitle",
                    "Type": "Edm.String",
                    "Nullable": true,
                    "MaxLength": 255,
                    "sap:label": "Magazine Title",
                    "sap:pdm-display-seq-no": 2
                }
            ]
        },
        {
            "Name": "orders",
            "sap:pdm-semantics": "business-data",
            "sap:pdm-business-object": "Orders",
            "sap:pdm-business-node": "OrderHeader",
            "sap:label": "Orders",
            "sap:updatable": false,
            "sap:deletable": false,
            "Keys": [
                {
                    "Name": "orderID"
                }
            ],
            "Properties": [
                {
                    "Name": "customerID",
                    "Type": "Edm.String",
                    "Nullable": false,
                    "MaxLength": 256,
                    "sap:pdm-property": "data-subject-id",
                    "sap:pdm-display-seq-no": -1
                },
                {
                    "Name": "orderID",
                    "Type": "Edm.Int32",
                    "Nullable": false,
                    "MaxLength": 50,
                    "sap:label": "Order ID",
                    "sap:pdm-display-seq-no": 1
                },
                {
                    "Name": "createdAt",
                    "Type": "Edm.DateTime",
                    "Nullable": false,
                    "MaxLength": 256,
                    "sap:label": "Created At",
                    "sap:pdm-display-seq-no": 3
                },
                {
                    "Name": "bookTitle",
                    "Type": "Edm.String",
                    "Nullable": false,
                    "MaxLength": 255,
                    "sap:label": "Book Title",
                    "sap:pdm-display-seq-no": 2
                }
            ]
        }
    ]
}
```

As shown in the Information MetaData JSON file above, every entity is annotated with the the `sap:pdm-semantics` annotation which is an indication of the entity type.  From our example JSON, we have the following mappings:

Entity | sap:pdm-semantics value | description |
------ |  ------------------ | ----------
`Customer` | `data-subject-identity` | The entity identifies represents a data subject. This entity type must also have a corresponding `sap:pdm-data-subject-role` annotation which indicates the role of the data subject.
`Subscription` | `business-data` | The entity represents a business or transactional data that is related to the data subject identity. | 
`Order` | `business-data` | == ditto == |

Other entity type annotations and descriptions can be found in the [Entity Annotation Table][entity-annotations].

The relevant entity Properties must also be annotated with the following PDM attributes:

- `sap:pdm-semantics`: to provide the semantics for a data field, for example, whether the field contains personal data or sensititve personal data. Possible values include `personal-data`, `sensitive-personal-data`, `data-subject-role`, `data-subject-id`, etc. See the [Property Type Annotation reference][property-type-annotations] for more values and description.
- `sap:pdm-proerty`: to denote the kind of data field, for example, whether the field indicates a first name, last name, address, telephone number, etc. Possible values include `first-name`, `last-name`, `gender`, `date-of-birth`, etc. See also the [Property Type Annotation reference][property-type-annotations] for more values and description.

## The DPI Instance Configuration

Below is the [DPI instance configuration JSON file][dpi-config] showing the configuration details to enable the Information capabilities of the Data Privacy Integration service.

```json
{
    "xs-security": {
        "xsappname": "dpi-enablement-inst",
        "authorities": [
            "$ACCEPT_GRANTED_AUTHORITIES"
        ]
    },
    "dppConfig": {
        "applicationName": "dpi-enablement-skeleton",
        "information": {
            "applicationTitle": "DPI Enablement Skeleton Application",
            "applicationTitleKey": "dpi-enablement-skeleton",
            "modules": [
                {
                    "fullyQualifiedModuleName": "dpi-sample-module",
                    "applicationURL": "https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com",
                    "endPoints": [
                        {
                            "type": "rest",
                            "serviceName": "EnablementSkeleton",
                            "serviceTitle": "Enablement Skeleton",
                            "serviceTitleKey": "Enablement Skeleton",
                            "serviceURI": "/api/v1",
                            "annotationProviderRestEndPoint": "/api/v1/dpp/metadata"
                        }
                    ]
                }
            ]
        }
    }
}
```

- The `information` configuration can contain several `modules`, each of which refer to an application and endpoints from which personal data information of data subjects can be retrieved.
- `applicationURL`: refers to the base URL of the application
- `endpoints[n]/type`: refers to the type of application, which could be `rest` or `odata`.
- `endpoints[n]/serviceURI`: refers to the base path of the entities of the application. The DPI derives the entity interface by appending the `applicationURL`, `serviceURI`, and the entity's `Name`. For example, the interface for the `customer` in our example is `https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com/api/v1/customers`. Using this derived URI, the DPI can query an entity using the entity properties as search parameters: e.g. `https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com/api/v1/customers?customerID=1`, `https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com/api/v1/subscriptions?customerID=1`, etc.

## How Information Retrieval Happens

Suppose a Data Protection Specialist wants to retrieve the information of a particular customer and determine where the personal data of the customer is being used. The sequence diagram below illustrates how this happens:

![Personal Data Use "personal data use"][personal-data-use-seq]

The Data Protection specialist enters a search query via the DPI SaaS Application, e.g. using the search parameter combination of "First Name", "Last Name", and "Email" as shown in the query below:

![Search Query for Personal Data][user-search-query]

To retrieve the search results, the DPI service uses the configuration data and derives the following URI and makes a background API call to the Application to retrieve the data subject's information:

`GET` [/api/v1/customers?firstName=Sophie&lastName=Guillaume&email=sophie.guillaume@example.com][sophie-search]

```json
[
    {
        "customerID": "91",
        "firstName": "Sophie",
        "lastName": "Guillaume",
        "email": "sophie.guillaume@example.com",
        "phone": "04-73-72-40-60",
        "street": "Place de L'Abbé-Franz-Stock",
        "houseNumber": "5486",
        "postalCode": "94666",
        "city": "Mulhouse",
        "country": "France",
        "birthday": "1989-02-22"
    }
]
```

Thus, the Data Protection Specialist can see the following result on the Personal Details page:

![Personal Details Page][sophie-personal-details]

To retriev where the personal data is used, DPI queries the exposed APIs for Order and Subscription transactional objects, using the customerID as a search parameter:

`GET` [/api/v1/subscriptions?customerID=91][sopie-sub-query]

```json
[
    {
        "subscriptionID": 47,
        "customerID": "91",
        "startDate": "2010-11-24",
        "endDate": "2015-06-27",
        "magazineTitle": "InfoWorld"
    },
    {
        "subscriptionID": 126,
        "customerID": "91",
        "startDate": "2006-05-26",
        "endDate": "2023-11-10",
        "magazineTitle": "Computerworld"
    },
    {
        "subscriptionID": 141,
        "customerID": "91",
        "startDate": "2015-03-05",
        "endDate": "2016-06-03",
        "magazineTitle": "InfoWorld"
    },
    {
        "subscriptionID": 164,
        "customerID": "91",
        "startDate": "2007-04-01",
        "endDate": "2015-03-10",
        "magazineTitle": "Jet"
    },
    {
        "subscriptionID": 214,
        "customerID": "91",
        "startDate": "2007-06-21",
        "endDate": "2020-01-19",
        "magazineTitle": "Billboard"
    }
]
```

`GET` [/api/v1/orders?customerID=91][sopie-order-query]

```json
[
    {
        "customerID": "91",
        "orderID": 43,
        "bookTitle": "Hana",
        "createdAt": "2015-12-29"
    },
    {
        "customerID": "91",
        "orderID": 93,
        "bookTitle": "Fundraising - von Database-Marketing bis Financial Controlling",
        "createdAt": "2020-07-11"
    },
    {
        "customerID": "91",
        "orderID": 294,
        "bookTitle": "Gdpr",
        "createdAt": "2012-08-10"
    },
    {
        "customerID": "91",
        "orderID": 417,
        "bookTitle": "Chaos-based Cryptography",
        "createdAt": "2013-01-10"
    }
]
```

Thus, the DPP Specialist can view the result as displayed via the DPI SaaS application:

![DPI SaaS Usage of Personal Data][sopie-usage-personal-data]

[showcase-app-repo]: https://github.wdf.sap.corp/DPP-showcase-application/news_paper_scenario_server/tree/get-bupas
[showcase-app]: https://dpp-showcase-approuter-media-bupa-integration.cfapps.sap.hana.ondemand.com/frontend/
[dpi-enablement-setup]: /dpi-enablement-skeleton/README.md
[sopie-usage-personal-data]: /assets/personal-data-use.png "Usage of Personal Data from DPI SaaS"
[sopie-order-query]: https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com/api/v1/orders?customerID=91
[sopie-sub-query]: https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com/api/v1/subscriptions?customerID=91
[sophie-personal-details]: /assets/personal-data.png
[sophie-search]: https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com/api/v1/customers?firstName=Sophie&lastName=Guillaume&email=sophie.guillaume@example.com
[user-search-query]: /assets/search-result.png "query personal data"
[dpi-config]: dpi-enablement-skeleton/src/main/resources/config/dpi-instance-config.json
[property-type-annotations]: https://help.sap.com/viewer/b20c77c1f1fc44b896f6f66719fc603a/SHIP/en-US/66570273d5ff492aad06ab76765275e4.html#loio66570273d5ff492aad06ab76765275e4__table_aln_ck4_cbb
[entity-annotations]:https://help.sap.com/viewer/620a3ea6aaf64610accdd05cca9e3de2/SHIP/en-US/66570273d5ff492aad06ab76765275e4.html#loio66570273d5ff492aad06ab76765275e4__table_c55_nf4_cbb
[dpi]: https://github.wdf.sap.corp/pages/Data-Privacy-Engineering-Services/services/dpi.html
[dpi-api]: https://github.wdf.sap.corp/pages/Data-Privacy-Engineering-Services/services/dpi.html#apis
[dpi-capabilities]: https://github.wdf.sap.corp/pages/Data-Privacy-Engineering-Services/services/dpi.html#services
[dpi-dev]: https://github.wdf.sap.corp/pages/Data-Privacy-Engineering-Services/services/developers/dev-dpi.html
[dpi-onboard]: https://help.sap.com/viewer/03be4706e6f74c03907b7f790cf6a76e/SHIP/en-US/1e57b324f4d44ff3819bdf6bb1ac068d.html
[dpi-kyma]: https://help.sap.com/viewer/03be4706e6f74c03907b7f790cf6a76e/SHIP/en-US/021bbbf6f34b4b418eb652e3dc9739f6.html
[showcase-code]: https://github.wdf.sap.corp/DPP-showcase-application/news_paper_scenario_server/tree/get-bupas
[skeleton-code]: /dpi-enablement-skeleton
[media-app]: /assets/media_app_class.png "Media App Class Diagram"
[pdm-odata-annotations]: https://help.sap.com/viewer/620a3ea6aaf64610accdd05cca9e3de2/SHIP/en-US/5a55fae1eb7c496c92c56071186d76b3.html
[skeleton-metadata]:https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com/api/v1/dpp/metadata
[customer-controller]: /dpi-enablement-skeleton/src/main/java/com/sap/dpi/skeleton/controllers/CustomerController.java
[subscription-controller]: /dpi-enablement-skeleton/src/main/java/com/sap/dpi/skeleton/controllers/SubscriptionController.java
[order-controller]: /dpi-enablement-skeleton/src/main/java/com/sap/dpi/skeleton/controllers/OrderController.java
[customer-endpoint]: https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com/api/v1/customers
[subscription-endpoint]: https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com/api/v1/subscriptions
[order-endpoint]: https://dpi-enablement-skeleton.cfapps.sap.hana.ondemand.com/api/v1/orders
[personal-data-use-seq]: /assets/usage_personal_data.png "Personal Data Use Sequence Diagram"
[spring-boot]: https://spring.io/projects/spring-boot
[information-metadata]: /dpi-enablement-skeleton/src/main/resources/config/information-metadata.json
[pdm]: https://help.sap.com/viewer/product/PERSONAL_DATA_MANAGER/SHIP/en-US
[drm]: https://help.sap.com/viewer/product/DATA_RETENTION_MANAGER/SHIP/en-US
[ccr]: TODO
[bcm]: https://wiki.wdf.sap.corp/wiki/display/PDM/Business+Context+Manager
[dpp-controller]: /dpi-enablement-skeleton/src/main/java/com/sap/dpi/skeleton/controllers/DPPController.java
[customer-model]: /dpi-enablement-skeleton/src/main/java/com/sap/dpi/skeleton/model/Customer.java
[subscription-model]: /dpi-enablement-skeleton/src/main/java/com/sap/dpi/skeleton/model/Subscription.java
[order-model]: /dpi-enablement-skeleton/src/main/java/com/sap/dpi/skeleton/model/Order.java
