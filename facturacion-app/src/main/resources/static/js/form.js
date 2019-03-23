$('input[type="file"]').change(function (e) {
    var fileName = e.target.files[0].name;
    $('.custom-file-label').html(fileName);
});

/* datepicker */
$('.jnd-datepicker').datepicker({
    format: "yyyy-mm-dd",
    todayBtn: "linked",
    language: "es",
    autoclose: true,
    todayHighlight: true,
});