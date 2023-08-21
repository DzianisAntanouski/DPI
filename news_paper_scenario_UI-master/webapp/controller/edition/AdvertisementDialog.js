sap.ui.define([
	"TestUIApp2/controller/BaseObjController",
	"sap/m/MessageToast",
	'sap/ui/model/Filter'
], function(BaseObjController, MessageToast, Filter) {
	"use strict";
	return BaseObjController.extend("TestUIApp2.controller.edition.AdvertisementDialog", {
		 constructor: function(oCntrl) {
		 	this._onInit("advertisements", oCntrl);
		 },
		onCreateRecord: function(){
			var newAdv = {};
			newAdv.advertisementTypeId = sap.ui.getCore().byId("aaadvertisementTypeCbId").getSelectedKey();
			newAdv.description = sap.ui.getCore().byId("aadescriptionId").getValue();
			newAdv.author = sap.ui.getCore().byId("aaauthorId").getValue();
			var new_adv_url =this.obj_url+newAdv.advertisementTypeId;
			this._onCreateRecord(new_adv_url, newAdv);
		},
		onDeleteRecord: function(){
			this._onDeleteRecord(this.obj_url+this.selectedItem);
		},
		onEditRecord: function(){
			var editAdv = this.oView.getModel(this.modelName).getProperty(this.onGetSelectedPath());
			var edit_adv_url = this.obj_url+this.selectedItem+"/"+editAdv.advertisementType.advertisementTypeId;
			this._onEditRecord(edit_adv_url, editAdv);
		},
		onBindElement: function(){
			var EditAdvertisementFrag = sap.ui.getCore().byId("EditAdvertisementFragId");
			EditAdvertisementFrag.bindElement({ 
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
			this._onSetAddDialog("TestUIApp2.view.edition.fragment.CreateAdvertisementDialog");
		},
		onSetEditDialog: function() {
			this._onSetEditDialog("TestUIApp2.view.edition.fragment.EditAdvertisementDialog");
		},
		onOpenAddDialog: function() {
			this.onSetAddDialog();
			var oSubTypeComboBox = sap.ui.getCore().byId("aaadvertisementTypeCbId");
    		oSubTypeComboBox.bindElement({
    			path: "/advertisementtypes"
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
			var oAdvertisementTable = this.oCntrl.byId("advertisementTableId");
    		oAdvertisementTable.bindElement({
    			path: "/advertisements"
			});
		},
		onBindTable: function() {
			var EditAdvertisementFrag = sap.ui.getCore().byId("EditAdvertisementFragId");
			EditAdvertisementFrag.bindElement({ 
 				path: this.selectedPath,
				model: this.modelName
			});
		},
		onSetModel: function(){
			this._setModel();
			this.onBindModel();
		},
		onSetComboBoxEntityValue: function(){
			var EditAdvertisementFrag = sap.ui.getCore().byId("EditAdvertisementFragId");
			var oCtx = EditAdvertisementFrag.getBindingContext(this.modelName);
			sap.ui.getCore().byId("eadvertisementTypeCbId").setSelectedKey(oCtx.getProperty("advertisementType/advertisementTypeId"));
		}
	});
});