$(document).ready(function () {

    $('#password-form').submit(function (event) {
        event.preventDefault();
        $.ajax({
            url: '/account/settings/password',
            type: 'post',
            async: true,
            data: $('#password-form').serialize(),
            success: function (response) {
                if (response === 'saved') {
                    $('.new-password input').css('border-color', 'black').val('');
                    $('.old-password input').css('border-color', 'green').val('');
                    $('.password-error-message').addClass('hidden');
                    $('.password-success-message').removeClass('hidden');
                } else {
                    $('.error-message').html(response);
                    $('.old-password input').val('');
                    $('.new-password input').val('');
                    $('.password-success-message').addClass('hidden');
                    $('.password-error-message').removeClass('hidden');
                }
            }
        });
    });

    $('.old-password input').keyup(function () {
        var password = $('.old-password input').val();
        if (password.match(/^[A-Za-z1-9]{6,10}$/)) {
            $('.old-password input').css('border-color', 'green');
        } else {
            $('.old-password input').css('border-color', 'red');
        }
    });

    $('.new-password input').keyup(function () {
        var password = $('.new-password input').val();
        if (password.match(/^[A-Za-z1-9]{6,10}$/)) {
            $('.new-password input').css('border-color', 'green');
        } else {
            $('.new-password input').css('border-color', 'red');
        }
    });

    $('#account-info-form').submit(function (event) {
       event.preventDefault();
        $.ajax({
            url: '/account/settings',
            type: 'post',
            async: true,
            data: $('#account-info-form').serialize(),
            success: function (response) {
                if (response === 'saved') {
                    $('#account-info-form input').css('border-color', 'black');
                    $('.account-info-error-message').addClass('hidden');
                    $('.account-info-success-message').removeClass('hidden');
                } else {
                    $('.account-info-error-message').html(response);
                    $('.account-info-success-message').addClass('hidden');
                    $('.account-info-error-message').removeClass('hidden');
                }
            }
        });
    });

    $('.phone input').keyup(function () {
        var phone = $('.phone input').val();
        if (phone.match(/^\d(-)?[0-9]{3}-?[0-9]{3}-?[0-9]{2}-?[0-9]{2}$/)) {
            $('.phone input').css('border-color', 'green');
        } else {
            $('.phone input').css('border-color', 'red');
        }
    });

    $('.surname input').keyup(function () {
        var surname = $('.surname input').val();
        if (surname.match(/^[A-Za-z]{1,15}$/)) {
            $('.surname input').css('border-color', 'green');
        } else {
            $('.surname input').css('border-color', 'red');
        }
    });

    $('.name input').keyup(function () {
        var name = $('.name input').val();
        if (name.match(/^[A-Za-z]{1,15}$/)) {
            $('.name input').css('border-color', 'green');
        } else {
            $('.name input').css('border-color', 'red');
        }
    });

    $('.postcode input').keyup(function () {
        var postcode = $('.postcode input').val();
        if (postcode.match(/^[1-9]{6,8}$/)) {
            $('.postcode input').css('border-color', 'green');
        } else {
            $('.postcode input').css('border-color', 'red');
        }
    });

    $('.country input').keyup(function () {
        var country = $('.country input').val();
        if (country.match(/^[A-Za-z]{1,10}(\s|\.|-)?[A-Za-z]{0,10}(\s|-)?[A-Za-z]{0,10}$/)) {
            $('.country input').css('border-color', 'green');
        } else {
            $('.country input').css('border-color', 'red');
        }
    });

    $('.city input').keyup(function () {
        var city = $('.city input').val();
        if (city.match(/^[A-Za-z]{1,10}(\s|\.|-)?[A-Za-z]{0,10}(\s|-)?[A-Za-z]{0,10}$/)) {
            $('.city input').css('border-color', 'green');
        } else {
            $('.city input').css('border-color', 'red');
        }
    });

    $('.street input').keyup(function () {
        var street = $('.street input').val();
        if (street.match(/^[A-Za-z]{1,10}(\s|\.|-)?[A-Za-z]{0,10}(\s|-)?[A-Za-z]{0,10}$/)) {
            $('.street input').css('border-color', 'green');
        } else {
            $('.street input').css('border-color', 'red');
        }
    });

    $('.house input').keyup(function () {
        var house = $('.house input').val();
        if (house.match(/^[1-9]{1,6}[A-Za-z]?$/)) {
            $('.house input').css('border-color', 'green');
        } else {
            $('.house input').css('border-color', 'red');
        }
    });

    $('.apartment input').keyup(function () {
        var apartment = $('.apartment input').val();
        if (apartment.match(/^[1-9]{1,6}[A-Za-z]?$/)) {
            $('.apartment input').css('border-color', 'green');
        } else {
            $('.apartment input').css('border-color', 'red');
        }
    });

    var date = $('.birthday input').attr('value').split('-');
    var resultDate = date[2] + '-' + date[1] + '-' + date[0];
    $('.birthday input').val(resultDate);

});