$(function(){
	var aspCdMap = $("#aspMap").val();
	
	var hasChanged = $("#hasChanged").val();
	
	if(hasChanged == "true"){
		hasChanged = $("#hasChanged").val("false");
		swal({
               text : "登録完了しました。",
               buttons : "OK",
                      }).then(function(enforce) {
                        if (enforce) { 
							var url = $("form:first").attr('action');
						    url = url.replace("/register/finish","");
							location.href = url;
							return;
                        } 
                      });
	}	
	
	$("input").on("keydown", function (e) {
    	return e.which !== 32;
	});
	
	$(".search-asp-open").on("click", function(e) {
		$('.search-asp-modal').fadeIn();
		InitModal();
        return false;
	});
	
    $('.js-modal-close').on('click',function(){
        $('.search-asp-modal').fadeOut();
        return false;
    }); 

	$(".search-ajax-btn").on("click", function(e) {	
		e.preventDefault();
		
		$('.result-tr').remove();
		
        var aspNameVal = $(".input-asp-name").val();
		var aspAddressVal = $(".input-asp-address").val();

		
		var fd= new FormData();
		fd.append("aspName",aspNameVal);
		fd.append("aspAddress",aspAddressVal);
		var linkUrl = $(this).attr('url');
		
		$.ajax({
            type : "POST",
            url : linkUrl,
            data : fd,
            contentType : false,
			processData : false,
			cache : false,
            success : function(data, status, xhr) {
					
				if(data == null) {
					NotFound();
					return;
				}
				
                if (data['validated']) {
					Founded();
					$('tbody.asp-result-body').find("tr:gt(0)").remove();
                        if(data['hasData']){
	
							for(let i = 0; i <data.aspList.length; i++) {
								var fullAddress = data.aspList[i]['address1'] + data.aspList[i]['address1'];

                                $('tbody.asp-result-body').append('<tr class="result-tr">'
                                          + '<td>' + data.aspList[i]['aspName'] + '</td>'
                                          + '<td style="text-align:left;">' + fullAddress + '</td>'
								          + '<td>' + '<a class="asp-select-btn user-func" href="#" value=' + data.aspList[i]['aspCode'] +  '>選択</a>' + '</td>'
                                          + '</tr>');
							}
							$('tbody.asp-result-body').on("click", ".asp-select-btn", function(){
							    	SelectResultAsp($(this).attr('value'));	
								});
                         }
                } else {
                    NotFound();
                }
            },
            error : function(XMLHttpRequest, textStatus, errorThrown) {
                swal('error:' + XMLHttpRequest + '/' + textStatus + '/' + errorThrown);
            }
        });	
		return;
	});

	function InitModal(){
		$(".input-asp-name").val("");
		$(".input-asp-address").val("");
		$(".search-ajax-btn").click();
	}
	
	function Founded(){
		$(".frame").show();
		$(".founded").show();
		$(".not-found").hide();
	}
	
	function NotFound(){
		$(".frame").show();
		$(".not-found").show();
		$(".founded").hide();
	}
	
	function SelectResultAsp(aspCode){		
		$('.search-asp-modal').fadeOut();
		$("#aspCode").val(aspCode);
		var dd= GetNameOfAsp(aspCode);
		$("#aspNameForPreUser").val(GetNameOfAsp(aspCode));
		
	}
	
	function GetNameOfAsp(aspCode){
		return $("#aspCdSelect option[value='"+ aspCode +"']").text();
	}
});