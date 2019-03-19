var btnBack = document.querySelector("#btn-back");

function goBack() {
    window.history.back();
}

btnBack.addEventListener('click', goBack);

/* jquery ui */
$(document).ready(function () {
    $("#find-product").autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "/invoice/load-products/" + request.term,
                dataType: "json",
                data: {
                    term: request.term
                },
                success: function (data) {
                    response($.map(data, function (item) {  
                        return {
                            value: item.id,
                            label: item.name,
                            cost: item.cost
                        };
                    }));
                }
            });
        },
        select: function (event, ui) { 
            //$("#find-product").val(ui.item.label);
            var line = $("#template-items-invoice").html();
            alert(line);
            console.log(line);
            line = line.replace(/{ID}/g, ui.item.value);
            console.log(line);
            line = line.replace(/{NOMBRE}/g, ui.item.label);
            console.log(line);
            line = line.replace(/{PRECIO}/g, "$" + ui.item.cost);
            console.log(line);

            $("#load-item-products tbody").append(line);
            $("#find-product").val('');
            return false;
        }
    });
});