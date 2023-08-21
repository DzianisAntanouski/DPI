sap.ui.define([
	"TestUIApp2/controller/BaseObjController",
	"sap/m/MessageToast"
], function(BaseObjController, MessageToast) {
	"use strict";
	return BaseObjController.extend("TestUIApp2.controller.edition.OrderDialog", {
		 constructor: function(oCntrl) {
		 	this._onInit("orders", oCntrl);
		 },
		onCreateRecord: function(){
			var newOrder = {};
			newOrder.orderTitle = sap.ui.getCore().byId("orderTitleId").getValue();
			this._onCreateRecord(this.obj_url, newOrder);
		},
		onDeleteRecord: function(){
			this._onDeleteRecord(this.sel_obj_url);
		},
		onEditRecord: function(){
			var editOrder = this.oView.getModel(this.modelName).getProperty(this.onGetSelectedPath());
			this._onEditRecord(this.sel_obj_url, editOrder);
		},
		onBindElement: function(){
			var EditOrderFrag = sap.ui.getCore().byId("EditOrderFragId");
			EditOrderFrag.bindElement({ 
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
			this._onSetAddDialog("TestUIApp2.view.customer.fragment.CreateOrderDialog");
		},
		onSetEditDialog: function() {
			this._onSetEditDialog("TestUIApp2.view.customer.fragment.EditOrderDialog");
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
			var oOrderTable = this.oCntrl.byId("orderTableId");
    		oOrderTable.bindElement({
    			path: "/order"
			});
		},
		onBindTable: function() {
			var EditOrderFrag = sap.ui.getCore().byId("EditOrderFragId");
			EditOrderFrag.bindElement({ 
 				path: this.selectedPath,
				model: this.modelName
			});
		},
		onSetModel: function(){
			this._setModel();
			this.onBindModel();
		}
	});
});