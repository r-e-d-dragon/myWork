$(function(){
	$("#btnRegisterBooking*").on("click", function(e) {
		var reservationDate = $('#reservationDate').val();
		var reservationTime = $(this).closest('tr').data('timeslotname');
		var consumedPoint = $(this).closest('tr').data('consumedpoint');
		var batNumberCd = $(this).closest('tr').data('batnumbercd');
		var batNumber = $(this).closest('tr').data('batnumber');

		$('#registerForm #reservationDate').val(reservationDate);
		$('#registerForm #reservationTime').val(reservationTime);
		$('#registerForm #batNumberCd').val(batNumberCd);
		$('#registerForm #batNumber').val(batNumber);
		$('#registerForm #consumedPoint').val(consumedPoint);

		$('#registerForm').submit();
		return;
	});
});