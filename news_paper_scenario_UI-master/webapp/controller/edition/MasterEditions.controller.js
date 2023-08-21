sap.ui.define([
	"TestUIApp2/controller/BaseController",
	"sap/ui/model/json/JSONModel",
	"sap/m/MessageToast"
], function(BaseController,JSONModel,MessageToast) {
	"use strict";

	return BaseController.extend("TestUIApp2.controller.edition.MasterEditions", {
		onInit: function() {
			var oRouter = this.getRouter();
			oRouter.getRoute("masterEditions").attachPatternMatched(this._onObjectMatched, this);
			this.onEditionRead();
		},
		_onObjectMatched: function(oEvent) {
			this.getView().bindElement({
				path: "/editions"
			});
		},
		onSelect: function(oEvent) {
			var oItem = oEvent.getSource();
			var oCtx = oItem.getBindingContext("editions");
			
 			this.getRouter().navTo("editionDetails", {
 				editionID: oCtx.getProperty("editionID"),
 				path: oCtx.getPath().substr(1)
			});
		},
		_onEditionSave: function() {
			var newEdition = {};
			newEdition.description =    sap.ui.getCore().byId("editionDescriptionId").getValue();
			var that = this;
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: this.getOwnerComponent()._edit_url,
				data: JSON.stringify(newEdition),
				headers : { "x-csrf-token" : that.getOwnerComponent().sCsrfToken },
				success: function(oResponse) {
					MessageToast.show("New edition successfully created", 
					{ duration: 100 } );
					that.getOwnerComponent().getModel("editions").loadData(that.getOwnerComponent()._edit_url);
				},
				error: function(oResponse) {
					MessageToast.show("Error: " + oResponse.responseText);
				}
			});
			this.onCloseDialog();
		},

		onEditionAdd: function() {
			var oDialog = this._getDialog();
			oDialog.open();
		},
		
		onEditionRead: function() {
			this.getOwnerComponent().getModel("editions").loadData(this.getOwnerComponent()._edit_url);
		},
		
		_getDialog: function() {
			if (!this._oDialog) {
				this._oDialog = sap.ui.xmlfragment("TestUIApp2.view.edition.fragment.CreateEditionDialog", this);
				this.getView().addDependent(this._oDialog);
			}
			return this._oDialog;
		},
		
		onCloseDialog: function() {
			this._getDialog().close();
		}
	});
});

	// onSearch: function() {
		// 	// Add search filter
		// 	var filters = [];
		// 	var searchString = this.getView().byId("masterCustomersSearchField").getValue();
		// 	if (searchString && searchString.length > 0) {
		// 		filters = [new sap.ui.model.Filter("CustomerName", sap.ui.model.FilterOperator.Contains, searchString)];
		// 	}
		// 	// Update list binding
		// 	this.getView().byId("masterCustomersList").getBinding("items").filter(filters);
		// },