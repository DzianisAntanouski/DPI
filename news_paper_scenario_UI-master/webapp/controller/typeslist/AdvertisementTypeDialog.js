sap.ui.define([
	"TestUIApp2/controller/BaseObjController",
	"sap/m/MessageToast"
], function(BaseObjController, MessageToast) {
	"use strict";
	return BaseObjController.extend("TestUIApp2.controller.typeslist.AdvertisementTypeDialog", {
		 constructor: function(oCntrl) {
		 	this._onInit("advertisementtypes", oCntrl);
		 },
		onCreateRecord: function(){
			var newAdvertisementType = {};
			newAdvertisementType.description = sap.ui.getCore().byId("AdvertisementTypeDescriptionId").getValue();
			newAdvertisementType.price = sap.ui.getCore().byId("AdvertisementTypePriceId").getValue();
			this._onCreateRecord(this.obj_url, newAdvertisementType);
		},
		onDeleteRecord: function(){
			this._onDeleteRecord(this.sel_obj_url);
		},
		onEditRecord: function(){
			var editAdvertisementType = this.oView.getModel(this.modelName).getProperty(this.onGetSelectedPath());
			this._onEditRecord(this.sel_obj_url, editAdvertisementType);
		},
		onBindElement: function(){
			var EditAdvertisementTypeFrag = sap.ui.getCore().byId("EditAdvertisementTypeFragId");
			EditAdvertisementTypeFrag.bindElement({ 
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
			this._onSetAddDialog("TestUIApp2.view.typeslist.fragment.CreateAdvertisementTypeDialog");
		},
		onSetEditDialog: function() {
			this._onSetEditDialog("TestUIApp2.view.typeslist.fragment.EditAdvertisementTypeDialog");
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
			var oAdvertisementTypeTable = this.oCntrl.byId("advertisementTypeTableId");
    		oAdvertisementTypeTable.bindElement({
    			path: "/advertisementtypes"
			});
		},
		onBindTable: function() {
			var EditAdvertisementTypeFrag = sap.ui.getCore().byId("EditAdvertisementTypeFragId");
			EditAdvertisementTypeFrag.bindElement({ 
 				path: this.selectedPath,
				model: "advertisementtypes"
			});
		},
		onSetModel: function(){
			this.onBindModel();
		}
	});
});