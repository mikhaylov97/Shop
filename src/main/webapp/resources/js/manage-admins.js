function deleteAdmin(id) {
    $.ajax({
        url: '/admin/super/management/delete/' + id,
        type: 'post',
        async: true,
        success: function (response) {
            $('.admins').html("<div class='address-title'>List of Admins</div>" + response);
        }
    });
}