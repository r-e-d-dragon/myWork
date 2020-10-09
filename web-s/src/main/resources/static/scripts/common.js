
function isDoubleClicked(element) {
    if (element.data("isclicked")) return true;
    element.data("isclicked", true);
    setTimeout(function () {
        element.removeData("isclicked");
    }, 3000);
    return false;
}

function NumCheck(chkVal) {
   if (chkVal.match(/[^z0-9]+/)) {
   return false;	
   }
   return true;
}

$(function(){
	
	$("input").on("keydown", function (e) {
		if($(this).hasClass( "trim" )){
			return e.which !== 32;
		}
		return;    	
	});
	
	$(".table-search input").on("keydown", function (e) {
		if($(this).hasClass( "not-trim" )){
			return;
		}
		return e.which !== 32;   	
	});
	
    $("a.newWindow").click(function(e) {
        e.preventDefault();
        var href = $(this).attr('href');
        var newWindow = window.open(href, '_blank', 'resizable, scrollbars, width=1250,  height=677, left=0, top=0');
        newWindow.focus();
    });

    $(".disabled").attr("tabIndex", -1);
    $(".menu-disabled").removeAttr('href');
    $(".menu-disabled").css('opacity', '1');

    $(".number").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
             // Allow: Ctrl/cmd+A
            (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: Ctrl/cmd+C
            (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: Ctrl/cmd+X
            (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });

    $('a[href="#"]').click(function(e) {
        e.preventDefault();
		
		if ($(this).hasClass("user-func")){
			return;
		}
		
        if (!$(this).hasClass("no-timer")){
            if (isDoubleClicked($(this))){
                e.stopPropagation();
                e.stopImmediatePropagation();
                return;
            }
        }

		var linkUrl = $(this).attr('url');

        if(linkUrl != null ) {
            $("form:first").attr("action",linkUrl);
        }
		var targets = $(this).attr('target');
		var values = $(this).attr('value');
		
		if(values != null && targets != null) {
			var targetArray = targets.split(",");
			var valueArray =  values.split(",");
			if(targetArray.length === valueArray.length) {
				for(var idx =0; idx < targetArray.length; idx++) {
					$("#"+targetArray[idx]).val(valueArray[idx]);
				}
			}
		}
		
        $("form:first").submit();
    });

    $('a[href!="#"]').click(function(e) {
		
		if ($(this).hasClass("user-func")){
            return;
		}
		
		var aLinkUrl = $(this).attr('href').trim();
		if(aLinkUrl != null && aLinkUrl != '') {
			location.href = aLinkUrl;
			return;
		}
		
        if (isDoubleClicked($(this))){
            e.preventDefault();
            e.stopPropagation();
            e.stopImmediatePropagation();
            return;
        }
		
		var linkUrl = $(this).attr('url');
		if(linkUrl != null ) {
            $("form:first").attr("action",linkUrl);
        }

		var targets = $(this).attr('target');
		var values = $(this).attr('value');
		
		if(values != null && targets != null) {
			var targetArray = targets.split(",");
			var valueArray =  values.split(",");
			if(targetArray.length === valueArray.length) {
				for(var idx =0; idx < targetArray.length; idx++) {
					$("#"+targetArray[idx]).val(valueArray[idx]);
				}
			}
		}
		
        $("form:first").submit();

		return;
        
    });

	$(".date-clear-icon").click(function(e) {
		var target = $(this).attr('target');
		$("#" + target).val("");
	});



  $(window).keydown(function(event){
      // クリックされたキーのコード
      var keyCode = event.keyCode;
      var ctrlClick = event.ctrlKey;
      var altClick = event.altKey;
      var obj = event.target;

      // ファンクションキーを制御する
      // 制限を掛けたくない場合は対象から外す
      if( keyCode == 112 // F1キーの制御
              || keyCode == 113 // F2キーの制御
              || keyCode == 114 // F3キーの制御
              || keyCode == 115 // F4キーの制御
              || keyCode == 117 // F6キーの制御
              || keyCode == 118 // F7キーの制御
              || keyCode == 119 // F8キーの制御
              || keyCode == 120 // F9キーの制御
              || keyCode == 121 // F10キーの制御
              || keyCode == 122 // F11キーの制御
           //   || keyCode == 123 // F12キーの制御
              ){
        return false;
      }

      // F5キーの制御
      if(keyCode == 116){
          if($('#f5_key_available').val()){
              return true;
          } else {
              return false;
          }
      }

      // バックスペースキーを制御する
    if(keyCode == 8){

        // テキストボックス／テキストエリアを制御する
        if((obj.tagName == "INPUT" && (obj.type == "text" || obj.type == "password"))
                ||(obj.tagName == "INPUT" && (obj.type == "TEXT" || obj.type == "PASSWORD"))
              || obj.tagName == "TEXTAREA"){
          // 入力できる場合は制御しない
          if(!obj.readOnly && !obj.disabled){
            return true;
          }
        }
        return false;
      }

      // Alt + ←→を制御する
      if(altClick && (keyCode == 37 || keyCode == 39)){
        return false;
      }

      // Ctrl + Nを制御する
      if(ctrlClick && keyCode == 78){
        return false;
      }

      // Ctrl + Rを制御する
      if(ctrlClick && keyCode == 82){
        return false;
      }
    });
    
});