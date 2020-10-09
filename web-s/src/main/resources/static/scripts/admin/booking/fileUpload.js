$(function(){
	
	var today = new Date();
	var lastMonth = new Date(today.getFullYear(), today.getMonth()-1, 1);
	var nextMonth = new Date(today.getFullYear(), today.getMonth()+2, 0);
	
/*	$("#targetMonth").prop('value', setMonth(today));
	$("#targetMonth").prop('min', setMonth(lastMonth));
	$("#targetMonth").prop('max', setMonth(nextMonth));
	
	function setMonth(date) {
	    var yyyy = date.getFullYear();
	    var mm = ("0" + (date.getMonth() + 1)).slice(-2);
	    var yyyymm = yyyy+'-'+mm;

		return yyyymm;	
	}
	*/
	$(".calInput").datepicker({
        changeMonth: true,
        changeYear: true,
        minDate: lastMonth,
        maxDate: nextMonth,
		showButtonPanel: true
    });

	$('.calInput').keypress(function(e) {
	    e.preventDefault();
	});
	
	$('.calInput').focus(function(e) {
	    $(this).blur();
	});
});