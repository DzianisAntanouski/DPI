# Setup Guide for Media App Skeleton Application

Setting up the Media App Skeleton application involves deploying the Spring Boot application to Cloud Foundry and onboarding the Data Privacy Integration service.

## Prerequisites

- A global SAP Cloud Platform account with available quota for Data Privacy Integration Service and the SaaS application. See [DPI Developers Guide][dpi-dev]

- For local deployment: Java (mininum version 11) and Maven must be installed.

- [Cloud Foundry CLI tools][cli-tools]

## Deploying the Skeleton Application to Cloud Foundry

The Media App Skeletion Application is a REST-based Spring Boot application that can be deployed into cloud foundry by executing:

```command

# cf push --route-path <application-route>

```

After deploying, save the base Application URL.

The Media App application can also be installed manually by executing:

```command

mvn spring-boot:run

```

The following API endpoints are exposed:

API Endpoint | Search Paramters | Description |
----------------- | -------------| ------------ |
`/api/v1/customers` | `customerID`, `firstName`, `lastName`, `email`, `birthday` | To query the Customer entity set. |
`/api/v1/subscriptions` | `customerID` | To query the Subscription entity set
`/api/v1/orders` | `customerID` |  To query the Order entity set |
`/api/v1/dpp/metadata` |  | Exposes the Personal Data Information metadata |

## Onboarding the Data Privacy Integration Kernel Service

To onboard the DPI service, follow the steps specified in the [DPI Development Guide][dpi-dev] using the following configuration file after replacing the `applicationURL` attribute with the base Application URL:

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

<!--  
## Setting up the Data Privacy Integration SaaS Application
-->

[dpi-dev]: https://github.wdf.sap.corp/pages/Data-Privacy-Engineering-Services/services/developers/dev-dpi.html
[cli-tools]: https://docs.cloudfoundry.org/cf-cli/install-go-cli.html
