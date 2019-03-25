$(document).ready(function () {

    $(".delBtn").on("click", function (event) {
        event.preventDefault();
        var href = $(this).attr("href");
        $("#exampleModal #delRef").attr("href", href);
        $("#exampleModal").modal();
    })
    
    /* datatables */
    $('.all-data-tables').DataTable({
        "columnDefs": [{
            /* "targets": [5, 6, 7], // column index (start from 0)
            "orderable": false, // set orderable false for selected columns */
        }],
        "pagingType": "full_numbers",
        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "todos los"]],
        "language": {
            "sProcessing": "Procesando...",
            "sLengthMenu": "Mostrar _MENU_ registros",
            "sZeroRecords": "No se encontraron resultados",
            "sEmptyTable": "Ningún dato disponible en esta tabla",
            "sInfo": "Registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty": "Registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix": "",
            "sSearch": "Buscar:",
            "sUrl": "",
            "sInfoThousands": ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst": "Primero",
                "sLast": "Último",
                "sNext": "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            }
        },
        "initComplete": function (settings, json ) {
            $('#loadingSpinner').fadeTo("slow",0)
                                .slideUp();
        }
    });


    /* logout */
    $("#btn-logout").click(function (e) {
        e.preventDefault();
        $("#logout-form").submit();
    });

    /* for hiding alerts */
    setTimeout(function () {
        $(".alert-close-auto").fadeTo("slow", 0)
               .slideUp("slow");
    }, 4000);
});