function regUser(){
    let name = document.getElementById('name').value;
    let surname = document.getElementById('surname').value;
    let username = document.getElementById('username').value;
    let email = document.getElementById('email').value;
    let password1 = document.getElementById('password1').value;
    let password2 = document.getElementById('password2').value;

    if(password1!==password2){
       document.getElementById('message').textContent = 'Passwords do not match!';
       return; 
    }
    else{
        document.getElementById('message').textContent = '';
    }

    let data = {
        type: 'register',
        data: [name, surname, username, email, password1]
    };

    let JSONDATA = JSON.stringify(data);

    fetch('http://localhost/something', {
        method: 'POST',
        body: JSONDATA,
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        if(data.status === 'success'){
            window.location.href = 'login.html';
        }
        else{
            console.log('Error: ', data.data);
        }
    });
}

function logUser(){
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;

    let data = {
        type: 'login',
        data: [email, password]
    };

    let JSONDATA = JSON.stringify(data);

    fetch('http://localhost/something', {
        method: 'POST',
        body: JSONDATA,
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if(data.status === 'success'){
            window.location.href = 'wine.html';
        }
        else{
            console.log('Error: ', data.data);
        }
    });
}