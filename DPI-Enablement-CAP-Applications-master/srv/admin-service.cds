using { sap.capire.bookshop as my } from '../db/schema';

service AdminService @(_requires:'authenticated-user') {
    entity Customer as projection on my.Customers;
    entity Book as projection on my.Books;
    entity Order as projection on my.Orders;
    entity Magazine as projection on my.Magazines
}