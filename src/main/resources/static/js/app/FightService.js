'use strict'

angular.module('ufc.services', []).factory('FightService',
		[ "$http", "CONSTANTS", function($http, CONSTANTS) {
			var service = {};
			/*
			service.getUserById = function(userId) {
				var url = CONSTANTS.getUserByIdUrl + userId;
				return $http.get(url);
			}
			service.getAllUsers = function() {
				return $http.get(CONSTANTS.getAllUsers);
			}
			service.saveUser = function(userDto) {
				return $http.post(CONSTANTS.saveUser, userDto);
			}
			*/
			service.getFightsMissingUrl = function() {
				return $http.get(CONSTANTS.getFightsMissingUrl);
			}
			return service;
		} ]);