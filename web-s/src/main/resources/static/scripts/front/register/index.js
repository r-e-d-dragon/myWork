$(function(){
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
});