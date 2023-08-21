sap.ui.define([
	"TestUIApp2/controller/BaseController",
	"sap/m/MessageToast",
	"sap/ui/model/json/JSONModel",
	"TestUIApp2/controller/customer/OrderDialog"
], function(BaseController, MessageToast,JSONModel,OrderDialog) {
	"use strict";

	return BaseController.extend("TestUIApp2.controller.customer.CustomerDetails", {
		onInit: function() {
			var oRouter = this.getRouter();
			oRouter.getRoute("customerDetails").attachMatched(this._onUserObjectMatched, this);
			this.OrderDialog = new OrderDialog(this);
		},
		
		_onUserObjectMatched: function(oEvent) {
 			this._selectedItem = oEvent.getParameter("arguments").customerID;
 			this._selectedPath = "/" + oEvent.getParameter("arguments").path;
 			var oProductDetailPanel = this.byId("detailCustomerPage");
 			
			oProductDetailPanel.bindElement({ 
 				path: this._selectedPath,
				model: "customers"
			});	
			this.setOrderTableModel();
		},

		_onBindingChange: function(oEvent) {
			// No data for the binding
			if (!this.getView().getBindingContext()) {
				this.getRouter().getTargets().display("notFound");
			}
		},
		
		onCustomerDelete: function(){
			var itemPath = this.getOwnerComponent()._cust_url;
			if(this._selectedItem !== 0)
			{
				itemPath=itemPath + this._selectedItem;
			}
			var that = this;
			$.ajax({
				type: "DELETE",
				contentType: "application/json",
				url: itemPath,
				headers : { "x-csrf-token" : that.getOwnerComponent().sCsrfToken },
				success: function(oResponse) {
					MessageToast.show("Item successfully deleted",
						{ duration: 100 } );
						that.getOwnerComponent().getModel("customers").loadData(that.getOwnerComponent()._cust_url);
				},
				error: function(oResponse) {
					MessageToast.show("Error: " + oResponse.responseText);
				}
			});
		},
		
		onCustomerEdit: function() {
			var oDialog = this._getDialog();
			var EditCustomerFrag = sap.ui.getCore().byId("EditCustomerFragId");
			EditCustomerFrag.bindElement({ 
 				path: this._selectedPath,
				model: "customers"
			});	
			
			oDialog.open();
		},
		
		_getDialog: function() {
			if (!this._oDialog) {
				this._oDialog = sap.ui.xmlfragment("TestUIApp2.view.customer.fragment.EditCustomerDialog", this);
				this.getView().addDependent(this._oDialog);
			}
			return this._oDialog;
		},
		onCloseDialog: function() {
			this._getDialog().close();
		},
		
		_onCustomerSave: function() {
			var changeCustomer = {};
			changeCustomer = this.getOwnerComponent().getModel("customers").getProperty(this._selectedPath);

			var itemPath = this.getOwnerComponent()._cust_url + this._selectedItem;
			var that = this;
			$.ajax({
			type: "PUT",
				contentType: "application/json",
				url: itemPath,
				data: JSON.stringify(changeCustomer),
				headers : { "x-csrf-token" : that.getOwnerComponent().sCsrfToken },
				success: function(oResponse) {
					MessageToast.show("Item successfully updated", 
					{ duration: 100 } );
					that.getOwnerComponent().getModel("customers").loadData(that.getOwnerComponent()._cust_url);
				},
				error: function(oResponse) {
					MessageToast.show("Error: " + oResponse.responseText);
				}
			});
			this.onCloseDialog();
		},
		//Order
		setOrderTableModel: function(){
			this.OrderDialog.onSetObjUrl(this.getOwnerComponent()._cust_url + this._selectedItem+"/orders/");
			this.OrderDialog.onSetModel();
		},
		onOrderSelect: function(oEvent){
			var oItem = oEvent.getSource();
			var oCtx = oItem.getBindingContext(this.OrderDialog.modelName);
			this.OrderDialog.onSelectRecord(oCtx.getProperty("order_ID"),oCtx.getPath().substr(1));
		},
		onOrderDelete: function(){
			this.OrderDialog.onDeleteRecord();
		},
		onOrderAdd: function() {
			this.OrderDialog.onOpenAddDialog();
		},
		onOrderEdit: function(){
			this.OrderDialog.onOpenEditDialog();
		}
	});
});