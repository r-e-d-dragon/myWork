$(function(){

    $(".calInput").datepicker({
        changeMonth: true,
        changeYear: true,
		yearRange: "-100:+0",
        minDate: new Date(1920, 01, 01),
        maxDate: new Date()
    });

	$('.calInput').keypress(function(e) {
	    e.preventDefault();
	});
	
	$('.calInput').focus(function(e) {
	    $(this).blur();
	});

});
