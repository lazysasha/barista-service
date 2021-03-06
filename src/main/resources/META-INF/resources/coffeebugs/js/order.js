function order() {
    if (!document.querySelector('#customer-name') || !document.querySelector('#beverage')) {
        return;
    }
    if (!document.querySelector('#customer-name').value) {
        return;
    }

    let order = {}
    let customerName = document.querySelector('#customer-name').value
    let beverage = document.querySelector('#beverage').value

    order.customerName = customerName
    if (beverage) {
        order.beverage = beverage.toLowerCase()
    }

    $.ajax({
        type: 'POST',
        url: '/orders',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(order),
        success: () => {
            document.querySelector('#customer-name').value = null;
            document.querySelector('#beverage').value = null;
            document.querySelector('.order').style.display = 'none'
            document.querySelector('.success').style.display = 'block'
        }, error: (data) => {
            document.querySelector('#customer-name').value = null;
            document.querySelector('#beverage').value = null;
            document.querySelector('.order').style.display = 'block'
            if (data.status === 500) {
                document.querySelector('.order').innerHTML = '<div class="error">' + data.statusText + '</div>'
            } else {
                document.querySelector('.order').innerHTML = '<div class="error">' + data.responseText + '</div>'
            }
            document.querySelector('.success').style.display = 'none'
        }
    });
}
function reOrder() {
    document.querySelector('.success').style.display = 'none'
    document.querySelector('.order').style.display = 'block'
}
