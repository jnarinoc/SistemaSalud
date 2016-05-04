'use strict';

var myModule = angular.module('sisc_web');
// Service that provides persons operations
myModule.factory('medicamentoService', function ($resource) {
	return $resource('http://127.0.0.1:8080/hc/api/medicamento/');
});

myModule.factory('cirugiaService', function ($resource) {
	return $resource('http://127.0.0.1:8080/hc/api/cirugia/');
});

myModule.factory('tratamientoService', function ($resource) {
	return $resource('http://127.0.0.1:8080/hc/api/tratamiento/');
});

myModule.factory('examenService', function ($resource) {
	return $resource('http://127.0.0.1:8080/hc/api/examen/');
});

myModule.factory('incapacidadService', function ($resource) {
	return $resource('http://127.0.0.1:8080/hc/api/incapacidad/');
});