$(function(){
	$("#btnRegisterBooking*").on("click", function(e) {
		var reservationDate = $('#datepicker').val();
		var reservationTime = $(this).closest('tr').data('timeslotname');
		var consumedPoint = $(this).closest('tr').data('consumedpoint');
        var gradeTypeCd = $(this).closest('tr').data('gradetypecd');
		var batNumberCd = $(this).closest('td').data('batnumbercd');
		var batNumber = $(this).closest('td').data('batnumber');

		$('#registerForm #reservationDate').val(reservationDate);
		$('#registerForm #reservationTime').val(reservationTime);
		$('#registerForm #batNumberCd').val(batNumberCd);
		$('#registerForm #batNumber').val(batNumber);
        $('#registerForm #consumedPoint').val(consumedPoint);
        $('#registerForm #gradeTypeCd').val(gradeTypeCd);

		$('#registerForm').submit();
		return;
	});
});