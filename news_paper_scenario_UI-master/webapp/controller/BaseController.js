sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/core/routing/History"
], function(Controller, History) {
	"use strict";

	return Controller.extend("TestUIApp2.controller.BaseController", {

		getRouter: function() {
			return sap.ui.core.UIComponent.getRouterFor(this);
		},
		
		getMyMetadata: function() {
			return "/destinations/Testodata";
		},

		onNavHome: function(oEvent) {
			this.getRouter().navTo("Home", {}, true /*no history*/ );
		},
		onNavMagazine: function(oEvent) {
			this.getRouter().navTo("Magazines", {}, true /*no history*/ );
		},

		onNavBack: function(oEvent) {
			var oHistory, sPreviousHash;

			oHistory = History.getInstance();
			sPreviousHash = oHistory.getPreviousHash();

			if (sPreviousHash !== undefined) {
				window.history.go(-1);
			} else {
				this.getRouter().navTo("Home", {}, true /*no history*/ );
			}
		}

	});

});