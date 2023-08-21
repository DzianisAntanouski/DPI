using {sap.capire.bookshop as db} from '../db/schema';

@path : '/dpp'
service DPPService {
    entity Customers as
        select from db.Customers {
            key ID,
                firstName,
                lastName,
                email,
                phone,
                houseNumber,
                postalCode,
                city,
                country,
                birthday
        };

    entity Orders    as
        select from db.Orders {
            key ID                 as orderID,
                book.bookTitle     as bookTitle,
                customer.firstName as firstName,
                customer.lastName  as lastName,
                customer.email     as email,
                customer.ID        as customerID
        };
    
    entity Subscriptions as
        select from db.Subscriptions
        {
            key ID as SubscriptionId,
            customer.firstName  as firstName,
            customer.lastName as lastName,
            customer.ID as customerID,
            magazine.magazineTitle as magazineTitle,
            startDate
         }
};

annotate DPPService.Customers with @(Communication.Contact  : {
    n : {
        surname: lastName,
        given: firstName
    },
    bday :  birthday ,
    email: email
}) 
@(PersonalData.EntitySemantics : 'DataSubject')  @(PersonalData.DataSubjectRole : 'Customer') {

    ID          @PersonalData.FieldSemantics : 'DataSubjectID';
    firstName   @PersonalData.IsPotentiallyPersonal;
    lastName    @PersonalData.IsPotentiallyPersonal;
    email       @Communication.IsEmailAddress;
    phone       @Communication.IsPhoneNumber;
    houseNumber @PersonalData.IsPotentiallyPersonal;
    postalCode  @PersonalData.IsPotentiallyPersonal;
    city        @PersonalData.IsPotentiallyPersonal;
    country     @PersonalData.IsPotentiallyPersonal;
    birthday    @PersonalData.IsPotentiallyPersonal;
};

annotate DPPService.Orders with @(PersonalData.EntitySemantics : 'ContractRelated') {
    customerID @PersonalData.FieldSemantics : 'DataSubjectID';
    orderID @PersonalData.IsPotentiallyPersonal;
    firstName @PersonalData.IsPotentiallyPersonal;
    lastName @PersonalData.IsPotentiallyPersonal;
    email @PersonalData.IsPotentiallyPersonal;
    bookTitle;
};



annotate DPPService.Subscriptions with @(PersonalData.EntitySemantics : 'ContractRelated') {
    customerID @PersonalData.FieldSemantics : 'DataSubjectID';
    SubscriptionId @PersonalData.IsPotentiallyPersonal;
    firstName @PersonalData.IsPotentiallyPersonal;
    lastName @PersonalData.IsPotentiallyPersonal;
    email @PersonalData.IsPotentiallyPersonal;
    startDate;
    magazineTitle;
};