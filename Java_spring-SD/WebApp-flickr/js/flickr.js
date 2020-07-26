//variables del programa
var selected_contact;
var selected_user;

//variables para las peticiones a flickr
var urlFlickr = 'https://api.flickr.com/services/rest/?';

//pagina principal, mostrar fotos recientes
function mainPage(){
	//cargar fotos recientes
	recent_photos();
}

//lista de contactos
function contact_list(){
	$("contactList").empty();
	$.getJSON(urlFlickr
		+'&method=flickr.contacts.getPublicList'
		+'&api_key='+api_key,
		+'&user_id'+user_id,
		'&format=json&nojsoncallback=1',
		load_contact_list
	);
}

function load_contact_list(list){
	var aux;
	if($("#contactList #lista").length){}
	else{
		//crear lista
		aux = $("#contactList").append("<select id='lista'></select>");
	}
	for(c in list){
		aux = $("#contactList #lista");
		aux.append("<option value='"+c.nsid+"'>"+c.username+"</option>");
		console.log(c.username);
	}
}

//imagenes recientes
function recent_photos(){
	$("#mainPage").empty();
	$.getJSON(urlFlickr
		+'&method=flickr.photos.getContactsPublicPhotos'
		+'&api_key='+api_key
		+'&user_id='+user_id
		+'&extras=date_upload,date_taken, realname',
		'&format=json&nojsoncallback=1',
		load_recent_photos
	);
	if($("#mainPage tr").length == 0){
		alert("No hay imagenes recientes");
	}
}

function load_recent_photos(info){
	if(info.photos.photo.length==0){
		alert("No hay imágenes!");
	}
	else{
		//obtenemos las fotos y su información
		for (var i=0;i<info.photos.photo.length;i++) {
			var item = info.photos.photo[i];
			var aux;
			
			//obtenemos username, realname y nsid del contacto
			var user = item.username;
			var real = item.realname;
			owner=item.owner;
			
			//obtener fecha
			var date = new Date(item.datetaken).getTime();
			date += parseInt(item.dateupload);
			date = new Date(date);
			
			if(isRecent(date)){
				if($("#mainPage #"+user).length){}
				else{
					//añadir fila y columnas
					aux = $("#mainPage").append('<tr id="'+user+'"></tr>');
					aux = $("#mainPage #"+user);
					aux.append("<tr id='info'></tr>");
					var aux2 = $("#mainPage #"+user+" #info");
					aux2.append($('<label id="username">'+user+'</label>'));
					aux2.append($('<label id="realname">'+real+'</label>'));
					aux.append($('<tr id="images"></tr>'));
				}
				var url = 'https://farm'+item.farm+".staticflickr.com/"+item.server
					+'/'+item.id+'_'+item.secret+'_m.jpg';
				console.debug(url);
				//añadir imagen en la pagina y fecha
				aux ='#mainPage #'+user+' #images';
				aux = aux.append("<td class='container'></td>");
				aux.append("<img class='container' id='"+item.id+"'src='"+url+"'</img>");
				aux.append("<div class='centered'>"+date+"</div>");
			}
		}	
	}
}

// para saber si es una imagen recientemente publicada, en los ultimos 20 dias
function isRecent(date){
	var aux,y1, m1, d1;
	var y2,m2,d2;
	
	//obtener fecha actual
	aux = new Date();
	y = aux.getFullYear();
	m = aux.getMonth();
	d = aux.getTime();
	
	//obtener datos de la fecha de la imagen
	y2 = date.getFullYear();
	m2 = date.getMonth();
	d2 = date.getTime();
	
	if(y1 - y2 < 2){
		if(Math.abs(m1 - m2) < 2){
			if(Math.abs(d1 - d2) < 20) return true;
		}
	}
	return false;
}

//imagenes de familia y amigos
function family_and_friends(){
	$("#family_friends").empty();
	$.getJSON(urlFlickr
		+'&method=flickr.photos.getContactsPublicPhotos'
		+'&api_key='+api_key
		+'&user_id='+user_id
		+'&just_friends=1'
		+'&extras=date_upload,date_taken, realname',
		'&format=json&nojsoncallback=1',
		load_photos
	);
}

function load_photos(info){
	if(info.photos.photo.length==0){
		alert("No hay imágenes!");
	}
	else{
		//obtenemos las fotos y su información
		for (var i=0;i<info.photos.photo.length;i++) {
			var item = info.photos.photo[i];
			var aux;
			
			//obtenemos username, realname y nsid del contacto
			var user = item.username;
			var real = item.realname;
			owner=item.owner;
			
			//obtener fecha
			var date = new Date(item.datetaken).getTime();
			date += parseInt(item.dateupload);
			date = new Date(date);
			
			var url = 'https://farm'+item.farm+".staticflickr.com/"+item.server
						+'/'+item.id+'_'+item.secret+'_z.jpg';
			console.debug(url);
			
			if($("#family_friends #"+user).length){}
			else{
				//añadir fila y columnas
				aux = $("#family_friends").append('<table id="'+user+'"></table>');
				aux = $("#family_friends #"+user);
				aux.append("<tr id='info'></tr>");
				
				var aux2 = $("#family_friends #"+user+" #info");
				aux2.append($('<label id="username">'+user+'</label>'));
				aux2.append($('<label id="realname">'+real+'</label>'));
				aux.append($('<tr id="images"></tr>'));
			}
			//añadir imagen en la pagina y fecha
			aux = $('#family_friends #'+user+' #images');
			aux = aux.append("<td class='container'></td>");
			aux.append("<img class='container' id='"+item.id+"'src='"+url+"'</img>");
			aux.append("<div class='centered'>"+date+"</div>");
		}	
	}
}

