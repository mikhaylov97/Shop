$(document).ready(function() {
    var currentAmount;
    $.ajax({
        url: '/get/amount',
        type: 'post',
        data: $('#sizeId').serialize(),
        async: true,
        success: function (response) {
            currentAmount = parseInt(response);
        }
    });

    $('#button-less').on('click', function () {
        if (parseInt($('#amount-number').val()) !== 1) {
            $('#amount-number').val(parseInt($('#amount-number').val()) - 1);
        }
    });

    $('#button-more').on('click', function () {
        if (parseInt($('#amount-number').val()) < currentAmount) {
            $('#amount-number').val(parseInt($('#amount-number').val()) + 1);
        }
    });

    $('#sizeId').on('change', function () {
        // var tmp = currentAmount;
        $.ajax({
            url: '/get/amount',
            type: 'post',
            data: $('#sizeId').serialize(),
            async: true,
            success: function (response) {
                currentAmount = parseInt(response);
                var amount = response;
                if (parseInt($('#amount-number').val()) > amount) {
                    $('#amount-number').val(amount);
                }
                $.ajax({

                });
            }
        });
    })
});