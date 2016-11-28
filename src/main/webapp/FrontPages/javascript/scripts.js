$(function() {

    $('[data-toggle="tooltip"]').tooltip(); 

    $(".imageButton").mouseup(function(){
	    $(this).blur();
	})

	$('img.bgM').css("visibility", "visible");

	$('img[delayed-src]').each(function(){
		$(this).attr('src', $(this).attr('delayed-src'));
	});

    $('#slideshow').cycle({
	fx: 'fade',
	speed: 1800,
	timeout:  7000,
	delay: 4000
	});

});