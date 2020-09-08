$(function(){
      $('#top_slide .slider').slick({
        autoplay: true,
        autoplaySpeed: 5000,
        arrows: true,
        pauseOnFocus: false,
        pauseOnHover: false,
        pauseOnDotsHover: false,
        waitForAnimate: false
      });
      $('#newsticker .ticker').slick({
        autoplay: true,
        autoplaySpeed: 5000,
        arrows: true,
      });
       $('.js-pause').on('click', function() {
        $('#newsticker .ticker').slick('slickPause');
         $(this).hide();
         $('.js-play').show();
      });

      $('.js-play').on('click', function() {
        $('#newsticker .ticker').slick('slickPlay');
         $(this).hide();
         $('.js-pause').show();
      });
      
      $('#newsticker .slick-prev, #newsticker .slick-next').click(function(){
         $('.js-play').show();
         $('.js-pause').hide();
      });
      
      $(".js-modal-btn").modalVideo();
      
    });