sap.ui.define([
	"TestUIApp2/controller/BaseObjController",
	"sap/m/MessageToast"
], function(BaseObjController, MessageToast) {
	"use strict";
	return BaseObjController.extend("TestUIApp2.controller.edition.ArticleDialog", {
		 constructor: function(oCntrl) {
		 	this._onInit("articles", oCntrl);
		 },
		onCreateRecord: function(){
			var newArticle = {};
			newArticle.description = sap.ui.getCore().byId("aarticleDescriptionId").getValue();
			newArticle.author = sap.ui.getCore().byId("aarticleAuthorId").getValue();
			this._onCreateRecord(this.obj_url, newArticle);
		},
		onDeleteRecord: function(oCtx){
			this._onDeleteRecord(this.sel_obj_url);
		},
		onEditRecord: function(){
			var editArticle = this.oView.getModel(this.modelName).getProperty(this.onGetSelectedPath());
			this._onEditRecord(this.sel_obj_url, editArticle);
		},
		onBindElement: function(){
			var EditArticleFrag = sap.ui.getCore().byId("EditArticleFragId");
			EditArticleFrag.bindElement({ 
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
			this._onSetAddDialog("TestUIApp2.view.edition.fragment.CreateArticleDialog");
		},
		onSetEditDialog: function() {
			this._onSetEditDialog("TestUIApp2.view.edition.fragment.EditArticleDialog");
		},
		onOpenAddDialog: function() {
			this.onSetAddDialog();
			this._onOpenAddDialog();
		},
		onOpenEditDialog: function() {
			this.onSetEditDialog();
			this.onBindTable();
			this._onOpenEditDialog();
		},
		onCloseAddDialog: function() {
			this._onCloseAddDialog();
		},
		onCloseEditDialog: function() {
			this._onCloseEditDialog();
		},
		onBindModel: function() {
			var oArticleTable = this.oCntrl.byId("articleTableId");
    		oArticleTable.bindElement({
    			path: "/articles"
			});
		},
		onBindTable: function() {
			var EditArticleFrag = sap.ui.getCore().byId("EditArticleFragId");
			EditArticleFrag.bindElement({ 
 				path: this.selectedPath,
				model: "articles"
			});
		},
		onSetModel: function(){
			this._setModel();
			this.onBindModel();
		}
	});
});