//imagenes de todos los contactos
function all_contacts(){
	$("#contacts").empty();
	$.getJSON(urlFlickr
		+'&method=flickr.photos.getContactsPublicPhotos'
		+'&api_key='+api_key
		+'&user_id='+user_id
		+'&extras=date_upload,date_taken, realname',
		'&format=json&nojsoncallback=1',
		load_photos_all
	);
}

//obtener fotos de todos
function load_photos_all(info){
	if(info.photos.photo.length==0){
		alert("No hay imágenes!");
	}
	else{
		//obtenemos las fotos y su información
		for (var i=0;i<info.photos.photo.length;i++) {
			var item = info.photos.photo[i];
			var aux;
			//obtenemos username, realname y nsid del contacto
			var user = item.username;
			var real = item.realname;
			owner=item.owner;
			
			//obtener fecha
			var date = new Date(item.datetaken).getTime();
			date += parseInt(item.dateupload);
			date = new Date(date);
			
			//comprovar si ya existe la fila
			if($("#contacts #"+user).length){}
			else{
				//añadir fila y columnas
				aux = $("#contacts").append('<tr id="'+user+'"></tr>');
				aux = $("#contacts #"+user);
				aux.append("<tr id='info'></tr>");
				
				var aux2 = $("#contacts #"+user+" #info");
				aux2.append($('<label id="username">'+user+'</label>'));
				aux2.append($('<label id="realname">'+real+'</label>'));
				aux.append($('<tr id="images"></tr>'));
			}
			
			//small: m,n  medium: z,c  large: b,h
			var url = 'https://farm'+item.farm+".staticflickr.com/"+item.server
						+'/'+item.id+'_'+item.secret+'_z.jpg';
			console.debug(url);
			
			//añadir imagen en la pagina y fecha
			aux = $('#contacts #'+user+' #images');
			aux = aux.append("<td class='container'></td>");
			aux.append("<img class='container' id='"+item.id+"'src='"+url+"'</img>");
			aux.append("<div class='centered'>"+date+"</div>");
		}	
	}
}

//cargar fotos de un contacto
function contact_photos(){
	$.getJSON(urlFlickr
		+'&method=flickr.people.getPublicPhotos'
		+'&api_key='+api_key
		+'&user_id='+selected_contact
		+'&extras=date_upload,date_taken,realname',
		'&format=json&nojsoncallback=1',
		load_contact_photos
	);
}

function load_contact_photos(info){
	if(info.photos.photo.length==0){
		alert("No hay imágenes!");
	}
	else{
		var aux;
		//obtener username, realname
		var user = selected_user;
		var owner = selected_contact;
		var real = info.realname;
			
		//comprovar ya existe la fila
		if($("#one_contact #"+selected_user).length);
		else{
			//añadir fila y columnas
			aux = $("#one_contact").append('<tr id="'+user+'"</tr>');
			aux = $("#one_contact #"+user);
			var aux2 = $("#one_contact #"+user+" #info");
			aux2.append($('<label id="username">'+user+'</label>'));
			aux2.append($('<label id="realname">'+real+'</label>'));
			aux.append($('<tr id="images"></tr>'));
		}
		
		for (var i=0;i<info.photos.photo.length;i++) {
			var item = info.photos.photo[i];	
			
			//obtener fecha
			var date = new Date(item.datetaken).getTime();
			date += parseInt(item.dateupload);
			date = new Date(date);
			
			var url = 'https://farm'+item.farm+".staticflickr.com/"+item.server
						+'/'+item.id+'_'+item.secret+'_z.jpg';
			console.debug(url);
			
			date = date.toString().split("GMT",2);
			
			//añadir imagen en la pagina
			aux = $('#one_contact #'+user+' #images');
			aux = aux.append("<td class='container'></td>");
			aux.append("<img class='container' id='"+item.id+"'src='"+url+"'</img>");
			aux.append("<div class='centered'>"+date+"</div>");
		}	
	}
}

//menu
function menuEffect(){
	$(this).on("hover"),
	function(){
		$(this).css("backgroundColor","lightblue");
	}, 
	function(){
		$(this).css("backgroundColor","");
	}
}

//programa principal
$().ready(function(){
	//añadir eventos
	$("#options").bind("hover",menuEffect);
	
	$("#home").bind("click",function(){
		mainPage();
		//ocultar elementos
		$("#contacts").hide();	
		$("#one_contact").hide();
		$("#family_friends").hide();
		$("#mainPage").show();	
	});
	
	$("#family_and_friends").bind("click",function(){
		family_and_friends();
		//ocultar elementos
		$("#contacts").hide();	
		$("#one_contact").hide();
		$("#mainPage").hide();	
		$("#family_friends").show();
	});
	
	$("#all").bind("click",function(){
		all_contacts();
		//ocultar elementos
		$("#one_contact").hide();
		$("#family_friends").hide();
		$("#mainPage").hide();	
		$("#contacts").show();
	});
	
	$("#selectContact").bind("hover",
		function(){
			$("#selectContact").bind("click",function(){
				$("#contactList").slideDown();
				if($("#contactList #lista").length){}
				else alert("No tiene contactos");
			});
		},
		function(){
			$("#contactList").slideUp();
		}
	);
	
	$("#selectContact #list").bind("selected",
		function(){
			selected_contact = $("#lista :selected").val();
			selected_user = $("#lista").text();
			contact_photos();
		}
	);
	
	//ocultar elementos
	$("#contacts").hide();	
	$("#one_contact").hide();
	$("#family_friends").hide();
	$("#mainPage").show();	
	mainPage();
	contact_list();
});