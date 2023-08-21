sap.ui.define([
	"TestUIApp2/controller/BaseController",
	"sap/m/MessageToast",
	"sap/ui/model/json/JSONModel",
	"TestUIApp2/controller/edition/ArticleDialog",
	"TestUIApp2/controller/edition/SubscriptionDialog",
	"TestUIApp2/controller/edition/AdvertisementDialog"
], function(BaseController, MessageToast,JSONModel, ArticleDialog,SubscriptionDialog,AdvertisementDialog) {
	"use strict";

	return BaseController.extend("TestUIApp2.controller.edition.EditionDetails", {
		onInit: function() {
			var oRouter = this.getRouter();
			oRouter.getRoute("editionDetails").attachMatched(this._onUserObjectMatched, this);
			this.ArticleDialog = new ArticleDialog(this);
			this.SubscriptionDialog = new SubscriptionDialog(this);
			this.AdvertisementDialog = new AdvertisementDialog(this);
		},
		
		_onUserObjectMatched: function(oEvent) {
 			this._selectedItem = oEvent.getParameter("arguments").editionID;
 			this._selectedPath = "/" + oEvent.getParameter("arguments").path;
 			var oProductDetailPanel = this.byId("detailEditionPage");
 			oProductDetailPanel.bindElement({ 
 				path: this._selectedPath,
				model: "editions"
			});
			
			this.setArticleTableModel();
			this.setSubscriptionTableModel();
			this.setAdvertisementTableModel();
		},
		_onBindingChange: function(oEvent) {
			// No data for the binding
			if (!this.getView().getBindingContext()) {
				this.getRouter().getTargets().display("notFound");
			}
		},
		onEditionDelete: function(){
			var itemPath = this.getOwnerComponent()._edit_url;
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
					that.getOwnerComponent().getModel("editions").loadData(that.getOwnerComponent()._edit_url);
				},
				error: function(oResponse) {
					MessageToast.show("Error: " + oResponse.responseText);
				}
			});
		},
		onEditionEdit: function() {
			var oDialog = this._getDialog();
			var EditEditionFrag = sap.ui.getCore().byId("EditEditionFragId");
			EditEditionFrag.bindElement({ 
 				path: this._selectedPath,
				model: "editions"
			});
			oDialog.open();
		},
		
		_getDialog: function() {
			if (!this._oDialog) {
				this._oDialog = sap.ui.xmlfragment("TestUIApp2.view.edition.fragment.EditEditionDialog",this);
				this.getView().addDependent(this._oDialog);
			}
			return this._oDialog;
		},
		onCloseDialog: function() {
			this._getDialog().close();
		},
		
		_onEditSave: function() {
			var changeEdition = {};
			changeEdition = this.getOwnerComponent().getModel("editions").getProperty(this._selectedPath);

			var itemPath = this.getOwnerComponent()._edit_url + this._selectedItem;
			var that = this;
			$.ajax({
			type: "PUT",
				contentType: "application/json",
				url: itemPath,
				data: JSON.stringify(changeEdition),
				headers : { "x-csrf-token" : that.getOwnerComponent().sCsrfToken },
				success: function(oResponse) {
					MessageToast.show("Item successfully updated", 
					{ duration: 100 } );
					that.getOwnerComponent().getModel("editions").loadData(that.getOwnerComponent()._edit_url);
				},
				error: function(oResponse) {
					MessageToast.show("Error: " + oResponse.responseText);
				}
			});
			this.onCloseDialog();
		},
		//Article
		setArticleTableModel: function(){
			this.ArticleDialog.onSetObjUrl(this.getOwnerComponent()._edit_url + this._selectedItem+"/articles/");
			this.ArticleDialog.onSetModel();
		},
		onArticleSelect: function(oEvent){
			var oItem = oEvent.getSource();
			var oCtx = oItem.getBindingContext("articles");
			this.ArticleDialog.onSelectRecord(oCtx.getProperty("articleID"),oCtx.getPath().substr(1));
		},
		onArticleDelete: function(){
			this.ArticleDialog.onDeleteRecord(this.oCtx);
		},
		onArticleAdd: function() {
			this.ArticleDialog.onOpenAddDialog();
		},
		onArticleEdit: function(){
			this.ArticleDialog.onOpenEditDialog();
		},
		// Subscription
		setSubscriptionTableModel: function(){
			this.SubscriptionDialog.onSetObjUrl(this.getOwnerComponent()._cur_mag_url+"subscriptions/");
			this.SubscriptionDialog.onSetModel();
		},
		onSubscriptionSelect: function(oEvent){
			var oItem = oEvent.getSource();
			var oCtx = oItem.getBindingContext("subscriptions");
			this.SubscriptionDialog.onSelectRecord(oCtx.getProperty("subscription_ID"),oCtx.getPath().substr(1));
		},
		onSubscriptionDelete: function(){
			this.SubscriptionDialog.onDeleteRecord();
		},
		onSubscriptionAdd: function() {
			this.SubscriptionDialog.onOpenAddDialog();
		},
		onSubscriptionEdit: function(){
			this.SubscriptionDialog.onOpenEditDialog();
		},
		// Advertisement
		setAdvertisementTableModel: function(){
			this.AdvertisementDialog.onSetObjUrl(this.getOwnerComponent()._cur_mag_url+"advertisements/");
			this.AdvertisementDialog.onSetModel();
		},
		onAdvertisementSelect: function(oEvent){
			var oItem = oEvent.getSource();
			var oCtx = oItem.getBindingContext(this.AdvertisementDialog.modelName);
			this.AdvertisementDialog.onSelectRecord(oCtx.getProperty("advertisement_ID"),oCtx.getPath().substr(1));
		},
		onAdvertisementDelete: function(){
			this.AdvertisementDialog.onDeleteRecord();
		},
		onAdvertisementAdd: function() {
			this.AdvertisementDialog.onOpenAddDialog();
		},
		onAdvertisementEdit: function(){
			this.AdvertisementDialog.onOpenEditDialog();
		}
	});
});