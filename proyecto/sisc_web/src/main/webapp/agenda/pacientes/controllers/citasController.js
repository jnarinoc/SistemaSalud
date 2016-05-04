/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//modulo
var app = angular.module('sisc_web');

app.filter("getFormatofecha", function () {
    return function (input) {
        var res = new Date(input);
        return res.getDate() + '-' + (res.getMonth() + 1) + '-' + res.getFullYear();

    }
});
app.filter("getFormatoHora", function () {
    return function (input) {
        var res = new Date(input);
        return res.getHours() + ':' + (res.getMinutes()) + ':' + res.getSeconds();

    }
});


app.controller('citasController',
        function ($scope, $http, $stateParams, $route) {





            ////////////////////////////////////////////////////////////////////
            $scope.listaCitasPaciente = {};
            /**
             *  Traer la lista de citas de un paciente
             */
            var data_citasPaciente = $http.get('/SiscAgenda/api/paciente/' + $stateParams.idPaciente + '/listaCitas');
            data_citasPaciente.then(function (result) {
                $scope.listaCitasPaciente = result.data;
            });
            ////////////////////////////////////////////////////////////////////


            ////////////////////////////////////////////////////////////////////
            $scope.esconderMensajeCitaSeleccionada = true;
            /**
             * provicional mientras tanto <esconderMensajeCitaSeleccionada>
             * @returns {undefined}
             */
            $scope.mostrarMensajeCitaSeleccionada = function () {
                $scope.esconderMensajeCitaSeleccionada = !$scope.esconderMensajeCitaSeleccionada;
            }
            ////////////////////////////////////////////////////////////////////


            ////////////////////////////////////////////////////////////////////
            $scope.informacionCita = null;
            $scope.mensajesCita = {};
            /**
             * Mostrar una cita detallada, que fue seleccionada por el paciente
             */
            $scope.mostrarUnaCitaDetallada = function (informacionCita) {
                //alert("entro a mostrar una cita mediante un click");
                $scope.informacionCita = informacionCita;

                $('#message-box-sound-2').show();
                $('#audio-fail').get(0).play();

                $scope.mensajesCita =
                        {
                            msn_citaSeleccionada1: 'MUY BIEN!!! ',
                            msn_citaSeleccionada2: 'Has seleccionado una cita correctamente'
                        };
            }
            ////////////////////////////////////////////////////////////////////


            ////////////////////////////////////////////////////////////////////
            $scope.posicionFila = -1;

            /**
             * No cancelar mi cita que fue seleccionada en el boton cancelar
             * @returns {undefined}
             */
            $scope.confirmacionNOCancelarCita = function () {
                $('#mb-signout').hide();
                console.log("*** no cancelo mi cita");
                $scope.posicionFila = -1;
            };

            /**
             * Confirmacion para poder cancelar una cita que fue seleccionada
             * @param {type} index
             * @param {type} cita
             * @returns {undefined}
             */
            $scope.confirmacionCancelarCita = function (index, cita) {
                $('#mb-signout').show();
                console.log("***" + index);
                $scope.posicionFila = index;
                //citaEscogida = cita;
            };
            /**
             * Si la confirmacion de eliminar la cita es TRUE, entonces
             * se cancelara la cita del paciente
             * @returns {undefined}
             */
            $scope.cancelarCita = function () {
                /**
                 * Cancelar la cita seleccionada por un paciente.
                 */
                if ($scope.posicionFila >= 0) {
                    console.log("*** popsicio0nFila = " + $scope.posicionFila + "  ..  ");


                    var configServicePost = {
                        headers: {
                            'Content-Type': 'application/json;charset=utf-8;'
                        }
                    }

                    console.log("idCita-->" + JSON.stringify($scope.listaCitasPaciente[$scope.posicionFila].idCita));
                    console.log("...");
                    var idCita = $scope.listaCitasPaciente[$scope.posicionFila].idCita + "";
                    //$http.post('/SiscAgenda/api/paciente/cancelarCita', idCita, configServicePost)
                    $http.post('/SiscAgenda/api/paciente/' + idCita + '/cancelarCita', configServicePost)
                            .success(function (data, status, headers, config) {

                                $('#mb-signout').hide();
                                $scope.mensajesCita =
                                        {
                                            msn_citaSeleccionada1: 'MUY BIEN!!! ',
                                            msn_citaSeleccionada2: 'Has cancelado una cita correctamente'
                                        };
                                //$scope.loadData();       
                                $scope.loadData = function () {
                                    $http.get('/SiscAgenda/api/paciente/').success(function (data) {
                                        $scope.listaCitasPaciente = data;
                                    });
                                };

                            })
                            .error(function (data, status, header, config) {
                                //$('#message-box-warning').show();
                                alert("ERROR: Noo se puede cancelar la cita...");


//                                var actualiza = data_citasPaciente;
//                                $scope.reloadRoute = function () {
//                                    $route.reload();
//                                }


                            });
                } else {
                    console.log("problemas ... ELSE");
                }
            };

            /**
             * Recargar la pagina, cuando paciente cancele una cita, entonces
             * esa cita cancelada no se le mostrara
             * @returns {undefined}
             */
            //Recargar
            $scope.loadData=null;
            $scope.loadData = function () {
                $http.get('/SiscAgenda/api/paciente/').success(function (data) {
                    $scope.listaCitasPaciente = data;
                });
            };






            //fin <citasController>
        });

