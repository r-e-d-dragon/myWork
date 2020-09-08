$(function(){
	
	if($('#pointExpirationTermCd').val() == '02'){
		setCarriablePointType();
		if($('#carriablePointTypeCd').val() == '06'){
			setCustom();
			setPointExpirationTerm();
			}
		}else{
			setMonthlyPointType();
			if($('#monthlyPointTypeCd').val() == '06'){
				setCustom();
				setPointExpirationTermNull();
			}else{
				setNotCustom();
			}
	}
	
	
	$('#pointExpirationTermCd').change(function(e) {
		if($('#pointExpirationTermCd').val() == '02'){
			setCarriablePointType();
			if($('#carriablePointTypeCd').val() == '06'){
				setCustom();
				setPointExpirationTerm();
				}
		}else{
			setMonthlyPointType();
			if($('#monthlyPointTypeCd').val() == '06'){
				setCustom();
				setPointExpirationTermNull();
			}else{
				setNotCustom();
			}
		}
	});
	
	$('#carriablePointTypeCd').change(function(e) {
		if($('#carriablePointTypeCd').val() == '06'){
				setCustom();
				setPointExpirationTerm();
			}else{
				setNotCustom();
			}
	});
	
	$('#monthlyPointTypeCd').change(function(e) {
		if($('#monthlyPointTypeCd').val() == '06'){
				setCustom();
				setPointExpirationTermNull();
			}else{
				setNotCustom();
			}
	});

	
	function setCustom(){
		$('.forCustomPoint').show();
		$("#memo").attr("disabled", false);
		$("#pointVariation").attr("disabled", false);
	}
	
	function setNotCustom(){
		$('.forCustomPoint').hide();
		$("#memo").attr("disabled", true);
		$("#pointVariation").attr("disabled", true);
		setPointExpirationTermNull()
	}
	
	function setCarriablePointType(){
		$('.carriablePointTypeCd').show();
		$("#carriablePointTypeCd").attr("disabled", false);
		$('.monthlyPointTypeCd').hide();
		$("#monthlyPointTypeCd").attr("disabled", true);
	}
	
	function setMonthlyPointType(){
		$('.carriablePointTypeCd').hide();
		$("#carriablePointTypeCd").attr("disabled", true);
		$('.monthlyPointTypeCd').show();
		$("#monthlyPointTypeCd").attr("disabled", false);
	}
	
	function setPointExpirationTerm(){
		//$('.forCarriablePoint').show();
		//$("#termStartDate").attr("disabled", false);
		//$("#termEndDate").attr("disabled", false);
	}
	
	function setPointExpirationTermNull(){		
		//$('.forCarriablePoint').hide();
		//$("#termStartDate").attr("disabled", true);
		//$("#termEndDate").attr("disabled", true);
		
	}
	
	
	
});