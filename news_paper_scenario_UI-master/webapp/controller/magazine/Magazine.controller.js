sap.ui.define([
		"TestUIApp2/controller/BaseObjController"
	], function(BaseObjController) {
	"use strict";
	return BaseObjController.extend("TestUIApp2.controller.magazine.Magazine", {
		onInit : function (evt) {
			this._onInit("magazines", this);
			this.onSetObjUrl(this.getOwnerComponent()._mag_url);
			this.onSetModel();
		},

		handleEditPress : function (evt) {
			var oTileContainer = this.getView().byId("tilecontainerId");
			var newValue = !oTileContainer.getEditable();
			oTileContainer.setEditable(newValue);
			evt.getSource().setText(newValue ? "Done" : "Edit");
		},
		onBindModel: function() {
			var oArticleTable = this.oCntrl.byId("tilecontainerId");
    		oArticleTable.bindElement({
    			path: "/magazines"
			});
		},
		onBindTable: function() {
			var EditArticleFrag = sap.ui.getCore().byId("magazineEditId");
			EditArticleFrag.bindElement({ 
 				path: this.selectedPath,
				model: "magazines"
			});
		},
		onSetModel: function(){
			this._setModel();
			this.onBindModel();
		},
		onSelect: function(oEvent) {
			var oItem = oEvent.getSource();
			var oCtx = oItem.getBindingContext("magazines");
			this.getOwnerComponent().createEditionModel(oCtx.getProperty("magazineID"));
			this.getRouter().navTo("Editions", {
					fromTarget: "Magazines"
			});
		},
		handleCreatePress : function (evt) {
			var newMagazine = {};
			newMagazine.description = "NewMagazine";
			this._onCreateRecord(this.obj_url, newMagazine);
		},

		handleTileDelete : function (oEvent) {
			var tile = oEvent.getParameter("tile");
			var oCtx = tile.getBindingContext("magazines");
 			this.onSelectRecord(oCtx.getProperty("magazineID"),oCtx.getPath().substr(1));
			this._onDeleteRecord(this.sel_obj_url);
		}
	});
});