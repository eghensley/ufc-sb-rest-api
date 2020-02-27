'use strict'

var module = angular.module('ufc.controllers', []);
module.controller("FightController", [ "$scope", "FightService",
		function($scope, FightService) {

			$scope.userDto = {
				userId : null,
				userName : null,
				skillDtos : []
			};
			$scope.skills = [];
			
			$scope.getResponse = {
				status: null,
				timestamp: null,
				errorMSg: null,
				response: null
			}
			
			var missingUrlGetPromise = FightService.getFightsMissingUrl();
			missingUrlGetPromise.then(function(res){
				$scope.MissingUrlFights = res.data.response;
				$scope.MissingUrlFightMessage = res.data.errorMsg;
			});
				
			/*
			UserService.getUserById(1).then(function(value) {
				console.log(value.data);
			}, function(reason) {
				console.log("error occured");
			}, function(value) {
				console.log("no callback");
			});

			$scope.saveUser = function() {
				$scope.userDto.skillDtos = $scope.skills.map(skill => {
					return {skillId: null, skillName: skill};
				});
				UserService.saveUser($scope.userDto).then(function() {
					console.log("works");
					UserService.getAllUsers().then(function(value) {
						$scope.allUsers= value.data;
					}, function(reason) {
						console.log("error occured");
					}, function(value) {
						console.log("no callback");
					});

					$scope.skills = [];
					$scope.userDto = {
						userId : null,
						userName : null,
						skillDtos : []
					};
				}, function(reason) {
					console.log("error occured");
				}, function(value) {
					console.log("no callback");
				});
			}
			*/
		} ]);