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

$(document).ready(function(){
    $('.timepicker').timepicker({
        twelveHour: false,
    });
});

const api = axios.create({
    baseURL: 'http://localhost:8080/api/bill',
    timeout: 1000
});

let sorting = "";

function get() {

    let dateTime = "";
    let dateTimeFrom = "";
    let dateTimeTo = "";

    let date = document.getElementById("purchaseDate").value;
    let time = document.getElementById("purchaseTime").value;
    if (time !== "" && date !== "") {
        dateTime = date + " " + time
    }

    date = document.getElementById("purchaseDateFrom").value;
    time = document.getElementById("purchaseTimeFrom").value;
    if (time !== "" && date !== "") {
        dateTimeFrom = date + " " + time
    }

    date = document.getElementById("purchaseDateTo").value;
    time = document.getElementById("purchaseTimeTo").value;
    if (time !== "" && date !== "") {
        dateTimeTo = date + " " + time
    }

    api.get("", {
        params: {
            id: document.getElementById("id").value,
            customerId: document.getElementById("customerId").value,
            totalPrice: document.getElementById("totalPrice").value,
            totalPriceFrom: document.getElementById("totalPriceFrom").value,
            totalPriceTo: document.getElementById("totalPriceTo").value,
            purchaseDateTime: dateTime,
            purchaseDateTimeFrom: dateTimeFrom,
            purchaseDateTimeTo: dateTimeTo,
            deviceId: document.getElementById("deviceId").value,
            price: document.getElementById("price").value,
            priceFrom: document.getElementById("priceFrom").value,
            priceTo: document.getElementById("priceTo").value,
            quantity: document.getElementById("quantity").value,
            quantityFrom: document.getElementById("quantityFrom").value,
            quantityTo: document.getElementById("quantityTo").value,
            page: document.getElementById("page").value,
            count: document.getElementById("count").value,
            orderBy: sorting


        }
    }).then(response => {
        let container = document.getElementById("containerBill");
        let containerDevice = document.getElementById("containerDevice");
        container.innerHTML = "";
        containerDevice.innerHTML = "";

        for (let i in response.data) {
            let responseContainer = document.getElementById("responseBill").content.cloneNode(true);
            let tab = responseContainer.querySelectorAll('li');
            tab[0].textContent = response.data[i].id;
            tab[1].textContent = response.data[i].customerId;
            tab[2].textContent = response.data[i].totalPrice;
            tab[3].textContent = response.data[i].purchaseDateTime;
            container.appendChild(responseContainer);

            let items = response.data[i].items;

            items.forEach(item => {
                let responseContainer = document.getElementById("responseBillItem").content.cloneNode(true);
                let tab = responseContainer.querySelectorAll('li');
                tab[0].textContent = item.deviceId;
                tab[1].textContent = item.price;
                tab[2].textContent = item.quantity;
                containerDevice.appendChild(responseContainer);

                if (items.indexOf(item) !== 0) {
                    setInvisibleCard(response);
                }

            });

        }
    }).catch(error => {
        alert(error.response.data.message)
    });
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