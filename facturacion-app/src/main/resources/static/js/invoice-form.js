var btnBack = document.querySelector("#btn-back");

function goBack() {
    window.history.back();
}

btnBack.addEventListener('click', goBack);

/* jquery ui */
$(document).ready(function () {
    $("#find-product").autocomplete({
        source: function (request, reponse) {
            $.ajax({
                url: "/invoice/load-products/" + request.term,
                dataType: "jason",
                data: {
                    term: request.term
                },
                success: function (data) {
                    Response($.map(data, function (item) {  
                        return {
                            value: item.id,
                            label: item.name,
                            cost: item.cost
                        }
                    }));
                }
            });
        },
        select: function (event, ui) { 
            $("#find-product").val(ui.item.label);
            return false;
         }
    });
});