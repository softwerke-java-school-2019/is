const api = axios.create({
    baseURL: 'http://localhost:8080/api/device',
    timeout: 5000
});

function get() {
    let param = getParam();
    api.get("?" + param , {}).then(response => {
        let output = "";
        for (let i in response.data) {
            output += "<div class='card'>";
            output += "<div class='card-tabs'>";
            output += "<ul class='tabs tabs-fixed-width'>";
            output += "<li class = 'tab'>" + response.data[i].manufacturer + "</li>";
            output += "<li class = 'tab'>" + response.data[i].modelName + "</li>";
            output += "<li class = 'tab'>" + response.data[i].price + "</li>";
            output += "<li class = 'tab'>" + response.data[i].manufactureDate + "</li>";
            output += "<li class = 'tab'>" + response.data[i].deviceType + "</li>";
            output += "<li class = 'tab'>" + response.data[i].colorName + "</li>";
            output += "</ul>";
            output += "</div>";
            output += "</div>";
        }
        document.getElementById("container").innerHTML = output;
    }).catch(error => {
        alert(error.response.data.message)
    });
}

function getParam() {
    let param = "";
    let manufacturer = document.getElementById("manufacturer").value;
    if (manufacturer != '')
        param += 'manufacturer=' + manufacturer + '&';
    let deviceId = document.getElementById("deviceId").value;
    if (deviceId != '')
        param += 'deviceId=' + deviceId + '&';
    let modelName = document.getElementById("modelName").value;
    if (modelName != '')
        param += 'modelName=' + deviceId + '&';
    let price = document.getElementById("price").value;
    if (price != '')
        param += 'price=' + price + '&';
    let priceFrom = document.getElementById("priceFrom").value;
    if (priceFrom != '')
        param += 'priceFrom=' + priceFrom + '&';
    let priceTo = document.getElementById("priceTo").value;
    if (priceTo != '')
        param += 'priceTo=' + priceTo + '&';
    let manufactureDate = document.getElementById("manufactureDate").value;
    if (manufactureDate != '')
        param += 'manufactureDate=' + manufactureDate + '&';
    let manufactureDateFrom = document.getElementById("manufactureDateFrom").value;
    if (manufactureDateFrom != '')
        param += 'manufactureDateFrom=' + manufactureDateFrom + '&';
    let manufactureDateTo = document.getElementById("manufactureDateTo").value;
    if (manufactureDateTo != '')
        param += 'manufactureDateTo=' + manufactureDateTo + '&';
    let deviceType = document.getElementById("deviceType").value;
    if (deviceType != '')
        param += 'deviceType=' + deviceType + '&';
    let colorName = document.getElementById("colorName").value;
    if (colorName != '')
        param += 'colorName=' + colorName + '&';
    let page = document.getElementById("page").value;
    if (page != '')
        param += 'page=' + page + '&';
    let count = document.getElementById("count").value;
    if (count != '')
        param += 'count=' + count + '&';
    let sortBy = document.getElementById("sortBy").value;
    if (sortBy != '')
        param += 'orderBy=' + sortBy + '&';

    if (param != '') {
        param.substr(0, param.length - 1)
    }

    return param;
}
