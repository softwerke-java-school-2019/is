# is

# Usage
```
POST /api/<entity> (JSON)
GET /api/<entity>?<filter parameters><paging paremeters>
GET /api/<entity>/{id}
```
# Entities:
## Client:
* id: Number
* secondName: String
* name: String
* patronymic: String
* birthday: String (date format "dd.MM.yyyy")
## Device:
* id: Number
* company: String
* name: String
* released: String (date format "dd.MM.yyyy")
* color: String 
* type: String
* price: Number
## Bill:
* id: Number
* clientId: Number
* totalPrice: Number (all bill items total price)
* date: String (purchasing date, format "dd.MM.yyyy HH:mm:ss")
* items: BillItem list
## Bill item (created with Bill):
* deviceId: Number
* price: Number (item price at the purchase time)
* quantity: Number
# Post feauters
* Id field generated automatically
* totalPrice in Bill and price in BillItem calculated based on information about devices
# Get feauters
* orderBy: String (field to order by, default: id)
* page: Number (page number, default: 1)
* count: Number (items count displayed on page, default: 10, max: 1000)
* there are some fields with ranging: birthday, price, date, totalPrice, released. Use:
<field>From and <field>To.
  
# Device colors and types:
## Color:
* name: String
* rgb: Number
## Type:
* name: String
## Usage
```
GET /api/device/color

POST /api/device/color
{
  "name": "purple",
  "rgb": 123
}

GET /api/device/type

POST /api/device/type
{
  "name": "mouse",
}
```
 
# Example
```
POST /api/client 
{ 
  "secondName": "Саяхов",
  "name": "Ильфат",
  "patronymic": "Раилевич",
  "birthday": "06.04.1998"
}

POST /api/bill
{
	"clientId": 0,
	"date": "13.11.2018 12:35:00",
	"items": [
		{
			"deviceId": 0,
			"quantity": 2
		},
		
		{
			"deviceId": 1,
			"quantity": 1
		}
		] 
}

GET /api/device?priceFrom=100&priceTo=10000&orderBy=price&page=2&count=3&orderBy=type
```
