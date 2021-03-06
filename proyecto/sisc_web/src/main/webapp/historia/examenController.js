var app = angular.module('sisc_web');
// Create a controller with name clientesListController to bind to the grid section.
app.controller('examenController', function ($scope, $rootScope,$state ,$timeout, examenService,$modal,cabeceraService, modalService) {
    // Initialize required information: sorting, the first page to show and the grid options.

    $scope.myData = [];



    cabeceraService.get({idcita:localStorage.getItem('idCita')}).$promise.then(
      function (data) {
    console.log("get cabeceraService");
    $timeout(function() {
     
      $scope.nombre = data.data[0].nombre;
      $scope.fechanac = data.data[0].fechanac;
      $scope.identificacion = data.data[0].identificacion;
      $scope.correo = data.data[0].correo;
      $scope.$apply();
    }, 300);
      },

      function () {
    console.log("get FAIL cabeceraService");
      });


    $scope.gridOptions = {

        data: 'myData',
        columnDefs: [
            { field: 'cita', displayName: 'cita' ,   visible: false},  
            { field: 'examen', displayName: 'examen',
                      enableCellEdit: false,
                      displayField : 'examen',
                      valueField: 'examen',
                      width: 140,
                      editableCellTemplate: '<select id="s2" name="s2" ng-model="COL_FIELD" class="dropdown-toggle" style="height: 100%;width: 100%;"> ' +
                      '<option ng-repeat="mc in exmn" value={{mc.idMedicamento}}>{{mc.nombreMedicamento}}</option></select>'
            },

            { field: 'detalles', displayName: 'detalles' ,  enableCellEdit: true},

            { field: 'observaciones', displayName: 'observaciones' ,  enableCellEdit: true},

            {field: 'Eliminar', displayName:'', width: 115,
                cellTemplate : '<div class="ui-grid-cell-contents"> <button ng-click="deleteRow()" style="margin-left: 10px;" class="btn btn-danger btn-rounded btn-sm"><span class="fa fa-times"></span>Eliminar</button></div>'
            }
        ],

        canSelectRows : false,
        multiSelect: false,
        selectedItems: [],
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('modificarUsuario', $scope.gridOptions.selectedItems[0].usuaUsua);
                //console.log('Se emitio evento <modificarUsuario> ');
                //console.log($scope.gridOptions.selectedItems[0].usuaUsua);
            }
        }
    };

    rowCount = 0;
    var newRow = null;

    $scope.onAddRow = function(){

    var modalInstance = $modal.open({
      templateUrl: 'historia/modalexamenes.html',
      controller: 'modalexamenController',

    });
    modalInstance.result.then(function(data){
      $rootScope.$broadcast('refreshGrid');
    });


    };

    $scope.guardar = function(){

        var i = 0;
        for(i=0;i< $scope.myData.length; i++){
          if ($scope.myData[i].examen_name !== undefined){
            $scope.myData[i].examen = $scope.myData[i].examen_name;
          }
          $scope.myData[i].examen = parseInt($scope.myData[i].examen);
          delete $scope.myData[i].fechageneracion;
          delete $scope.myData[i].examen_name;
        }
        examenService.save($scope.myData).$promise.then(
        function () {
          // // Broadcast the event to refresh the grid.
          // $("#s2").hide();
          $rootScope.$broadcast('refreshGrid');
          // // Broadcast the event to display a save message.
          $rootScope.$broadcast('usuarioSaved');
          
        },
        function () {
          // Broadcast the event for a server error.
          $rootScope.$broadcast('error');
        });
    };

    $scope.deleteRow = function() {
       var index = this.row.rowIndex;
       $scope.gridOptions.selectItem(index, false);
       $scope.myData.splice(index, 1);
    };



    $scope.searchTextChanged = function(){
      console.log('Ingreso a funcion searchTextChanged');
    };

    // Refresh the grid, calling the appropriate rest method.
    $scope.refreshGrid = function () {
        var listUsuariosArgs = {
            idcita:localStorage.getItem('idCita')
        };

        examenService.get(listUsuariosArgs, function (data) {
          var examen_id = 0;
          for (var i = 0; i < data.data.length; i++) {
            examen_id = data.data[i].examen;
            data.data[i].examen = data.data[i].examen_name
            data.data[i].examen_name = examen_id
          };
          $scope.myData = data.data;
          console.log(data.data)
          
        });
    };

    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.

    
    $scope.updateRow = function(row){
      var usuaUsua = row.entity.usuaUsua;
      $state.go("modificarUsuario", {'usuaUsua':usuaUsua});
    };

    // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
    // This also works for the first page access, since we assign the initial sorting in the initialize section.
    $scope.$watch('sortInfo', function () {
        $scope.usuarios = {currentPage: 1};
        $scope.refreshGrid();
    }, true);

    // Do something when the grid is sorted.
    // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
    // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
      $scope.sortInfo = sortInfo;
    });

    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
      $scope.refreshGrid();
    });
    
    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });
    
    // Picks us the event broadcasted when the person is deleted from the grid and perform the actual person delete by
    // calling the appropiate rest service.
    $scope.$on('deleteUsuario', function (event, id) {
      console.log('Evento eliminar usuario :' + id);
      examenService.delete({usuaUsua: id}).$promise.then(
          function () {
              // Broadcast the event to refresh the grid.
              $rootScope.$broadcast('refreshGrid');
              // Broadcast the event to display a delete message.
              $rootScope.$broadcast('usuarioDeleted');
              //$scope.clearForm();
          },
          function () {
              // Broadcast the event for a server error.
              $rootScope.$broadcast('error');
          });
    });

    examenService.get().$promise.then(
      function (data) {
        console.log("get examenService");
        $timeout(function() {
          $scope.exmn = data.data;
          console.log($scope.exmn);
          $scope.$apply();
        }, 300);
      },
      function () {
        console.log("get FAIL");
      });
});

app.controller('modalexamenController',function($scope, $rootScope, $state, $timeout, examenService, modalService, $modalInstance){
  $scope.examenes = "";
  $scope.mod = {};

    examenService.get().$promise.then(
          function (data) {
            $timeout(function() {
              $scope.examenes = data.data;
              $scope.$apply();
            }, 300);
          },
          function () {
            console.log("get FAIL");
    });

    $scope.salvarnuevoexamen = function(){
      $scope.mod.cita = parseInt(localStorage.getItem('idCita'));
        console.log($scope.mod);
        $scope.mod.examen = parseInt($scope.mod.examen);
        examenService.save([$scope.mod]).$promise.then(
        function () {
         $modalInstance.close($scope.mod);
          
        },
        function () {
          console.log("FAIL");
          // Broadcast the event for a server error.
          $rootScope.$broadcast('error');
        });
         //alert($scope.formulaModal + $scope.medicamentoModal);

    };


});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
app.controller('alertMessagesController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('usuarioSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record saved successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('usuarioDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record deleted successfully!' }
        ];
    });

    // Picks up the event to display a server error message.
    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});
