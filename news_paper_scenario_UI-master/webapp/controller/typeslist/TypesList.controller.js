sap.ui.define([
	"TestUIApp2/controller/BaseObjController",
	"sap/m/MessageToast",
	"TestUIApp2/controller/typeslist/SubscriptionTypeDialog",
	"TestUIApp2/controller/typeslist/AdvertisementTypeDialog"
], function(BaseObjController, MessageToast,SubscriptionTypeDialog,AdvertisementTypeDialog) {
	"use strict";
	return BaseObjController.extend("TestUIApp2.controller.typeslist.TypesList", {
		onInit: function() {
			this.onSubscriptionTypeInit();
			this.onAdvertisementTypeInit();
		},
		onSubscriptionTypeInit: function(){
			this.SubscriptionTypeDialog = new SubscriptionTypeDialog(this);
			this.SubscriptionTypeDialog.onSetObjUrl(this.getOwnerComponent()._subtype_url);
			this.SubscriptionTypeDialog.onSetModel();
		},
		onSubscriptionTypeAdd: function(){
			this.SubscriptionTypeDialog.onOpenAddDialog();
		},
		onSubscriptionTypeSelect: function(oEvent){
			var oItem = oEvent.getSource();
			var oCtx = oItem.getBindingContext("subscriptiontypes");
			this.SubscriptionTypeDialog.onSelectRecord(oCtx.getProperty("subscriptionTypeId"),oCtx.getPath().substr(1));
		},
		onSubscriptionTypeDelete: function(oEvent){
//			this.onSubscriptionTypeSelect(oEvent); 
			this.SubscriptionTypeDialog.onDeleteRecord();
		},
		onSubscriptionTypeEdit: function(oEvent){
			this.onSubscriptionTypeSelect(oEvent); 
			this.SubscriptionTypeDialog.onOpenEditDialog();
		},
		
		onAdvertisementTypeInit: function(){
			this.AdvertisementTypeDialog = new AdvertisementTypeDialog(this);
			this.AdvertisementTypeDialog.onSetObjUrl(this.getOwnerComponent()._advtype_url);
			this.AdvertisementTypeDialog.onSetModel();
		},
		onAdvertisementTypeAdd: function(){
			this.AdvertisementTypeDialog.onOpenAddDialog();
		},
		onAdvertisementTypeSelect: function(oEvent){
			var oItem = oEvent.getSource();
			var oCtx = oItem.getBindingContext("advertisementtypes");
			this.AdvertisementTypeDialog.onSelectRecord(oCtx.getProperty("advertisementTypeId"),oCtx.getPath().substr(1));
		},
		onAdvertisementTypeDelete: function(){
			this.AdvertisementTypeDialog.onDeleteRecord();
		},
		onAdvertisementTypeEdit: function(){
			this.AdvertisementTypeDialog.onOpenEditDialog();
		}
	});
});