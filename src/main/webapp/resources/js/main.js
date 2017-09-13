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
            $('#main-content').html(response);
        }
    });
}

function closeFilter() {
    $('#filter-nav').css('left', -300);
    $('.wrap').css('display', 'none');
}

function openFilter() {
    $('#filter-nav').css('left', 0);
    $('.wrap').css('display', 'block');
}

$(document).ready(function() {
    $("#filter-data .filter-input input[type=text]").blur(function() {
        var val = $(this).val();
        if (val !== '' && val.indexOf('$') === -1) {
            $(this).val('$' + val);
        }
    });

    $('#search').autocomplete({
        minLength: 1,
        open: function(event, ui) {
            var autocomplete = $(".ui-autocomplete");
            var oldTop = autocomplete.offset().top;
            var newTop = oldTop - 24;

            autocomplete.css("top", newTop);
        },
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

    $(document).scroll(function(){
        $("#search").autocomplete("close");
    });

    $("#search").blur(function() {
        $("#search").val('');
    })
});