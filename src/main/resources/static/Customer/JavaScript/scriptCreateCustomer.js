$(document).ready(function() {
    $('select').formSelect();
});

const api = axios.create({
    baseURL: 'http://localhost:8080/api/customer',
    timeout: 1000
});

function addCustomer() {
    api.post("", {
        firstName: document.getElementById("firstName").value,
        middleName: document.getElementById("middleName").value,
        birthdate: formatDate(document.getElementById("birthdate").value),
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

function formatDate(dateStr) {
    if (dateStr === '') {
        return ''
    }

    let date = new Date(dateStr);

    let dd = date.getDate();
    if (dd < 10) dd = '0' + dd;

    let mm = date.getMonth() + 1;
    if (mm < 10) mm = '0' + mm;

    let yy = date.getFullYear();

    return dd + '.' + mm + '.' + yy;
}