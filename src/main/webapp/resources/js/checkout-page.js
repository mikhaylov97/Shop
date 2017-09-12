$(document).ready(function() {
    var currentShipMethodPrice = parseInt($('#price1').html());

    $('.shipping-price').html(currentShipMethodPrice);
    $('.total-price').html(parseInt($('.bag-total-price').html()) + currentShipMethodPrice);

    $('.cash').click(function(e){ //on add input button click
        e.preventDefault();
        $('.cash').addClass('active');
        $('.card').removeClass('active');
        $('.payment-by-cash').removeClass('hidden');
        $('.payment-by-card').addClass('hidden');
        $('.cash-radio:checked').click();
    });

    $('.card').click(function(e){ //on add input button click
        e.preventDefault();
        $('.card').addClass('active');
        $('.cash').removeClass('active');
        $('.payment-by-card').removeClass('hidden');
        $('.payment-by-cash').addClass('hidden');
        $('.card-radio:checked').click();
    });

    $('.cash-radio').on('click', function () {
        var shipPrice = parseInt($('.cash-radio:checked').val());
        $('.shipping-price').html(shipPrice);
        $('.total-price').html(parseInt($('.bag-total-price').html()) + shipPrice);
    })

    $('.card-radio').on('click', function () {
        var shipPrice = parseInt($('.card-radio:checked').val());
        $('.shipping-price').html(shipPrice);
        $('.total-price').html(parseInt($('.bag-total-price').html()) + shipPrice);
    })
});