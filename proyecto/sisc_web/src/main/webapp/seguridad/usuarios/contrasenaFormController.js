'use strict';
var app = angular.module('sisc_web');
// Create a controller with name personsFormController to bind to the form section.
app.controller('contrasenaFormController', function ($scope, $rootScope, $stateParams, $state,
        usuarioService, modalService, contrasenaService) {

    $scope.usuario = {};

    if (angular.isDefined($stateParams.usuaUsua)) {
        console.log('Usuario a modificar, ID = ' + $stateParams.usuaUsua);
        usuarioService.get({usuaUsua: $stateParams.usuaUsua}).$promise.then(
                function (data) {
                    $scope.usuario = data;
                    //A partir de Angular 1.3, ng-model requiere un objeto de tipo Date valido, no acepta un String
                },
                function () {
                    // Broadcast the event for a server error.
                    $rootScope.$broadcast('error');
                });
    }

    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    $scope.clearForm = function () {
        $scope.usuario = null;
        // Resets the form validation state.
        $scope.usuarioForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clear');
    };

    // Calls the rest method to save a Usuario.
    $scope.updateUsuario = function () {
        usuarioService.get({usuaUsua: $stateParams.usuaUsua}).$promise.then(
                function () {
                    contrasenaService.save($scope.usuario.usuaUsua + "-" + $scope.usuario.usuaPass0 + "-" + $scope.usuario.usuaPass + "-" + $scope.usuario.usuaPass1);
                    $rootScope.$broadcast('refreshGrid');
                    // Broadcast the event to display a save message.
                    $rootScope.$broadcast('usuarioSaved');
                },
                function () {
                    // Broadcast the event for a server error.
                    $rootScope.$broadcast('error');
                });
    };

    // Picks up the event broadcasted when the person is selected from the grid and perform the person load by calling
    // the appropiate rest service.
    $scope.$on('usuarioSelected', function (event, id) {
        console.log('Usuario seleccionado, ID = ' + id);
        $scope.usuario = usuarioService.get({id: id});
    });

    $scope.$on('usuarioSaved', function () {
        var modalOptions = {
            //closeButtonText: 'Cancelar',
            actionButtonText: 'Continuar',
            headerText: 'Resultado de operación',
            bodyText: 'Operación existosa!'
        };

        modalService.showModal({}, modalOptions).then(function () {
            $scope.clearForm();
            $state.go('home');
        });
    });

    $scope.cancelar = function () {
        $state.go('home');
    };

});

app.directive('wjValidationError', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctl) {
            scope.$watch(attrs['wjValidationError'], function (errorMsg) {
                elm[0].setCustomValidity(errorMsg);
                ctl.$setValidity('wjValidationError', errorMsg ? false : true);
            });
        }
    };
});