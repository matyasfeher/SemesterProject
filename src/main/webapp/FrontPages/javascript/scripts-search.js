$(function () {

    $('#datetimepicker1').datetimepicker({format: 'YYYY-MM-DD'});
    $('#datetimepicker2').datetimepicker({format: 'YYYY-MM-DD'});

    $('#radioBtn a').on('click', function () {
        var sel = $(this).data('title');
        var tog = $(this).data('toggle');
        $('#' + tog).prop('value', sel);

        if ($(this).hasClass('notActive'))
            $('#returnDate').toggle();

        $('a[data-toggle="' + tog + '"]').not('[data-title="' + sel + '"]').removeClass('active').addClass('notActive');
        $('a[data-toggle="' + tog + '"][data-title="' + sel + '"]').removeClass('notActive').addClass('active');
    });


    $('#exchangeCities').click(function () {
        var temp = $('#cityFrom').val();
        $('#cityFrom').val($('#cityTo').val());
        $('#cityTo').val(temp);
    });

    var states = {"CPH":"Copenhagen", "BUD":"Budapest"};

    $("#originAirport, #destinationAirport").autocomplete({
        source: states
    });

});