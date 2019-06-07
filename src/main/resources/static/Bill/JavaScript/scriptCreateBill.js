$(document).ready(function() {
    $('select').formSelect();
});

const api = axios.create({
    baseURL: 'http://localhost:8080/api/bill',
    timeout: 1000
});

const apiCustomer = axios.create({
    baseURL: 'http://localhost:8080/api/customer',
    timeout: 1000
});

const apiDevice = axios.create({
    baseURL: 'http://localhost:8080/api/device',
    timeout: 1000
});

let deviceExists = false;
let customerExists = false;

let items = [];

function addBill() {
    if (!customerExists || items.length === 0) {
        return
    }

    let json = [];

    for (let i = 0; i < items.length; i++) {
        let item = items[i];
        let tab = item.querySelectorAll('li');
        json.push({deviceId: tab[0].textContent, quantity: tab[1].textContent});
    }

    api.post("", {
        customerId: document.getElementById("customerId").value,
        items: json
    })
        .then(response => {
            let container = document.getElementById("containerBill");
            let responseContainer = document.getElementById("responseBill").content.cloneNode(true);
            let tab = responseContainer.querySelectorAll('li');
            tab[0].textContent = response.data.id;
            tab[1].textContent = response.data.customerId;
            tab[2].textContent = response.data.totalPrice;
            tab[3].textContent = response.data.purchaseDateTime;
            container.appendChild(responseContainer);

            let items = response.data.items;

            items.forEach(item => {
                let container = document.getElementById("containerDevice");
                let responseContainer = document.getElementById("responseBillItem").content.cloneNode(true);
                let tab = responseContainer.querySelectorAll('li');
                tab[0].textContent = item.deviceId;
                tab[1].textContent = item.price;
                tab[2].textContent = item.quantity;
                container.appendChild(responseContainer);

                if (items.indexOf(item) !== 0) {
                    setInvisibleCard(response);
                }

            });

        })
        .catch(error => {
            alert(error.response.data.message)
        })
}

function setInvisibleCard(response) {
    let container = document.getElementById("containerBill");
    let responseContainer = document.getElementById("responseBill").content.cloneNode(true);
    let tab = responseContainer.querySelectorAll('li');
    tab[0].textContent = response.data.id;
    tab[1].textContent = response.data.customerId;
    tab[2].textContent = response.data.totalPrice;
    tab[3].textContent = response.data.purchaseDateTime;
    responseContainer.querySelector('div').style.visibility = 'hidden';
    container.appendChild(responseContainer);
}

function getCustomer() {
    let id = document.getElementById("customerId").value;

    let cardId = "customerInfo";

    if (id === "") {
        customerExists = false;
        let card = document.getElementById(cardId);
        if (card !== null) {
            card.remove()
        }
        return
    }

    apiCustomer.get("/" + id, {}).then(response => {
        customerExists = true;
        let container = document.getElementById("containerCustomerDevice");

        let card = document.getElementById(cardId);
        if (card !== null) {
            card.remove()
        }

        let customer = document.getElementById("customer").content.cloneNode(true);
        card = customer.querySelector('div');
        card.id = cardId;
        let items = customer.querySelectorAll('li');
        items[0].textContent = response.data.middleName;
        items[1].textContent = response.data.firstName;
        items[2].textContent = response.data.lastName;
        items[3].textContent = response.data.birthdate;
        container.appendChild(customer);
    })
        .catch(error => {
            customerExists = false;
            let container = document.getElementById("containerCustomerDevice");

            let card = document.getElementById(cardId);
            if (card !== null) {
                card.remove()
            }

            let customerError = document.getElementById("error").content.cloneNode(true);
            card = customerError.querySelector('div');
            card.id = cardId;
            let item = customerError.querySelector('li');
            item.textContent = "Клиента с таким ID не существует: " + id;
            container.appendChild(customerError);
        })
}

function getDevice() {
    let id = document.getElementById("deviceId").value;
    let cardId = "deviceInfo";

    if (id === "") {
        deviceExists = false;
        let card = document.getElementById(cardId);
        if (card !== null) {
            card.remove()
        }
        return
    }

    apiDevice.get("/" + id, {}).then(response => {
        deviceExists = true;
        let container = document.getElementById("containerCustomerDevice");

        let card = document.getElementById(cardId);
        if (card !== null) {
            card.remove()
        }

        let device = document.getElementById("device").content.cloneNode(true);
        card = device.querySelector('div');
        card.id = cardId;
        let items = device.querySelectorAll('li');
        items[0].textContent = response.data.manufacturer;
        items[1].textContent = response.data.modelName;
        items[2].textContent = response.data.price;
        items[3].textContent = response.data.manufactureDate;
        items[4].style.background = response.data.colorHex;
        container.appendChild(device);
    })
        .catch(error => {
            deviceExists = false;
            let container = document.getElementById("containerCustomerDevice");

            let card = document.getElementById(cardId);
            if (card !== null) {
                card.remove()
            }

            let deviceError = document.getElementById("error").content.cloneNode(true);
            card = deviceError.querySelector('div');
            card.id = cardId;
            let item = deviceError.querySelector('li');
            item.textContent = "Девайса с таким ID не существует: " + id;
            container.appendChild(deviceError);
        })
}

function addItem() {
    let id = document.getElementById("deviceId").value;
    let quantity = document.getElementById("quantity").value;

    if (id === "" || quantity === "" || !deviceExists) {
        return
    }

    let container = document.getElementById("billItems");
    let item = document.getElementById("billItem").content.cloneNode(true);
    items.push(item.querySelector('div'));
    let tab = item.querySelectorAll('li');
    tab[0].textContent = id;
    tab[1].textContent = quantity;
    container.appendChild(item);
}

function removeItem() {
    let item = items.pop();

    if (item !== undefined) {
        item.remove()
    }
}

function clearItems() {
    items = [];

    document.getElementById("billItems").innerHTML = "";
}