'use strict';
// Declare app level module which depends on views, and components
var app = angular.module('sisc_web', ['ui.router','ngResource','ui.calendar', 'ui.bootstrap','ngGrid','app.utils']);

app.config(['$stateProvider','$urlRouterProvider', function($stateProvider,$urlRouterProvider) {
  $urlRouterProvider.otherwise("/home");
  
  $stateProvider.
    state('home',{
      url: '/home',
      templateUrl: 'home.html'
    })
    
    .state('seguridad',{
       url:'/seguridad',
       templateUrl: 'seguridad/login.html',
       controller: 'loginFormController'
    })
    
    .state('accesos',{
      url:'/accesos',
      templateUrl: 'accesos/accesos.html',
      controller: 'accesosListController'
    })
  
    .state('crearAcceso',{
      url: '/crearAcceso',
      templateUrl: 'accesos/formularioAcceso.html',
      controller: 'accesoFormController'
    })
    
    .state('modificarAcceso',{
      url: '/modificarAcceso',
      templateUrl: 'accesos/formularioAcceso.html',
      controller: 'accesoFormController',
      params : {'acceAcce':null}
    })
    
    .state('grupos',{
      url:'/grupos',
      templateUrl: 'grupos/grupos.html',
      controller: 'gruposListController'
    })
  
    .state('crearGrupo',{
      url: '/crearGrupo',
      templateUrl: 'grupos/formularioGrupo.html',
      controller: 'grupoFormController'
    })
    
    .state('modificarGrupo',{
      url: '/modificarGrupo',
      templateUrl: 'grupos/formularioGrupo.html',
      controller: 'grupoFormController',
      params : {'grupGrup':null}
    })
      
    .state('usuarios',{
      url:'/usuarios',
      templateUrl: 'usuarios/usuarios.html',
      controller: 'usuariosListController'
    })
  
    .state('crearUsuario',{
      url: '/crearUsuario',
      templateUrl: 'usuarios/formularioUsuario.html',
      controller: 'usuarioFormController'
    })
    
    .state('modificarUsuario',{
      url: '/modificarUsuario',
      templateUrl: 'usuarios/formularioUsuario.html',
      controller: 'usuarioFormController',
      params : {'usuaUsua':null}
    })
    
    .state('agenda',{
      url: '/medico/agenda',
      templateUrl: 'agenda/medicos/agendaMedico.html',
      controller:'agendaMedicoContoller',
      params : {'idMedico':'1'}
      
    })
     .state('citasPaciente',{
      url: '/paciente/lcitas',
      templateUrl: 'agenda/pacientes/consultarCitas.html',
      controller: 'citasController',
      params : {'idPaciente':'2'}
    })    
    
    // Registro    
    .state('registroMedicos',{
      url:'/medicos',
      templateUrl: 'registro/medicos/registroMedicos.html',
      controller: 'medicosController'
    })
     
    .state('listarMedicos',{
      url:'/listaMedicos',
      templateUrl: 'registro/medicos/listaMedicos.html',
      controller: 'listaMedicosController'
    })
    
    .state('modificarMedicos',{
      url:'/medicos',
      templateUrl: 'registro/medicos/registroMedicos.html',
      controller: 'medicosController',
      params : {idPersona:null}
    })
    
    
    .state('registroPacientes',{
      url: '/pacientes',
      templateUrl: 'registro/pacientes/registroPacientes.html',
      controller: 'pacientesController'
    })
      
    .state('listarPacientes',{
      url:'/listaPacientes',
      templateUrl: 'registro/pacientes/listaPacientes.html',
      controller: 'listaPacientesController'
    })

    .state('modificarPacientes',{
      url:'/pacientes',
      templateUrl: 'registro/pacientes/registroPacientes.html',
      controller: 'pacientesController',
      params : {idPersona:null}
    })

    
    .state('registroBeneficiarios',{
      url: '/beneficiario',
      templateUrl: 'registro/beneficiario/registroBeneficiarios.html',
      controller: 'beneficiariosController'
    })
    
    .state('listarBeneficiarios',{
      url:'/listaBeneficiarios',
      templateUrl: 'registro/beneficiario/listaBeneficiarios.html',
      controller: 'listaBeneficiariosController'
    })

    .state('modificarBeneficiarios',{
      url:'/ModificacionBeneficiarios',
      templateUrl: 'registro/beneficiario/registroBeneficiarios.html',
      controller: 'pacientesController',
      params : {idPersona:null}
    })
    
   
    .state('registroEps',{
      url: '/RegistroEPS',
      templateUrl: 'registro/eps/registroEps.html',
      controller: 'epsController'
    })
      
    .state('listarEps',{
      url:'/listaEPS',
      templateUrl: 'registro/eps/listaEps.html',
      controller: 'listaEpsController'
    })

    .state('modificarEps',{
      url:'/ModificarEPS',
      templateUrl: 'registro/eps/registroEps.html',
      controller: 'epsController',
      params : {idPersona:null}
    })


    // hc

    .state('menuhc',{
       url:'/menuhc',
       templateUrl: 'historia/menuhc.html',
      // controller: 'loginFormController'
    })
    .state('asignarmedicamento',{
      url:'/historia/asignar-medicamento',
      templateUrl: 'historia/asignarmedicamento.html',
      controller: 'medicamentoController'
    })
    .state('asignarTratamiento',{
      url:'/historia/asignar-tratamiento',
      templateUrl: 'historia/asignarTratamiento.html',
      controller: 'tratamientoController'
    })

    .state('asignarincapacidad',{
      url:'/historia/asignar-incapacidad',
      templateUrl: 'historia/asignarincapacidad.html',
      controller: 'incapacidadController'
    })

    .state('asignarexamen',{
      url:'/historia/asignar-examen',
      templateUrl: 'historia/asignarexamen.html',
      controller: 'examenController'
    })

    .state('asignarCirugia',{
      url:'/historia/asignar-cirugia',
      templateUrl: 'historia/asignarCirugia.html',
      controller: 'cirugiaController'
    })

    ;
    
}]);

