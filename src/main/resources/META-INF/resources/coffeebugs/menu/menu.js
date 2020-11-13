$(document).ready(function(e) {
    $.ajax({
        type: 'GET',
        url: '/menu',
        success: function (data) {
            document.querySelector('#greeting').innerHTML = data.greetingMessage;
            let menu = '';
            for (let item of data.menu) {
                menu += '' +
                    '<div class="coffee-type">' + item.beverage + '</div>' +
                    '<div class="coffee-price-tall">' + item.price.toFixed(2) + '</div>' +
                    '<div class="coffee-price-grande sold-out">' + (2 * item.price).toFixed(2) + '</div>' +
                    '<div class="coffee-price-venti sold-out">' + (3 * item.price).toFixed(2) + '</div>';
            }
            document.querySelector('#menu').innerHTML = menu;
        }
    });
});
