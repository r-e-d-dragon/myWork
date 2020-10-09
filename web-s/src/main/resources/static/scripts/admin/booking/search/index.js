$(function(){
	
	/*var today = new Date();	
	var lastDayOfNextMonth = new Date(today.getFullYear(), today.getMonth()+2, 0);

	$("#reservationDate").prop('min', setDate(today));
	$("#reservationDate").prop('max', setDate(lastDayOfNextMonth));
	
	var valDate = $("#reservationDate").attr('value');
	if(valDate != null || valDate != ''){
		$("#reservationDate").attr("value", valDate.replace(/[\/]/g, '-'));
	}
	
	function setDate(date) {
	    var yyyy = date.getFullYear();
	    var mm = ("0" + (date.getMonth() + 1)).slice(-2);
	    var dd = ("0" + date.getDate()).slice(-2);
	    var yyyymmdd = yyyy+'-'+mm+'-'+dd;

		return yyyymmdd;	
	}*/
	
	$(function(){

    $(".calInput").datepicker({
        changeMonth: true,
        changeYear: true,
		yearRange: "-100:+100",
        minDate: new Date(1920, 01, 01),
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
	
	
});