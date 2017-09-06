function checkNumeric(event) {
    var keyCode = event.keyCode;
    // 8 - backspace
    // 46 - delete
    // 37 - arrow-left
    // 39 - arrow-right
    if ((keyCode < 48 || keyCode > 57) && (keyCode != 8
        && keyCode != 46 && keyCode != 37 && keyCode != 39)) {
        event.preventDefault();
    }
}

function filterProducts() {
    $.ajax({
        url: '/filter',
        type: 'post',
        data: $('#filter-data').serialize(),
        async: true,
        success: function (response) {
            var a = response;
            $('#main-content').html(response);
        }
    });
}

function showSearchField() {
    $('.search').removeClass('hidden');
    $('.search input').focus();
}


$(document).ready(function() {
    $('#search').autocomplete({
        minLength: 1,
        source: function(req, add) {
            $.getJSON("/products/json", req, function(data) {
                var suggestions = [];
                if(!data.length){
                    suggestions = [
                        {
                            label: 'No matches found',
                            value: req.term
                        }
                    ];
                    add(suggestions);
                } else {
                    $.each(data, function (i, val) {
                        suggestions.push({
                            label: val.name,
                            productId: val.id
                        });
                    });
                    add(suggestions);
                }
            });
        },
        select: function (e, ui) {
            //location.reload();
            if (ui.item.productId !== undefined) {
                window.location.replace('http://localhost:8080/catalog/' + ui.item.productId);
            }
        }

    });

    $(".search input").blur(function() {
        $(".search").addClass('hidden');
    })
});