'use strict';
var app = angular.module('sisc_registro');

app.controller('medicosController', function ($scope, $rootScope, $stateParams, $state, medicoService, modalService) {
  
  $scope.medico={};
  
  if (angular.isDefined($stateParams.idPersona)){
    console.log('Médico a modificar, ID = '+ $stateParams.idPersona);
    medicoService.get({id: $stateParams.idPersona}).$promise.then(
      function (data) {
        $scope.medico = data;
        //A partir de Angular 1.3, ng-model requiere un objeto de tipo Date valido, no acepta un String
        $scope.medico.fechaNacimiento = new Date($scope.medico.fechaNacimiento); 
      },
      function () {
        // Broadcast the event for a server error.
        $rootScope.$broadcast('error');
      });
  }
  
  //TODO Reemplazar por consulta de items asociados a la enumeracion Java
  $scope.listaTiposIdentificacion = [
      {id: 'CC', name: 'Cedula'},
      {id: 'TI', name: 'T. Identidad'},
      {id: 'NIT', name: 'NIT'}
    ];
    
  //TODO Reemplazar por consulta de items asociados a la enumeracion en Java
  $scope.listaGeneros =[
    {id:'M', label:'Masculino'},
    {id:'F', label:'Femenino'}
  ];
  
  $scope.listaRH =[
    {id:'+', label:'Positivo'},
    {id:'-', label:'Negativo'}
  ];
  // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
  $scope.clearForm = function () {
    $scope.medico = null;
    // Resets the form validation state.
    $scope.medicoForm.$setPristine();
    // Broadcast the event to also clear the grid selection.
    $rootScope.$broadcast('clear');
  };

  // Calls the rest method to save a Medico.
  $scope.updateMedico = function () {
    medicoService.save($scope.medico).$promise.then(
    function () {
      // Broadcast the event to refresh the grid.
      $rootScope.$broadcast('refreshGrid');
      // Broadcast the event to display a save message.
      $rootScope.$broadcast('medicoSaved');
      
    },
    function () {
      // Broadcast the event for a server error.
      $rootScope.$broadcast('error');
    });
  };

  // Picks up the event broadcasted when the person is selected from the grid and perform the person load by calling
  // the appropiate rest service.
  $scope.$on('medicoSelected', function (event, id) {
    console.log('Médico seleccionado, ID = '+ id);
    $scope.medico = medicoService.get({id: id});
  });
  
  $scope.$on('medicoSaved', function(){
    var modalOptions = {
          //closeButtonText: 'Cancelar',
          actionButtonText: 'Continuar',
          headerText: 'Resultado de operación',
          bodyText: 'Operación existosa!'
      };

      modalService.showModal({}, modalOptions).then(function () {
        $scope.clearForm();
        $state.go('registroMedicos');
      });
  });
  
  $scope.cancelar = function(){
    $state.go('registroMedicos');
  };
  
});
