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
    baseURL: 'http://localhost:8080/api/device',
    timeout: 1000
});


getColors();

getTypes();

function getColors() {
    let selectorColor = document.getElementById("colorName");

    api.get("/color", {}).then(response => {
        for (let i in response.data) {
            let temp = document.getElementById("option").content.cloneNode(true);
            let option = temp.querySelector("option");
            option.value = response.data[i];
            option.textContent = response.data[i];
            selectorColor.appendChild(option);
        }

    }).catch(error => {
        alert("Error loading colors")
    });
}

function getTypes() {
    let selectorType = document.getElementById("deviceType");

    api.get("/type", {}).then(response => {
        for (let i in response.data) {
            let temp = document.getElementById("option").content.cloneNode(true);
            let option = temp.querySelector("option");
            option.value = response.data[i];
            option.textContent = response.data[i];
            selectorType.appendChild(option);
        }

    }).catch(error => {
        alert("Error loading types")
    });
}

function addColor() {
    api.post("/color", {
        name: document.getElementById("colorNameCreate").value,
        rgb: document.getElementById("colorCreate").value
    })
        .then(response => {
            alert("Color added");
        })
        .catch(error => {
            alert(error.response.data.message)
        });
}

function addType() {
    api.post("/type",{
        name: document.getElementById("typeName").value
    })
        .then(response => {
            alert("Type added")
        })
        .catch(error => {
            alert(error.response.data.message)
        })
}

function addDevice() {
    api.post("", {
        manufacturer: document.getElementById("manufacturer").value,
        modelName: document.getElementById("modelName").value,
        manufactureDate: document.getElementById("manufactureDate").value,
        price: document.getElementById("price").value,
        colorName: document.getElementById("colorName").value,
        deviceType: document.getElementById("deviceType").value
    })
        .then(response => {
            let container = document.getElementById("container");
            let responseContainer = document.getElementById("response").content.cloneNode(true);
            let tab = responseContainer.querySelectorAll('li');
            tab[0].textContent = response.data.id;
            tab[1].textContent = response.data.manufacturer;
            tab[2].textContent = response.data.modelName;
            tab[3].textContent = response.data.price;
            tab[4].textContent = response.data.manufactureDate;
            tab[5].textContent = response.data.deviceType;
            tab[6].style = "background: " + response.data.colorHex;
            container.appendChild(responseContainer);
        })
        .catch(error => {
            alert(error.response.data.message)
        })
}