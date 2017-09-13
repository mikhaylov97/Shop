function filterOrders() {
    $.ajax({
        url: '/admin/filter/active',
        type: 'post',
        data: $('#filter-data').serialize(),
        async: true,
        success: function (response) {
            $('.main-context').html(response);
        }
    });
}
