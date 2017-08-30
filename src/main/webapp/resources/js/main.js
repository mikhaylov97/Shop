$(document).ready(function () {
    window.onscroll = function() {
        var scrolled = window.pageYOffset || document.documentElement.scrollTop; // Получаем положение скролла
        if(scrolled < 80){
            $('.navbar').css('transition', 'all ease-in-out 0.4s');
            $('.navbar').css('background', 'transparent');
        }else{
            $('.navbar').css('transition', 'all ease-in-out 0.3s');
            $('.navbar').css('background', 'black');
        }
    };
});