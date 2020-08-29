$(function(){
	
	if($('#useFlag').val() == '01' && $('#memberTypeCd').val() == '03'){
		setForNormalMember();
	}else{
		setForOtherMembers();
	}
	
	$('#email').change(function(e) {
	    $('#emailConfirm').val("");
	});
	
	
	$('#useFlag').change(function(e) {
		if($('#useFlag').val() == '01' && $('#memberTypeCd').val() == '03'){
			setForNormalMember();
			setMonthlyPointDefault()
		}else{
			setForOtherMembers();
			setMonthlyPointDefault()
		}	    
	});
	
	$('#memberTypeCd').change(function(e) {
		if($('#useFlag').val() == '01' && $('#memberTypeCd').val() == '03'){
			setForNormalMember();
			setMonthlyPointDefault();
		}else{
			setForOtherMembers();
			setMonthlyPointDefault();
		}		    
	});
	
	$('#memberGradeCode').change(function(e) {
		setMonthlyPointDefault();	    
	});
	
	function setForNormalMember(){
		$('.forNormalmember').show();
		$("#memberGradeCode").attr("disabled", false);
		$("#additionalLessonCd").attr("disabled", false);
		$("#monthlyPoint").attr("disabled", false);
		$("#carriablePoint").attr("disabled", false);
	}
	
	function setForOtherMembers(){
		$('.forNormalmember').hide();
		$("#memberGradeCode").attr("disabled", true);
		$("#additionalLessonCd").attr("disabled", true);	
	}
	
	function setMonthlyPointDefault(){
		if($('#memberGradeCode').val() == '01'){
			$('#monthlyPoint').val($('#monthlyPointForFullMember').val())
		}else{
			$('#monthlyPoint').val($('#monthlyPointForHalfMember').val())
		}
	}
	
	function setMonthlyPointNull(){
		$("#monthlyPoint").attr("disabled", true);
		$("#carriablePoint").attr("disabled", true);
	}
	
});