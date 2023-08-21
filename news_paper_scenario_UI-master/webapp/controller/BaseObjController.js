sap.ui.define([
	"TestUIApp2/controller/BaseController",
	"sap/m/MessageToast",
	"sap/ui/model/json/JSONModel"
], function(BaseController, MessageToast,JSONModel) {
	"use strict";
//	Base Object Class
//	attributes
//  	viewName, selectedItem, selectedPath, obj_url, sel_obj_url
//      modelName, oView, addDialog, editDialog, oCntrl

	return BaseController.extend("TestUIApp2.controller.BaseObjController", {
		 _onInit: function(modelName, oCntrl) {
		 	this.modelName = modelName;
		 	this.oView = oCntrl.getView();
		 	this.oCntrl = oCntrl;
		 },
	//Set/Get		
		onSetSelectedItem: function(selectedItem){
			this.selectedItem = selectedItem;
		},
		onSetSelectedPath: function(selectedPath){
			this.selectedPath = "/"+selectedPath;
		},
		onSetObjUrl: function(obj_url){
			this.obj_url = obj_url;
		},
		onGetSelectedItem: function(){
			return this.selectedItem;
		},
		onGetSelectedPath: function(){
			return this.selectedPath;
		},
		onGetObjUrl: function(){
			return this.obj_url;
		},
	//	Dialog
		_onGetAddDialog: function() {
			return this.addDialog;	
		},
		_onSetAddDialog: function(viewName) {
			if (!this.addDialog) {
				this.addDialog = sap.ui.xmlfragment(viewName, this);
				this.oView.addDependent(this.addDialog);
			}
		},
		_onGetEditDialog: function() {
			return this.editDialog;	
		},
		_onSetEditDialog: function(viewName) {
			if (!this.editDialog) {
				this.editDialog = sap.ui.xmlfragment(viewName, this);
				this.oView.addDependent(this.editDialog);
			}
		},
		_onOpenAddDialog: function() {
			this._onGetAddDialog().open();
		},
		_onCloseAddDialog: function() {
			this._onGetAddDialog().close();
		},
		_onOpenEditDialog: function() {
			this._onGetEditDialog().open();
		},
		_onCloseEditDialog: function() {
			this._onGetEditDialog().close();
		},
		//Record
		onSelectRecord: function(selectedItem, selectedPath){
			this.onSetSelectedItem(selectedItem);
			this.onSetSelectedPath(selectedPath);
			this.sel_obj_url=this.obj_url+selectedItem;
		},
		_onCreateRecord: function(url, newRecord){
			var that = this;
			sap.ui.core.BusyIndicator.show(1);
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: url,
				data: JSON.stringify(newRecord),
				headers : { "x-csrf-token" : that.getOwnerComponent().sCsrfToken },
				success: function(oResponse) {
					MessageToast.show("New item successfully created", 
						{ duration: 50 } );
					that._onLoadData();	
					sap.ui.core.BusyIndicator.hide();
				},
				error: function(oResponse) {
					MessageToast.show("Error: " + oResponse.responseText);
					sap.ui.core.BusyIndicator.hide();
				}
			});
		},
		_onDeleteRecord: function(url){
			var that = this;
			sap.ui.core.BusyIndicator.show(1);
			$.ajax({
				type: "DELETE",
				contentType: "application/json",
				url: url,
				headers : { "x-csrf-token" : that.getOwnerComponent().sCsrfToken },
				success: function(oResponse) {
					MessageToast.show("Item successfully deleted",
						{ duration: 50 } );
					that._onLoadData();	
					sap.ui.core.BusyIndicator.hide();
				},
				error: function(oResponse) {
					MessageToast.show("Error: " + oResponse.responseText);
					sap.ui.core.BusyIndicator.hide();
				}
			});
		},
		_onEditRecord: function(url, editRecord){
			var that = this;
			$.ajax({
				type: "PUT",
				contentType: "application/json",
				url: url,
				data: JSON.stringify(editRecord),
				headers : { "x-csrf-token" : that.getOwnerComponent().sCsrfToken },
				success: function(oResponse) {
					MessageToast.show("Item successfully updated", 
						{duration: 50});
					that._onLoadData();		
				},
				error: function(oResponse) {
					MessageToast.show("Error: " + oResponse.responseText);
				}
			});
		},
		_onLoadData: function(){
			this.oView.getModel(this.modelName).setData({});
			this.oView.getModel(this.modelName).loadData(this.obj_url);
		},
		_setModel: function(){
			var model = new JSONModel(this.obj_url, {
				json: true,
				loadMetadataAsync: true
			});
			model.setDefaultBindingMode(sap.ui.model.BindingMode.TwoWay);
			this.oView.setModel(model, this.modelName);
		}
	});
});