$(document).ready(() => {
    if (typeof(EventSource) !== 'undefined') {
        let itemsReady = [];

        let source = new EventSource('/outcounter');
        source.onmessage = (event) => {
            let order = JSON.parse(event.data);
            itemsReady.push(order);

            let items = '';
            let iterations = itemsReady.length;
            for (let itemReady of itemsReady) {
                if (!--iterations) {
                    items += '<div class="item show">';
                } else {
                    items += '<div class="item">';
                }
                items += '<div title="' + itemReady.customerName + '">' + itemReady.customerName + '</div><div>' + itemReady.beverage + '</div><div><div class="number">1</div></div><div>' + itemReady.createdBy + '</div></div>'
            }

            if (itemsReady.length > 4) {
                document.querySelector('#items').className = 'more';
            }

            document.querySelector('#items').innerHTML = items;
        };
    } else {
        document.querySelector('#out').innerHTML = '<img src="../images/bsod.png">';
    }
});

function order() {
    if (!document.querySelector('#customer-name') || !document.querySelector('#beverage')) {
        return;
    }
    if (!document.querySelector('#customer-name').value || !document.querySelector('#beverage').value) {
        return;
    }

    let customerName = document.querySelector('#customer-name').value
    let beverage = document.querySelector('#beverage').value

    let order = {
        customerName: customerName,
        beverage: beverage
    };

    $.ajax({
        type: 'POST',
        url: '/orders',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(order),
        success: () => {
            document.querySelector('#customer-name').value = null;
            document.querySelector('#beverage').value = null;
        }
    });
}
