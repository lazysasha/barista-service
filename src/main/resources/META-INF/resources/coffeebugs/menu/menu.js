$(document).ready(() => {
    $.ajax({
        type: 'GET',
        url: '/menu',
        success: (data) => {
            document.querySelector('#greeting').innerHTML = data.greetingMessage;
            let menu = '<div class="coffee">';
            for (let item of data.menu) {
                menu += '' +
                    '<div class="type">' + item.beverage + '</div>' +
                    '<div class="price">&euro;' + item.price.toFixed(2) + '</div>';
            }
            menu += '</div>';
            document.querySelector('#menu').innerHTML = menu;
        }
    });
});
