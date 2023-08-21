sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/Device",
	"TestUIApp2/model/models",
	"sap/ui/model/json/JSONModel",
	"sap/m/MessageBox"
], function(UIComponent, Device, models, JSONModel, MessageBox) {
	"use strict";

	return UIComponent.extend("TestUIApp2.Component", {
		metadata: {
			manifest: "json"
		},
		init: function() {
			// call the base component's init function
			UIComponent.prototype.init.apply(this, arguments);
			// set the device model
			this.setModel(models.createDeviceModel(), "device");
			// init router
			this.getRouter().initialize();
//			this.checkWebService(this.getDataUriNeo());
			this.checkWebService(this.getDataUriCF());
		},
		getDataUriNeo: function() {
			return  this.getMetadata().getManifestEntry("sap.app").dataSources["Neo"].uri;
		},
		getDataUriCF: function() {
			return  this.getMetadata().getManifestEntry("sap.app").dataSources["CF"].uri;
		},
		checkWebService: function(url) {
			var that = this;
			this.data_url = url;
			$.ajax({
				type: "GET",
				url: this.data_url,
				headers : { "x-csrf-token" : "fetch" },
				success: function(oResponse) {
					that.createCustomerModel();
					that.createMagazineModel();
					that.createSubTypeModel();
					that.createAdvTypeModel();
				},
				error: function(oResponse) {
					MessageBox.error("Error: " + oResponse.responseText);
				}
			})
			.done( function(data, textStatus, oJqXHR) { 
				that.sCsrfToken = oJqXHR.getResponseHeader('X-Csrf-Token');
			});	
		},
		createObjectModel: function(modelName, url) {
			var Model = new JSONModel(url, {
				json: true,
				loadMetadataAsync: true
			});
			Model.setDefaultBindingMode(sap.ui.model.BindingMode.TwoWay);
    		this.setModel(Model, modelName);
		},
		createCustomerModel: function(){
			this._cust_url = this.data_url+"customers/";
			this.createObjectModel("customers", this._cust_url);
		},
		createMagazineModel: function(){
			this._mag_url = this.data_url+"magazines/";

			this.createObjectModel("magazines", this._mag_url);
		},
		createEditionModel: function(magId){
			this._cur_magID = magId;
			this._cur_mag_url = this._mag_url+magId+"/";
			this._edit_url = this._mag_url+magId+"/editions/";
			this.createObjectModel("editions", this._edit_url);
		},
		createSubTypeModel: function(){
			this._subtype_url = this.data_url+"subscriptionTypes/";
			this.createObjectModel("subscriptiontypes", this._subtype_url);
		},
		createAdvTypeModel: function(){
			this._advtype_url = this.data_url+"advertisementTypes/";
			this.createObjectModel("advertisementtypes", this._advtype_url);
		},
		createUserModel: function(){
			var oModel = new JSONModel({
				json: true,
				loadMetadataAsync: true
			});
			this.setModel(oModel, "user");
			oModel.loadData("/services/userapi/currentUser");
		}
	});
});