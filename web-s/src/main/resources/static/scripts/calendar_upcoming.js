$(function(){

    $(".calInput").datepicker({
        changeMonth: true,
        changeYear: true,
        //constrainInput: true,
        minDate: new Date(),
        maxDate: new Date(new Date().setFullYear(new Date().getFullYear() + 5)),
		showButtonPanel: true
    });

	$('.calInput').keypress(function(e) {
	    e.preventDefault();
	});
	
	$('.calInput').focus(function(e) {
	    $(this).blur();
	});

});
