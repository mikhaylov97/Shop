function filterOrders() {
    $.ajax({
        url: '/admin/filter/done',
        type: 'post',
        data: $('#filter-data').serialize(),
        async: true,
        success: function (response) {
            $('.main-context').html(response);
        }
    });
}
