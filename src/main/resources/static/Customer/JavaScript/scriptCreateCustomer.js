$(document).ready(function() {
    $('select').formSelect();
});

$(document).ready(function(){
    $('.datepicker').datepicker({
        format: 'dd.mm.yyyy',
        yearRange: [1900, new Date().getFullYear()],
        minDate: new Date(1900, 1, 1),
        maxDate: new Date()
    });
});

const api = axios.create({
    baseURL: 'http://localhost:8080/api/customer',
    timeout: 1000
});

function addCustomer() {
    api.post("", {
        firstName: document.getElementById("firstName").value,
        middleName: document.getElementById("middleName").value,
        birthdate: document.getElementById("birthdate").value,
        lastName: document.getElementById("lastName").value,
    })
        .then(response => {
            let container = document.getElementById("container");
            let responseContainer = document.getElementById("response").content.cloneNode(true);
            let tab = responseContainer.querySelectorAll('li');
            tab[0].textContent = response.data.id;
            tab[1].textContent = response.data.middleName;
            tab[2].textContent = response.data.firstName;
            tab[3].textContent = response.data.lastName;
            tab[4].textContent = response.data.birthdate;
            container.appendChild(responseContainer);
        })
        .catch(error => {
            alert(error.response.data.message)
        })
}