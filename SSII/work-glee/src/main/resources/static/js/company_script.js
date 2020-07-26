jQuery(document).ready(function(){
	mainPage();
});

function mainPage(){
	loadCompanyInfo();
	loadOffers();
	jQuery("#create-offers").hide();
	jQuery("#edit-offer").hide();
	jQuery("#show-offers").show();
}

function showOffers(){
	loadOffers();
	jQuery("#create-offers").hide();
	jQuery("#edit-offer").hide();
	jQuery("#show-offers").show();
	jQuery("#menu-item-1").removeClass("active");
	jQuery("#menu-item-3").removeClass("active");
	jQuery("#menu-item-2").addClass("active");
	jQuery("#logout").removeClass("active");
}

function createNewOffer(){
	jQuery("#show-offers").hide();
	jQuery("#edit-offer").hide();
	jQuery("#create-offers").show();
	jQuery("#menu-item-1").removeClass("active");
	jQuery("#menu-item-2").removeClass("active");
	jQuery("#menu-item-3").addClass("active");
	jQuery("#logout").removeClass("active");
}

function loadOffers(){
	jQuery.ajax({
		url: "http://localhost:8080/companies/"+userId+"/offers"
	}).then(function(data) {
		jQuery('#offers-table tbody').empty();
		jQuery.each(data._embedded.offers, function (index, value) {
			var offer_link, offerId, activeText, active;
			offer_link = value['_links']['self']['href'];
			offerId = offer_link.substring(29,offer_link.length);
			active = value['visible'];
			if(active) activeText = 'Activada';
			else activeText = 'Desactivada';

			var text = '<tr>'
				+'<td>'+value['title']+'</td>'
				+'<td>'+value['description']+'</td>'
				+'<td>'+value['income']+'</td>'
				+'<td>'+value['city']+', '+value['province']+'</td>'
				+'<td>'+dateConverter(value['dueDate'].substring(0,10))+'</td>'
				+'<td> <span>'+activeText+'</span>'+'</td>'
				+'<td>'
					+ '<input type="button" class="edit-btn" value="Editar" onclick="editOffer('+offerId+')" />'
					+ '<input type="button" class="delete-btn" value="Eliminar" onclick="deleteOffer('+offerId+')" />'
				+ '</td>'
			+'</tr>';
			jQuery('#offers-table tbody').append(text);
		});
    });
}

function loadCompanyInfo(){
	jQuery.ajax({
		url: "http://localhost:8080/companies/"+userId+"/"
	}).then(function(value){
		jQuery('#userInfo').empty();
		var text = '<h2><label>Empresa</label></h2>'
			+'<a>'+value['companyName']+'</a>'
			+'<a>'+value['email']+'</a>'
			+'<a>'+value['description']+'</a>'
			+'<a>'+value['city']+', '+value['province']+'</a>';
		jQuery('#userInfo').append(text);
	});
}

function addOffer(){
	var aux = jQuery("#new-offer-form #income").val();
	jQuery("#new-offer-form #income").val(aux.replace(",","."));
	var form_data = jQuery("#new-offer-form").serialize();
	jQuery.ajax({
		method: "POST",
		url : "http://localhost:8080/addNewOffer/",
		data : form_data
	}).done(function(response){
		jQuery("#new-offer-form")[0].reset();
		showOffers();
	});
}

function editOffer(id){
    jQuery.ajax({
    	url: "http://localhost:8080/offers/"+id+"/"
    }).then(function(value){
    	jQuery("#edit-offer").empty();
    	var text = '<h2><label>Modificar Oferta</label></h2>';
        var isVisible = value['visible'];
        text += '<form class="input-form" id="edit-offer-form" action="javascript:changeOfferDetails('+id+')">'
			+ '<label>Titulo</label>'
			+ '<input type="text" id="title" name="title" value="'+value['title'] +'"/>'
			+ '<label >Descripción</label>'
			+ '<input type="text" id="description" name="description" value="'+value['description']+'"/>'
			+ '<label >Salario</label>'
			+ '<input type="text" id="income" name="income" value="'+ value['income'] +'" class="form-control"/>'
			+ '<label>Ciudad</label>'
			+ '<input type="text" id="city" name="city" value="'+ value['city'] +'" />'
			+ '<label>Provincia</label>'
            + '<input type="text" id="province" name="province" value="'+value['province']+'" />'
			+ '<label >Fecha Limite</label>'
			+ '<input type="date" id="due_date" name="due_date" value="'
					+ value['dueDate'] +'"/>'
			+ '<div class="ck-box">'
				+ '<label>Activar Oferta </label>';
				if(isVisible) text += '<input type="checkbox" id="visible" name="visible" checked/>';
				else text += '<input type="checkbox" id="visible" name="visible"/>';
			text += '</div>'
			+ '<div>'
				+ '<input type="submit" class="edit-btn" value="Guardar"/>'
				//+ '<input type="button" class="return-btn" value="Volver" onclick="window.location.href=\'/\'"/>'
				+ '<input type="button" class="return-btn" value="Volver" onclick="showOffers()"/>'
			+ '</div>'
		+ '</form>';

		jQuery("#edit-offer").append(text);
		jQuery("#show-offers").hide();
		jQuery("#edit-offer").show();
    });
}

function changeOfferDetails(id){
	var aux = jQuery("#edit-offer-form #income").val();
    jQuery("#edit-offer-form #income").val(aux.replace(",","."));

	var form_data = jQuery("#edit-offer-form").serialize();
	jQuery.ajax({
		method: "PUT",
		url : "http://localhost:8080/modify_offer/"+id+"/",
		data : form_data
	}).done(function(response){
		jQuery("#edit-offer-form")[0].reset();
		showOffers();
	});
}

function deleteOffer(id){
	jQuery.ajax({
		url: 'http://localhost:8080/deleteOffer/'+id+'/',
		method: 'DELETE',
		success: function(data) {
			loadOffers();
		}
    });
}

function dateConverter(date){
	var aux = date.split("-");
	var newDate = aux[2]+"/"+aux[1]+"/"+aux[0];
	return newDate;
}

