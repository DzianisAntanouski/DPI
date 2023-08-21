# BupaByCloudSDK

SCP and S4 integration using Cloud SDK 
 

 


   Skip to end of metadata 

Created by D'Souza, Prashanth, last modified just a moment ago 
Go to start of metadata 


You need a VLAB system. In for the examples here in E1Y (https://my300098.s4hana.ondemand.com/)  is the S4 system.

Log on to the Launchpad with user ADMINISTRATOR (password at the bottom of this site). Then create a communication system with your own communication user and a communication arrangement for scenario 0008 that uses this system. Then use communication user to connect to the S/4 system. Refer to the section “Enable the Business Partner API within the S/4HANA Cloud system” in blog post and create the below items.
•Users created

          COMM_USER_BUSINESS_PARTNER_API Password: 

          OUTBOUND_USER_BUSINESS_PARTNER_API Password:
•Communication Arrangements 

          SCP_INTEGRATION_BuPA_API

Testing in postman

Use the below urls to test in postman . Hint :- Refer to "Make an example OData call to test the Business Partner API" in blog post

https://my300098-api.s4hana.ondemand.com/sap/opu/odata/sap/API_BUSINESS_PARTNER/$metadata

https://my300098-api.s4hana.ondemand.com/sap/opu/odata/sap/API_BUSINESS_PARTNER/A_BusinessPartner?$top=100&$format=json

BusinessPartner Retrieval

This display all the Business Partners from the S4 system. See the Section "Develop the app to consume the Business Partner API using the S/4HANA Cloud SDK" in blog post.

Access from ShowCaseApp => Login to ShowCaseApp (Business User test.cf.showcase@gmail.com) password: Welcome@123

Retrieve BuPa's

Hints:-

cf set-env businesspartnerretrival destinations "[{name: \"ErpQueryEndpoint\", url: \"https://my300098-api.s4hana.ondemand.com\", username: \"COMM_USER_BUSINESS_PARTNER_API\", password: \"SapCloudSDK2018isgood$\"}]"

Address Manager




Refer to https://github.com/SAP/cloud-s4-sdk-book to build run and deploy this app on cf.

Access from ShowCaseApp => Login to ShowCaseApp (Business User test.cf.showcase@gmail.com) password: Welcome@123

Address Manager

Hints:-

set the environment variable ALLOW_MOCKED_AUTH_HEADER to true in the app on Cloud Foundry (see this blog post) or properly secure your application, as for example described in this blog post

cf set-env address-manager destinations "[{name: \"ErpQueryEndpoint\", url: \"https://my300098-api.s4hana.ondemand.com\", username: \"COMM_USER_BUSINESS_PARTNER_API\", password: \"SapCloudSDK2018isgood$\"}]"

set ALLOW_MOCKED_AUTH_HEADER="true"




Troubleshooting

401 errors while accessing the Bupa may be because of Communication user being locked.

Goto https://my300098.s4hana.ondemand.com/ui#CommunicationUser-maintain

And unlock user COMM_USER_BUSINESS_PARTNER_API



