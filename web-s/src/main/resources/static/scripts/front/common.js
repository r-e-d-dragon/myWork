
$(function(){

    var url = window.location.pathname;
    $('#gnavi a[href="'+url+'"]').addClass('current');



	// アコーディオン
	$('.accordion_head').click(function() {
		$(this).toggleClass('active');
		$(this).next().slideToggle();
	}).next().hide();

  $('.sp_menubtn').click(function() {
    $(this).toggleClass('active');
    $('#gnavi').fadeToggle();
  });
  
  $('#gnavi .parent').click(function(){
    $(this).next().slideToggle();
    $(this).toggleClass('open');
    $('body').toggleClass('submenuopen');
  });


        //スムーズスクロール
  
        $('a[href^="#"]').on('click',function(){
		var speed = 500;
		var href= $(this).attr("href");
		var target = $(href == "#" || href == "" ? 'html' : href);
		var position = target.offset().top - $('header').innerHeight() - 30;
		$("html, body").animate({scrollTop:position}, speed, "swing");
		return false;
        });
  


	// スマホ以外でhref属性がtelから始まる要素の中身を抜いてspanに差し替える
	var ua = navigator.userAgent;
    if(ua.indexOf('iPhone') < 0 && ua.indexOf('Android') < 0){
		$("a[href^=tel]").each(function(){
			$(this).replaceWith("<span>" + $(this).html() + "</span>");
		});
    }


	//スマホ固定ボタン
	var showTop = 100;
    $(window).on('load scroll resize',function(){
        if($(window).scrollTop() >= showTop){
            $('.float_top').addClass('active');
            $('.float_bottom').addClass('active');
        } else if($(window).scrollTop() < showTop){
            $('.float_top').removeClass('active');
            $('.float_bottom').removeClass('active');
        }
    });

	
	//タブ
  $('.tab li a').click(function() {
		$('.tab li a').removeClass('active');
		$(this).addClass('active');
		var target = $(this).attr('href');
	  $('.tabcontent').hide();
		$(target).show();
	  return false;
  });
	
  
  //モーダル
    $('.js-modal-open').on('click',function(){
        $('.js-modal').fadeIn();
        return false;
    });
    $('.js-modal-close').on('click',function(){
        $('.js-modal').fadeOut();
        return false;
    });  
  
	
});






