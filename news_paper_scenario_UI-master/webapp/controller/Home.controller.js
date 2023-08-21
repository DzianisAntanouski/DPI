sap.ui.define([
	"TestUIApp2/controller/BaseController"
], function(BaseController) {
	"use strict";

	return BaseController.extend("TestUIApp2.controller.Home", {
		onInit: function() {
			this.getOwnerComponent().createUserModel();
			var oButton = this.byId("userButtonId");
			oButton.bindElement({ path: "/", model: "user" });	
		},
		onDisplayCustomers: function() {
			this.getRouter().navTo("Customers", {
				fromTarget: "Home"
			});
		},
		onDisplayMagazines: function() {
			this.getRouter().navTo("Magazines", {
				fromTarget: "Home"
			});
		},
		onDisplayTypes: function() {
			this.getRouter().navTo("TypesList", {
				fromTarget: "Home"
			});
		},
		onLogoutBtn: function(oEvent) {
			 window.close();
		}
	});
});