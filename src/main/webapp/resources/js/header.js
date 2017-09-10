$(document).ready(function () {
    window.onscroll = function() {
        var scrolled = window.pageYOffset || document.documentElement.scrollTop; // Получаем положение скролла
        if(scrolled < 80){
            $('.navbar').css('transition', 'all ease-in-out 0.4s');
            $('.navbar').css('background', 'transparent');
            $('#scroll-to-top-button').css('display', 'none');
        }else{
            $('.navbar').css('transition', 'all ease-in-out 0.3s');
            $('.navbar').css('background', 'black');
            $('#scroll-to-top-button').css('display', 'block');
        }
    };

});

// When the user clicks on the button, scroll to the top of the document
function scrollToTop() {
    $('html,body').animate({scrollTop: 0}, 1000);
    // document.body.scrollTop = 0; // For Chrome, Safari and Opera
    // document.documentElement.scrollTop = 0; // For IE and Firefox
}