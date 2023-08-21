namespace sap.capire.bookshop;
using { Currency, managed } from '@sap/cds/common';

entity Customers  {
    key ID : Integer;
    firstName : String;
    lastName :  String;
    email : String;
    phone : String;
    houseNumber : String;
    postalCode : String;
    city : String;
    country :  String;
    birthday: Date;
    orders : Association to many Orders on orders.customer = $self; 
    subscriptions : Association to many Subscriptions on subscriptions.customer = $self; 
}

entity Orders : managed {
    key ID : Integer;
    book: Association to one Books;
    customer : Association to one Customers;
}

entity Books : managed
{
    key ID : Integer;
    author : String;
    bookTitle : String;
}

entity Magazines
{
    key ID : Integer;
    magazineTitle: String;
    publishDate : Date;
}

entity Subscriptions : managed
{
    key ID : Integer;
    customer: Association to one Customers;
    startDate : Date;
    magazine : Association to one Magazines;

}

 
    