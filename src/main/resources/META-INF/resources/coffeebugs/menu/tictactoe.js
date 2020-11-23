let play = true;

function placeX(x,y) {
    if (!play) {
        return;
    }

    document.querySelector('#pos-' + x + y).innerHTML = '&nbsp;X&nbsp;';
    if (gameOver('&nbsp;X&nbsp;')) {
        play = false;
        return;
    }
    placeO();
    if (gameOver('&nbsp;O&nbsp;')) {
        play = false;
    }
}

function placeO() {
    let x = Math.floor(Math.random() * 3);
    let y = Math.floor(Math.random() * 3);
    if (document.querySelector('#pos-' + x + y).innerHTML !== '&nbsp;X&nbsp;' && document.querySelector('#pos-' + x + y).innerHTML !== '&nbsp;O&nbsp;') {
        document.querySelector('#pos-' + x + y).innerHTML = '&nbsp;O&nbsp;';
    } else {
        placeO();
    }
}

function gameOver(player) {
    if (!play) {
        return;
    }
    if (document.querySelector('#pos-00').innerHTML === player && document.querySelector('#pos-01').innerHTML === player && document.querySelector('#pos-02').innerHTML === player) {
        document.querySelector('.strike').className = 'strike strike00-02';
        return true;
    }
    if (document.querySelector('#pos-10').innerHTML === player && document.querySelector('#pos-11').innerHTML === player && document.querySelector('#pos-12').innerHTML === player) {
        document.querySelector('.strike').className = 'strike strike10-12';
        return true;
    }
    if (document.querySelector('#pos-20').innerHTML === player && document.querySelector('#pos-21').innerHTML === player && document.querySelector('#pos-22').innerHTML === player) {
        document.querySelector('.strike').className = 'strike strike20-22';
        return true;
    }
    if (document.querySelector('#pos-00').innerHTML === player && document.querySelector('#pos-10').innerHTML === player && document.querySelector('#pos-20').innerHTML === player) {
        document.querySelector('.strike').className = 'strike strike00-20';
        return true;
    }
    if (document.querySelector('#pos-01').innerHTML === player && document.querySelector('#pos-11').innerHTML === player && document.querySelector('#pos-21').innerHTML === player) {
        document.querySelector('.strike').className = 'strike strike01-21';
        return true;
    }
    if (document.querySelector('#pos-02').innerHTML === player && document.querySelector('#pos-12').innerHTML === player && document.querySelector('#pos-22').innerHTML === player) {
        document.querySelector('.strike').className = 'strike strike02-22';
        return true;
    }
    if (document.querySelector('#pos-00').innerHTML === player && document.querySelector('#pos-11').innerHTML === player && document.querySelector('#pos-22').innerHTML === player) {
        document.querySelector('.strike').className = 'strike strike00-22';
        return true;
    }
    if (document.querySelector('#pos-20').innerHTML === player && document.querySelector('#pos-11').innerHTML === player && document.querySelector('#pos-02').innerHTML === player) {
        document.querySelector('.strike').className = 'strike strike20-02';
        return true;
    }
    return false;
}