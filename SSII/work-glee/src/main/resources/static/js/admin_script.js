jQuery().ready(function(){
	mainPage();
});

function mainPage(){
	loadCustomOffers();
	jQuery("#bottom-offers").hide();
	jQuery("#users").hide();
	jQuery("#offers").hide();
	jQuery("#offer").hide();
	jQuery("#custom-offers").show();
	jQuery("#menu-item-2").removeClass("active");
	jQuery("#menu-item-3").removeClass("active");
	jQuery("#menu-item-4").removeClass("active");
	jQuery("#menu-item-1").addClass("active");
	jQuery("#logout").removeClass("active");
}

function showOffers(){
	loadOffers();
	jQuery("#bottom-offers").hide();
	jQuery("#custom-offers").hide();
	jQuery("#users").hide();
	jQuery("#offer").hide();
	jQuery("#offers").show();
	jQuery("#menu-item-1").removeClass("active");
	jQuery("#menu-item-2").removeClass("active");
	jQuery("#menu-item-4").removeClass("active");
	jQuery("#menu-item-3").addClass("active");
	jQuery("#logout").removeClass("active");
}

function showBottomOffers(){
	loadBottomOffers();
	jQuery("#offers").hide();
	jQuery("#custom-offers").hide();
	jQuery("#users").hide();
	jQuery("#user").hide();
	jQuery("#bottom-offers").show();
	jQuery("#menu-item-1").removeClass("active");
	jQuery("#menu-item-3").removeClass("active");
	jQuery("#menu-item-4").removeClass("active");
	jQuery("#menu-item-2").addClass("active");
	jQuery("#logout").removeClass("active");
}

function showUsers(){
	loadUsers();
	jQuery("#offers").hide();
	jQuery("#bottom-offers").hide();
	jQuery("#custom-offers").hide();
	jQuery("#offer").hide();
	jQuery("#users").show();
	jQuery("#menu-item-1").removeClass("active");
	jQuery("#menu-item-2").removeClass("active");
	jQuery("#menu-item-3").removeClass("active");
	jQuery("#menu-item-4").addClass("active");
	jQuery("#logout").removeClass("active");
}

function showOfferInfo(id){
	loadOfferInfo(id);
	jQuery("#offers").hide();
	jQuery("#bottom-offers").hide();
	jQuery("#custom-offers").hide();
	jQuery("#users").hide();
	jQuery("#offer").show();
	jQuery("#menu-item-1").removeClass("active");
	jQuery("#menu-item-2").removeClass("active");
	jQuery("#menu-item-3").removeClass("active");
	jQuery("#menu-item-4").removeClass("active");
	jQuery("#logout").removeClass("active");
}

//offers with most inscribers
function loadOffers(){
    jQuery.ajax({
        url: "http://localhost:8080/offersByNumInscriptions/"
    }).then(function(response,status){
    	jQuery("#offers-table tbody").empty();
        jQuery(response).each(function(index,value){
        	jQuery.ajax({
        		url: "http://localhost:8080/offers/"+value['offerId']+"/company"
        	}).then(function(company){
        		var text = '<tr>'
        		+ "<td>"+company['companyName']+"</td>"
        		+ "<td>"+value['title']+"</td>"
        		+ "<td>"+value['numApplicants']+"</td><td>"
        		+ "<input class='edit-btn' type='button' onclick='showOfferInfo("+value['offerId']+")' value='Ver Ficha'/>"
        		+"</td></tr>";
        		jQuery("#offers-table tbody").append(text);
        	});
        });
    });
}

//offers with 0 inscribers
function loadBottomOffers(){
	jQuery.ajax({
		url: "http://localhost:8080/bottomOffers/"
	}).then(function(response,status){
		jQuery("#bottom-offers-table tbody").empty();
		jQuery(response).each(function(index,value){
			jQuery.ajax({
				url: "http://localhost:8080/offers/"+value['offerId']+"/company"
			}).then(function(company){
				var text = '<tr>'
				+ "<td>"+company['companyName']+"</td>"
				+ "<td>"+value['title']+"</td><td>"
				+ "<input class='edit-btn' type='button' onclick='showOfferInfo("+value['offerId']+")' value='Ver Ficha'/>"
				+"</td></tr>";
				jQuery("#bottom-offers-table tbody").append(text);
			});
		});
	});
}

//load offer by province
function loadCustomOffers(){
	jQuery.ajax({
		url: "http://localhost:8080/offersByProvince/"
	}).then(function(response,status){
		jQuery("#custom-offers-table tbody").empty();
		jQuery(response).each(function(index,value){
			jQuery.ajax({
				url: "http://localhost:8080/offers/"+value['offerId']+"/company"
			}).then(function(company){
				var text = '<tr>'
				+'</tr>';
				//numero de ofertas y el salario medio
				jQuery("#custom-offers-table tbody").append(text);
			});
		});
	});
}

//users with most inscriptions
function loadUsers(){
	jQuery.ajax({
		url: "http://localhost:8080/usersByNumOffers"
	}).then(function(response,status){
		jQuery("#users-table tbody").empty();
		jQuery(response).each(function(index,value){
			var text = '<tr>'
			+'<td>'+value['name']+'</td>'
			+'<td>'+value['surname']+'</td>'
			+'<td>'+value['city']+'</td>'
			+'<td>'+value['province']+'</td>'
			+'<td>'+value['numOffers']+'</td>'
			+'</tr>';
			jQuery("#users-table tbody").append(text);
		});
	});
}

function loadOfferInfo(id){
	jQuery.ajax({
		url: "http://localhost:8080/offers/"+id+"/"
	}).then(function(response,status){
		jQuery("#offer-table tbody").empty();
		jQuery(response).each(function(index,value){
			var activada = value['visibility'];
			var text = '<tr>'
			+'<td>'+value['title']+'</td>'
			+'<td>'+value['description']+'</td>'
			+'<td>'+value['income']+'</td>'
			+'<td>'+value['city']+','+value['province']+'</td>'
			+'<td>'+dateConverter(value['dueDate'].substring(0,10))+'</td>';
			if(activada) text += '<td>Activada</td>';
			else text += '<td>Desactivada</td>';
			text += '<td><input class="return-btn" type="button" onclick="javascript:mainPage()" value="Volver" />';
			text+='</td></tr>';
			jQuery("#offer-table tbody").append(text);
		});
	});
}

function dateConverter(date){
	var aux = date.split("-");
	var newDate = aux[2]+"/"+aux[1]+"/"+aux[0];
	return newDate;
}