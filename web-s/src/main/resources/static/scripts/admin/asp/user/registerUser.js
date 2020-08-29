var fd;

$(function(){
	
	$("#btnSearchAddress*").on("click", function(e) {
		e.preventDefault();
        var zip1 = $("#zip1").val().trim();
		var zip2 = $("#zip2").val().trim();
		
		if( zip1 == '' || zip2 == '') {
			alert("郵便番号に値を入力してください。");
			if(zip1 == '' || zip1.length != 3) {
				$("#zip1").select();
				$("#zip1").focus();
			} else {
				$("#zip2").select();
				$("#zip2").focus();
			}
			return;
		} 
		
		var fd= new FormData();
		fd.append("headZip",zip1);
		fd.append("tailZip",zip2);
		var linkUrl = $(this).attr('url');
		
		$.ajax({
            type : "POST",
            url : linkUrl,
            data : fd,
            contentType : false,
			processData : false,
			cache : false,
            success : function(data, status, xhr) {
	
				$('#zip1').removeClass('fieldError');
                $('#zip2').removeClass('fieldError');
				
				if(data == null) {
					return null;
				}
				
                if (data['validated']) {
					if(data['hasOnlyOne']){
						var prefectureCityStreet = data.zipList[0]['prefectureKanji'] + data.zipList[0]['cityKanji']+ data.zipList[0]['streetKanji'];
						$("#address1").val(prefectureCityStreet);
					} else {
						$('#address_list option').remove();
						for(let i = 0; i <data.zipList.length; i++) {
							var prefectureCityStreet = data.zipList[i]['prefectureKanji'] + data.zipList[i]['cityKanji'] + data.zipList[i]['streetKanji'];
							$("#address_list").append($("<option>").val(prefectureCityStreet).text(prefectureCityStreet));
						}
						
						$("#address_dialog").dialog({
                        	modal:true, //モーダル表示
                        	title:"住所を選択してください", //タイトル
                        	buttons: { //ボタン
                            	"選択": function() 
								{
                                	var selected = $('#address_list').val();
                                	$("#address1").val(selected);
                                	$(this).dialog("close");
                            	}
                        	}
                    	});	
					}
					

                } else {
                    $("#address1").val("");
					$('#zip1').addClass('fieldError');
                    $('#zip2').addClass('fieldError');
                }
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                swal('error:' + XMLHttpRequest + '/' + textStatus + '/' + errorThrown);
            }
        });

		return;
	});
	
});