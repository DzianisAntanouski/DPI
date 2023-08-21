using { sap.capire.bookshop as my } from '../db/schema';

@path:'/browse'
service CatalogService {

    entity Customer as SELECT from my.Customers { * } excluding { country, birthday };

}