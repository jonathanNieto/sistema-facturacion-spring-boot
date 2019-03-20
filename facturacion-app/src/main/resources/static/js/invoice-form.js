var btnBack = document.querySelector("#btn-back");

function goBack() {
    window.history.back();
}

btnBack.addEventListener('click', goBack);

/* jquery ui */
/* $(document).ready(function () { */
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
        focus: function (event, ui) {
            event.preventDefault();
            $("#find-product").val(ui.item.label);
        },
        select: function (event, ui) { 

            if (itemsHelper.hasProduct(ui.item.value)) {
                itemsHelper.updateAmount(ui.item.value, ui.item.cost);
                $("#find-product").val('');
                return false;
            }
            var line = $("#template-items-invoice").html();

            line = line.replace(/{id}/g, ui.item.value);
            line = line.replace(/{name}/g, ui.item.label);
            line = line.replace(/{cost}/g, ui.item.cost);

            $("#load-item-products tbody").append(line);
            $("#find-product").val('');
            itemsHelper.calculateAmount(ui.item.value, ui.item.cost, 1);

            return false;
        }
    });

    var itemsHelper = {
        calculateAmount: function (id, cost, quantity) {
            $("#total_amount_" + id).html(parseFloat(cost) * parseFloat(quantity));
            this.calculateTotalDue();

        },
        hasProduct: function(id){
            var result = false;
            $("input[name = 'item_id[]']").each(function () {
                if (parseInt(id) == parseInt($(this).val())) {
                    result = true;
                }
            });
            return result;
        },
        updateAmount: function (id, cost) {  
            var quantity = $("#quantity_" + id).val() ? parseInt($("#quantity_" + id).val()) : 0;
            $("#quantity_" + id).val(++quantity);
            this.calculateAmount(id, cost, quantity);
        }, 
        deleteProduct: function (id) {
            $("#row_" + id).remove();
            this.calculateTotalDue();
          },
        calculateTotalDue: function () {
            var total = 0;

            $("span[id^='total_amount_']").each(function () {
                total += parseInt($(this).html());
            });

            $("#total-due").html("$" + total);
        }
    }

    $(document).on("change", ".inputNumber", function () {
        let element = $(this)[0].parentElement.parentElement;
        let id = $(element).attr('productid');
        let cost = $(element).attr('cost');
        let value = document.getElementById("quantity_" + id).value;
        itemsHelper.calculateAmount(id, cost, value);
    });

    $(document).on("click", ".btn-products", function () {
        let element = $(this)[0].parentElement.parentElement;
        let id = $(element).attr('productid');
        itemsHelper.deleteProduct(id);
    });

/* }); */