$(function(){
    $("#checkAll").change(function(){
        if($("#checkAll").is(":checked")){
            $('input:checkbox[id^="cellItem"]').not(this).prop('checked', this.checked);
        }else{
            $('input:checkbox[id^="cellItem"]').not(this).prop('checked', this.checked);
        }
    });


/*	$('input:checkbox').change(function(){
		var count = $('[id^="cellItem"]:checked').length;
		var selectedValue= $("#customerDataDLCd").val();

		if(selectedValue == '02' && count < 1){
			  $('#href_download_customer_data').addClass('disabled');
		  }else{
			  $('#href_download_customer_data').removeClass('disabled');
		  }
	});*/





});
