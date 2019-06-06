$(document).ready(function() {
    $('select').formSelect();
});

const api = axios.create({
    baseURL: 'http://localhost:8080/api/device',
    timeout: 1000
});

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
    alert("Error")
});

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
    alert("Error")
});

let sorting = "";

function get() {
    let container = document.getElementById("container");
    container.innerHTML = "";


    api.get("", {
        params: {
            id: document.getElementById("deviceId").value,
            manufacturer: document.getElementById("manufacturer").value,
            modelName: document.getElementById("modelName").value,
            price: document.getElementById("price").value,
            priceFrom: document.getElementById("priceFrom").value,
            priceTo: document.getElementById("priceFrom").value,
            manufactureDate: formatDate(document.getElementById("manufactureDate").value),
            manufactureDateFrom: formatDate(document.getElementById("manufactureDateFrom").value),
            manufactureDateTo: formatDate(document.getElementById("manufactureDateTo").value),
            deviceType: document.getElementById("deviceType").value,
            colorName: document.getElementById("colorName").value,
            page: document.getElementById("page").value,
            count: document.getElementById("count").value,
            orderBy: sorting


        }
    }).then(response => {

        for (let i in response.data) {
            let responseContainer = document.getElementById("response").content.cloneNode(true);
            let tab = responseContainer.querySelectorAll('li');
            tab[0].textContent = response.data[i].id;
            tab[1].textContent = response.data[i].manufacturer;
            tab[2].textContent = response.data[i].modelName;
            tab[3].textContent = response.data[i].price;
            tab[4].textContent = response.data[i].manufactureDate;
            tab[5].textContent = response.data[i].deviceType;
            tab[6].style = "background: " + response.data[i].colorHex;
            container.appendChild(responseContainer);
        }
    }).catch(error => {
        alert(error.response.data.message)
    });
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

function addSorting() {
    let val = $('#sortBy').val();
    let params = sorting.split(",");

    if (val === "" || params.indexOf(val) !== -1 ) {
        return
    }

    let check = $('#order').is(":checked");
    let text = $('#sortBy option:selected').text();
    if (check) {
        val = "-" + val;
        text += ": по убыванию"
    }
    else {
        text += ": по возростанию"
    }


    let card = document.getElementById("card");
    card.style.display = "inline";
    sorting += val + ",";
    let container = document.getElementById("containerSort");
    let template = document.getElementById("addSort").content.cloneNode(true);
    let tab = template.querySelector('p');
    tab.textContent = text;
    tab.id = val + "sort";
    container.appendChild(template);
}

function removeSorting() {
    let params = sorting.split(",");
    let param = params[params.length - 2];
    sorting = sorting.replace(param + ",", "");

    let elem = document.getElementById(param + "sort");
    elem.remove();

    if (sorting === "") {
        document.getElementById("card").style.display = "none";
    }
}

function clearSortings() {
    sorting = "";
    document.getElementById("containerSort").innerHTML = "";
}