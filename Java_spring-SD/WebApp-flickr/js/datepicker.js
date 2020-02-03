$(function() { 
   $.datepicker.regional['es'] = {
      closeText: 'Cerrar',
      prevText: '<Ant',
      nextText: 'Sig>',
      currentText: 'Hoy',
      monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
      monthNamesShort: ['Ene','Feb','Mar','Abr', 'May','Jun','Jul','Ago','Sep', 'Oct','Nov','Dic'],
      dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
      dayNamesShort: ['Dom','Lun','Mar','Mié','Juv','Vie','Sáb'],
      dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
      weekHeader: 'Sm',
      dateFormat: 'dd-mm-yyyy',
      firstDay: 1,
      isRTL: false,
      showMonthAfterYear: false,
      yearSuffix: ''
   };
   
   $.datepicker.setDefaults($.datepicker.regional["es"]);
   $( "#locale" ).change(
      function() {                   
      
         function fechMax(){
         $( "#fMax" ).datepicker( "option",
            $.datepicker.regional[ $( this ).val() ] );
         
         }

         function fechMin(){
         $( "#fMin" ).datepicker( "option",
            $.datepicker.regional[ $( this ).val() ] );
         }
         
         function fechMaxTaken(){
         $  ( "#fMaxCap" ).datepicker( "option",$.datepicker.regional[ $( this ).val() ] );
         }           
                              
         function fechMinTaken(){
               $( "#fMinCap" ).datepicker( "option",
            $.datepicker.regional[ $( this ).val() ] );
         }
      });
   
      $("#fMax").datepicker();
      $("#fMin").datepicker();
      $("#fMaxCap").datepicker()
      $("#fMinCap").datepicker();

      /*Cada vez que el contenido de cada una de las cajas de texto de fechas cambie, se va actualizando el valor de su variable asociada*/
      $("#fMax").change(mostrarFechaMax);
      function mostrarFechaMax() {
         // Se actualiza la variable asociada a ésta caja de texto
         fechMax= $(this).datepicker('getDate').getTime() / 1000;
      }

      $("#fMin").change(mostrarFechaMin);

      function mostrarFechaMin() {
    	  fechMin= $(this).datepicker('getDate').getTime() / 1000;
      }   

      $("#fMaxCap").change(mostrarFechaMaxTaken);
      function mostrarFechaMaxTaken() {
         fechMaxTaken= $(this).datepicker('getDate').getTime() / 1000;
      }

      $("#fMinCap").change(mostrarFechaMinTaken);
      function mostrarFechaMinTaken() {
         fechMinTaken= $(this).datepicker('getDate').getTime() / 1000;
      } 
   }
)