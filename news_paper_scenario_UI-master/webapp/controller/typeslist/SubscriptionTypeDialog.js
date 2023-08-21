sap.ui.define([
	"TestUIApp2/controller/BaseObjController",
	"sap/m/MessageToast"
], function(BaseObjController, MessageToast) {
	"use strict";
	return BaseObjController.extend("TestUIApp2.controller.typeslist.SubscriptionTypeDialog", {
		 constructor: function(oCntrl) {
		 	this._onInit("subscriptiontypes", oCntrl);
		 },
		onCreateRecord: function(){
			var newSubscriptionType = {};
			newSubscriptionType.description = sap.ui.getCore().byId("SubscriptionTypeDescriptionId").getValue();
			newSubscriptionType.price = sap.ui.getCore().byId("SubscriptionTypePriceId").getValue();
			newSubscriptionType.oneTime = sap.ui.getCore().byId("SubscriptionTypeOneTimeId").getState();
			this._onCreateRecord(this.obj_url, newSubscriptionType);
		},
		onDeleteRecord: function(){
			this._onDeleteRecord(this.sel_obj_url);
		},
		onEditRecord: function(){
			var editSubscriptionType = this.oView.getModel(this.modelName).getProperty(this.onGetSelectedPath());
			this._onEditRecord(this.sel_obj_url, editSubscriptionType);
		},
		onBindElement: function(){
			var EditSubscriptionTypeFrag = sap.ui.getCore().byId("EditSubscriptionTypeFragId");
			EditSubscriptionTypeFrag.bindElement({ 
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
			this._onSetAddDialog("TestUIApp2.view.typeslist.fragment.CreateSubscriptionTypeDialog");
		},
		onSetEditDialog: function() {
			this._onSetEditDialog("TestUIApp2.view.typeslist.fragment.EditSubscriptionTypeDialog");
		},
		onOpenAddDialog: function() {
			this.onSetAddDialog();
			this._onOpenAddDialog();
		},
		onOpenEditDialog: function() {
			this.onSetEditDialog();
			this.onBindTable();
			this._onOpenEditDialog();
		},
		onCloseAddDialog: function() {
			this._onCloseAddDialog();
		},
		onCloseEditDialog: function() {
			this._onCloseEditDialog();
		},
		onBindModel: function() {
			var oSubscriptionTypeTable = this.oCntrl.byId("subscriptionTypeTableId");
    		oSubscriptionTypeTable.bindElement({
    			path: "/subscriptiontypes"
			});
		},
		onBindTable: function() {
			var EditSubscriptionTypeFrag = sap.ui.getCore().byId("EditSubscriptionTypeFragId");
			EditSubscriptionTypeFrag.bindElement({ 
 				path: this.selectedPath,
				model: "subscriptiontypes"
			});
		},
		onSetModel: function(){
			this.onBindModel();
		}
	});
});