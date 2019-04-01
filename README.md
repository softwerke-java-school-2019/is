# is

# Usage
```
POST /client (JSON)
GET /client
GET /client/{id}
GET /client/filter
```
## Client JSON representation:
* second name: String
* name: String
* patronymic: String
* birthday: String (date format "dd.MM.yyyy")
## Filter parameters (not required)
* second name
* name
* patronymic
* birthday from
* birthday to
# Example
## Post
```
POST /client {"second name": "Саяхов", "name": "Ильфат", "patronymic": "Раилевич", "birthday": "06.04.1998"}
```
## Get request
```
GET http://localhost:8080/client/filter?name=И
```
## Get response
```
[
  {
    "second name": "Саяхов",
    "name": "Ильфат",
    "patronymic": "Раилевич",
    "birthday": "06.04.1998",
    "id": 1
  },
  {
    "second name": "Иванов",
    "name": "Иван",
    "patronymic": "Иванович",
    "birthday": "04.11.1990",
    "id": 2
  }
]
```
## Get request
```
GET http://localhost:8080/client/filter?birthday from=05.11.1990&birthday to=24.03.2000
```
## Get response
```
[
  {
    "second name": "Саяхов",
    "name": "Ильфат",
    "patronymic": "Раилевич",
    "birthday": "06.04.1998",
    "id": 1
  },
  {
    "second name": "Сидоров",
    "name": "Александр",
    "patronymic": "Анатольевич",
    "birthday": "24.03.2000",
    "id": 3
  }
]
```
