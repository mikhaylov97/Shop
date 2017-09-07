function deleteAdmin(id) {
    $.ajax({
        url: '/super/admin/management/delete/' + id,
        type: 'post',
        async: true,
        success: function (response) {
            $('.admins').html("<div class='address-title'>List of Admins</div>" + response);
        }
    });
}

function addNewAdmin() {
    $.ajax({
        url: '/super/admin/management/add/ajax',
        type: 'post',
        async: true,
        data: $('.account-info').serialize(),
        success: function (response) {
            if (response === 'saved') {
                $('.name input').val('').css('background-color', 'white');
                $('.surname input').val('').css('background-color', 'white');
                $('.email input').val('').css('background-color', 'white');
                $('.password input').val('');
                $('.success-message').removeClass('hidden').html('Admin was successfully added');
               // $('.success-message').html('Admin was successfully added');
                $('.error-message').addClass('hidden');
                $.ajax({
                    url: '/super/admin/management/get/admins',
                    type: 'post',
                    async: true,
                    success: function (response) {
                        $('.admins').html("<div class='address-title'>List of Admins</div>" + response);
                    }
                });
            } else {
                $('.password input').val('');
                $('.success-message').addClass('hidden');
                $('.error-message').html('User with such email already exists').removeClass('hidden');
               // $('.error-message').removeClass('hidden');
            }
        }
    })
}