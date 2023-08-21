sap.ui.define([
	"TestUIApp2/controller/BaseController",
	"sap/ui/model/json/JSONModel",
	"sap/m/MessageToast"
], function(BaseController,JSONModel,MessageToast) {
	"use strict";

	return BaseController.extend("TestUIApp2.controller.customer.MasterCustomers", {
		onInit: function() {
			var oRouter = this.getRouter();
			oRouter.getRoute("masterCustomers").attachPatternMatched(this._onObjectMatched, this);
			this.onCustomerRead();
		},

		_onObjectMatched: function(oEvent) {
			this.getView().bindElement({
				path: "/customers"
			});
		},
		onSelect: function(oEvent) {
			var oItem = oEvent.getSource();
			var oCtx = oItem.getBindingContext("customers");
 			this.getRouter().navTo("customerDetails", {
 				customerID: oCtx.getProperty("customerID"),
 				path: oCtx.getPath().substr(1)
			});
		},
		_onCustomerSave: function() {
			var newCustomer = {};
			newCustomer.firstName =    sap.ui.getCore().byId("customerFirstNameId").getValue();
			newCustomer.lastName =    sap.ui.getCore().byId("customerLastNameId").getValue();
			newCustomer.phone =   sap.ui.getCore().byId("customerPhoneId").getValue();
			newCustomer.email =   sap.ui.getCore().byId("customerEmailId").getValue();
			newCustomer.address = sap.ui.getCore().byId("customerAddressId").getValue();
			newCustomer.birthday =sap.ui.getCore().byId("customerBirthdateId").getValue();
			var that = this;
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: this.getOwnerComponent()._cust_url,
				data: JSON.stringify(newCustomer),
				headers : { "x-csrf-token" : that.getOwnerComponent().sCsrfToken },
				success: function(oResponse) {
					MessageToast.show("New customer successfully created", 
					{ duration: 100 } );
					that.getOwnerComponent().getModel("customers").loadData(that.getOwnerComponent()._cust_url);
				},
				error: function(oResponse) {
					MessageToast.show("Error: " + oResponse.responseText);
				}
			});
			this.onCloseDialog();
		},

		onCustomerAdd: function() {
			var oDialog = this._getDialog();
			oDialog.open();
		},
		
		onCustomerRead: function() {
			this.getOwnerComponent().getModel("customers").loadData(this.getOwnerComponent()._cust_url);
		},
		
		_getDialog: function() {
			if (!this._oDialog) {
				this._oDialog = sap.ui.xmlfragment("TestUIApp2.view.customer.fragment.CreateCustomerDialog", this);
				this.getView().addDependent(this._oDialog);
			}
			return this._oDialog;
		},
		
		onCloseDialog: function() {
			this._getDialog().close();
		}
	});
});