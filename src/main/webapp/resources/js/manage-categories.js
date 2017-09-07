$(document).ready(function () {

    $('#mens').click(function(e){ //on add input button click
        e.preventDefault();
        $('#mens').addClass('active');
        $('#all').removeClass('active');
        $('#womens').removeClass('active');
        $.ajax({
            url: '/admin/get/categories?active=mens',
            type: 'post',
            async: true,
            success: function (response) {
                $('.current-categories').html(response);
            }
        });
    });

    $('#womens').click(function(e){ //on add input button click
        e.preventDefault();
        $('#womens').addClass('active');
        $('#mens').removeClass('active');
        $('#all').removeClass('active');
        $.ajax({
            url: '/admin/get/categories?active=womens',
            type: 'post',
            async: true,
            success: function (response) {
                $('.current-categories').html(response);
            }
        });
    });

    $('#add-button').click(function (e) {
        $.ajax({
            url: '/admin/categories/add',
            type: 'post',
            data: $('#new-category-form').serialize(),
            async: true,
            success: function (response) {
                if(response === 'saved') {
                    $('#category-name').val('');
                    $('#category-select').val('1');
                    $('.success-message').removeClass('hidden');
                    $('.error-message').addClass('hidden');
                    $('.active').click();
                } else {
                    $('.success-message').addClass('hidden');
                    $('.error-message').removeClass('hidden');
                }
            }
        });
    })
});

function showHideCategory(e, href) {
    e.preventDefault();
    var href2 = href;
    $.ajax({
        url: href,
        type: 'post',
        async: true,
        success: function (response) {
            $('.active').click();
        }
    });
}
