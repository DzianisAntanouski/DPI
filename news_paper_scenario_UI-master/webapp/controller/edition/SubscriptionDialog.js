sap.ui.define([
	"TestUIApp2/controller/BaseObjController",
	"sap/m/MessageToast",
	'sap/ui/model/Filter'
], function(BaseObjController, MessageToast, Filter) {
	"use strict";
	return BaseObjController.extend("TestUIApp2.controller.edition.SubscriptionDialog", {
		 constructor: function(oCntrl) {
		 	this._onInit("subscriptions", oCntrl);
		 },
		onCreateRecord: function(){
			var newSub = {};
			var newCust = {};
			newCust.customerID = sap.ui.getCore().byId("customerCbId").getSelectedKey();
			newSub.customer = newCust;
			newSub.subscriptionTypeId = sap.ui.getCore().byId("subscriptionTypeCbId").getSelectedKey();
			newSub.startDate = sap.ui.getCore().byId("startDateId").getValue();
			newSub.endDate = sap.ui.getCore().byId("endDateId").getValue();
			
			var cur_mag = this.getOwnerComponent()._cur_magID;
			var new_sub_url = this.getOwnerComponent()._cust_url+newCust.customerID+"/subscriptions/"+newSub.subscriptionTypeId+"/"+ cur_mag;
			this._onCreateRecord(new_sub_url, newSub);
		},
		onDeleteRecord: function(){
			this._onDeleteRecord(this.getOwnerComponent()._cust_url+this.oCntrl._selectedItem+"/subscriptions/" + this.selectedItem);
		},
		onEditRecord: function(){
			var editSub = this.oView.getModel(this.modelName).getProperty(this.onGetSelectedPath());
			var edit_sub_url = this.getOwnerComponent()._cust_url+editSub.customerID+"/subscriptions/"+editSub.subscriptionType.subscriptionTypeId+"/"+ this.getOwnerComponent()._cur_magID;
			this._onEditRecord(edit_sub_url, editSub);
		},
		onBindElement: function(){
			var EditSubscriptionFrag = sap.ui.getCore().byId("EditSubscriptionFragId");
			EditSubscriptionFrag.bindElement({ 
 				path: this.selectedPath,
				model: this.modelName
			});	
		},
		onAddSave: function() {
			this.onCreateRecord();
			this.onCloseAddDialog();
		},
		onEditSave: function() {
			this.onEditRecord();
			this.onCloseEditDialog();
		},
		onSetAddDialog: function() {
			this._onSetAddDialog("TestUIApp2.view.edition.fragment.CreateSubscriptionDialog");
		},
		onSetEditDialog: function() {
			this._onSetEditDialog("TestUIApp2.view.edition.fragment.EditSubscriptionDialog");
		},
		onOpenAddDialog: function() {
			this.onSetAddDialog();
			var oSubTypeComboBox = sap.ui.getCore().byId("subscriptionTypeCbId");
    		oSubTypeComboBox.bindElement({
    			path: "/subscriptiontypes"
			});
			var oCustomerComboBox = sap.ui.getCore().byId("customerCbId");
    		oCustomerComboBox.bindElement({
    			path: "/customers"
			});
			this._onOpenAddDialog();
		},
		onOpenEditDialog: function() {
			this.onSetEditDialog();
			this.onBindTable();
			this.onSetComboBoxEntityValue();
			this._onOpenEditDialog();
		},
		onCloseAddDialog: function() {
			this._onCloseAddDialog();
		},
		onCloseEditDialog: function() {
			this._onCloseEditDialog();
		},
		onBindModel: function() {
			var oSubscriptionTable = this.oCntrl.byId("subscriptionTableId");
    		oSubscriptionTable.bindElement({
    			path: "/subscriptions"
			});
		},
		onBindTable: function() {
			var EditSubscriptionFrag = sap.ui.getCore().byId("EditSubscriptionFragId");
			EditSubscriptionFrag.bindElement({ 
 				path: this.selectedPath,
				model: this.modelName
			});
		},
		onSetModel: function(){
			this._setModel();
			this.onBindModel();
		},
		onSetComboBoxEntityValue: function(){
			var EditSubscriptionFrag = sap.ui.getCore().byId("EditSubscriptionFragId");
			var oCtx = EditSubscriptionFrag.getBindingContext("subscriptions");
			sap.ui.getCore().byId("esubscriptionTypeCbId").setSelectedKey(oCtx.getProperty("subscriptionType/subscriptionTypeId"));
			sap.ui.getCore().byId("ecustomerCbId").setSelectedKey(oCtx.getProperty("customerID"));
		}
	});
});