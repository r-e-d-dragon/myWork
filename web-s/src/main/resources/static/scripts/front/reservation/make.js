$(function(){
	var reservationTotalMaxCount = parseInt($('#reservationTotalMaxCount').val());
	var today = new Date();	
	var firstDayOfNextMonth = new Date(today.getFullYear(), today.getMonth(), 1);
	var lastDayOfNextMonth = new Date(today.getFullYear(), today.getMonth()+2, 0);

	$("#reservationDate").prop('min', setDate(firstDayOfNextMonth));
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
	}
	
	var hasChanged = $("#hasChanged").val();
	if(hasChanged == "true"){
		hasChanged = $("#hasChanged").val("false");
		var alertMessage = '店舗名：' + $('.aspName').val() +
							'\n予約日：' + $('#registerForm #reservationDate').val() +
							'\n予約時間：' + $('#registerForm #reservationTime').val() +
							'\n打席番号：' + $('#registerForm #batNumber').val() +
							'\n使用予定ポイント：' + $('#registerForm #consumedPoint').val() +
							'\n月ポイント：' + $('#registerForm #monthlyPoint').val()+
							'\nイベントポイント：' + $('#registerForm #eventPoint').val();
		swal({
               title : "予約登録完了しました。",
               text : alertMessage,
               buttons : "OK",
                      }).then(function(enforce) {
                        if (enforce) { 
                        } 
                      });
	}
	
	
	$("#btnRegisterBooking*").on("click", function(e) {
		var dd = parseInt($('#form #reservationCnt').val());
		if(parseInt($('#form #reservationCnt').val()) > reservationTotalMaxCount){
			$('#global-error').append('<ul class="ul-error"><li class="li-error"><span>既に予約限度まで予約がありました。追加予約は出来ません。</span><br></li></ul>');
			return;
		}
		var reservationDate = $('#reservationDate').val().replace(/-/g, '/');
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

		swal("使用するポイントの種類を選択してください。", {
		  buttons: {
		    mothly: "月固定",
		    event: "イベント",
			cancel: "キャンセル",
		  },
		})
		.then((value) => {
		  switch (value) {
		 
		    case "mothly":
		      $('#pointCategoryCode').val("01");
			  $('#registerForm').submit();
		      break;
		 
		    case "event":
		      $('#pointCategoryCode').val("02");
			 $('#registerForm').submit();
		      break;
		  }
		});


		//
		return;
	});
	
	$(".js-cancel*").on("click", function(e) {
		var cancelMsg = "予約日時： " + $(this).text();
		var selectedReservationId = $(this).attr('value');
		var linkUrl = $(this).attr('url');
		swal({
               title : "予約取消をします。",
               text : cancelMsg,
               buttons: {
					    ok: "OK",
						cancel: "キャンセル",
					  },
                      }).then(function(enforce) {
                        if (enforce) { 
							$('#registerForm #reservationId').val(selectedReservationId);									
							$('#registerForm').attr("action",linkUrl);
							$('#registerForm').submit();
                        } 
                      });
		
		
	});
	
});