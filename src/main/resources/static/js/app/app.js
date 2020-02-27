'use strict'

var ufcApp = angular.module('ufc', [ 'ui.bootstrap', 'ufc.controllers',
		'ufc.services' ]);
ufcApp.constant("CONSTANTS", {
	/*
	getUserByIdUrl : "/user/getUser/",
	getAllUsers : "/user/getAllUsers",
	saveUser : "/user/saveUser",
	*/
	getFightsMissingUrl: "http://localhost:4646/ufc/rest/fight/missing/scoreUrl"
});