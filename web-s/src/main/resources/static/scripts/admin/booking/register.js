var fd;
var reservationCnt = 0;
var reservationPoint = 0;

$(function(){

    $(".registerBtn").hide();
    $(".reservation_dialog").hide();

	$("#btnSearchUser*").on("click", function(e) {
		e.preventDefault();
        var memberCode = $("#memberCode").val().trim();
        var reservationDate = $("#reservationDate").val().trim();

        $('tbody').find("tr:gt(0)").remove();
        $("#pointCategoryCode option:not(:selected)").prop('disabled', false);

        $("#valid").val(false);
        $(".registerBtn").hide();
        $(".reservation_dialog").hide();
        $("#selectedMemberName").text("");
        $("#reservation_message").text("");
        
        $("#memberName").val("");
        $("#validMonthlyPoint").val(0);
        $("#validEventPoint").val(0);

        $("#currentMonthlyPointText").text("");
        $("#nextMonthlyPointText").text("");
        $("#validEventPointText").text("");

        $("#limitReservationCount").val(0);
        $("#limitMonthlyReservationCount").val(0);
        $("#limitEventReservationCount").val(0);
        $("#limitReservationPoint").val(0);

		$("#reservationCnt").val(reservationCnt);
		$("#reservationPoint").val(reservationPoint);
                
		if( memberCode == '') {
			alert("会員コードに値を入力してください。");
			return;
        }

		var fd= new FormData();
		fd.append("memberCode",memberCode);
        fd.append("reservationDate",reservationDate);


        var consumedPoint = $("#consumedPoint").val();
        var pointCategoryCode = $("#pointCategoryCode").val();

        var mPointFlag = false;
        var ePointFlag = false;
        var countOverFlag = false;
        var mCountOverFlag = false;
        var eCountOverFlag = false;
        
		var linkUrl = $(this).attr('url');
		$.ajax({
            type : "POST",
            url : linkUrl,
            data : fd,
            contentType : false,
			processData : false,
			cache : false,
            success : function(data, status, xhr) {
	
				$('#memberCode').removeClass('fieldError');
                $("#memberName").removeClass('fieldError');
                $('#validMonthlyPoint').removeClass('fieldError');
                $('#validEventPoint').removeClass('fieldError');
                 
				if(data == null) {
					return null;
				}
                if (data['validated']) {
                    
                    if (data.reservation.tblUser != null) {
                        $("#memberName").val(data.reservation.tblUser['lastName'] + ' ' + data.reservation.tblUser['firstName']);
                        $("#validMonthlyPoint").val(data.reservation['validMonthlyPoint']);
                        $("#validEventPoint").val(data.reservation['validEventPoint']);

                        $("#currentMonthlyPointText").text(data.reservation['currentMonth']+' '+data.reservation['currentMonthlyPoint']+'P');
                        $("#nextMonthlyPointText").text(data.reservation['nextMonth']+' '+data.reservation['nextMonthlyPoint']+' P');
                        $("#validEventPointText").text(data.reservation['validEventPoint']+ 'P');
                        
                        $("#limitReservationCount").val(data.reservation['limitReservationCount']);
                        $("#limitEventReservationCount").val(data.reservation['limitEventReservationCount']);
                        $("#limitMonthlyReservationCount").val(data.reservation['limitMonthlyReservationCount']);
                        $("#limitReservationPoint").val(data.reservation['limitReservationPoint']);
                        
                        $('tbody').find("tr:gt(0)").remove();
                        if(data['hasReservation']){
                            $('#selectedMemberName').text($("#memberName").val());
                            for(let i = 0; i <data.reservation.reservationList.length; i++) {
								reservationCnt = reservationCnt + 1;
								reservationPoint = reservationPoint + data.reservation.reservationList[i]['consumedPoint'];
								var pointCategory;
								if(data.reservation.reservationList[i]['pointCategoryCode'] == '01'){
									pointCategory = "月固定ポイント"
								}else{
									pointCategory = "イベントポイント"
								}
                                $('tbody').append('<tr>'
                                          + '<td class="text-center">' + data.reservation.reservationList[i]['aspName'] + '</td>'
                                          + '<td class="text-center">' + data.reservation.reservationList[i]['reservationNumber'] + '</td>'
                                          + '<td class="text-center">' + data.reservation.reservationList[i]['batNumber'] + '</th>'
                                          + '<td class="text-center">' + data.reservation.reservationList[i]['reservationDate'] + '</td>'
                                          + '<td class="text-center">' + data.reservation.reservationList[i]['reservationTime'] + '</td>'
										  + '<td class="text-center">' + data.reservation.reservationList[i]['consumedPoint'] + '</td>'
								          + '<td class="text-center">' + pointCategory + '</td>'
                                          + '</tr>');
                            }

						$("#reservationCnt").val(reservationCnt);
						$("#reservationPoint").val(reservationPoint);

                            if (data.reservation.reservationList.length >= data.reservation['limitReservationCount']) {
                                $("#reservation_message").text("既に予約限度まで予約がありました。追加予約は出来ません。");
                                countOverFlag = true;
                                
                            } else if (data.reservation['monthlyReservationCount'] >= data.reservation['limitMonthlyReservationCount']) {
                                $("#reservation_message").text("既に月ポイントで可能な予約限度まで予約がありました。　月ポイントでは追加予約は出来ません。");
                                $("#pointCategoryCode").val("02");
                                $("#pointCategoryCode option:not(:selected)").prop('disabled', true);
                                mCountOverFlag = true;

                            } else if (data.reservation['eventReservationCount'] >= data.reservation['limitEventReservationCount']) {
                                $("#reservation_message").text("既にイベントポイントで可能な予約限度まで予約がありました。　イベントポイントでは追加予約は出来ません。");
                                $("#pointCategoryCode").val("01");
                                $("#pointCategoryCode option:not(:selected)").prop('disabled', true);
                                eCountOverFlag = true;
                                
                            }
                            $(".reservation_dialog").show();
                        }
                        
                        if (consumedPoint<=data.reservation['validMonthlyPoint']) {
                            mPointFlag = true;
                        } else {
                            $("#pointCategoryCode").val("02");
                            $("#pointCategoryCode option:not(:selected)").prop('disabled', true);
                        }
                        if (consumedPoint<=data.reservation['validEventPoint']) {
                            ePointFlag = true;
                        } else {
                            $("#pointCategoryCode").val("01");
                            $("#pointCategoryCode option:not(:selected)").prop('disabled', true);
                        }

                        if (countOverFlag === false) {
                            if (mPointFlag === true && mCountOverFlag === false) {
                                $("#valid").val(true);
                                $(".registerBtn").show();
                            } else if (ePointFlag === true && eCountOverFlag === false) {
                                $("#valid").val(true);
                                $(".registerBtn").show();
                            }
                        }
                    } else {
                        alert("該当する会員が存在しません。　会員コードを確認してから再検索を行ってください。");
                    }
                } else {
                    $("#memberName").val("");
                    $("#validMonthlyPoint").val(0);
                    $("#validEventPoint").val(0);

                    $("#currentMonthlyPointText").text('0 P');
                    $("#nextMonthlyPointText").text('0 P');
                    $("#validEventPointText").text('0 P');
                    
                    $("#memberName").addClass('fieldError');
                    $('#validMonthlyPoint').addClass('fieldError');
                    $('#validEventPoint').addClass('fieldError');
                }
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                swal('error:' + XMLHttpRequest + '/' + textStatus + '/' + errorThrown);
            }
        });

		return;
	});
	
	if($("#trigger").val() == '1'){
		$("#btnSearchUser").click();
	}
});