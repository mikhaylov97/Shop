function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#upload-image')
                .attr('src', e.target.result)
                .width(e.width)
                .height(e.height);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

$(document).ready(function() {
    var max_fields      = 10; //maximum input boxes allowed
    var wrapper         = $(".item-size"); //Fields wrapper
    var add_button      = $("#add-size-button"); //Add button ID

    var x = 0; //initlal text box count
    x = wrapper.children.length;
    $(add_button).click(function(e){ //on add input button click
        e.preventDefault();
        if(x < max_fields){ //max input box allowed
           // x++; //text box increment
            $(wrapper).append('<div class="col-lg-12 size-line"><input type="text" name="sizes[' + x + '].size" placeholder="Size" required/><input type="text" name="sizes[' + x + '].availableNumber" placeholder="Amount" required/><a href="#" class="remove-field"><i class="fa fa-times"></i></a></div>'); //add input box
            x++;
        }
    });

    $(wrapper).on("click",".remove-field", function(e){ //user click on remove text
        e.preventDefault(); $(this).parent('div').remove(); x--;
    })
});