#Dog Barbershop REST API

All resources are avaleible under api root URL ```/pa165/rest```:

## Customer:
----
### GET /customer
Returns list of all customers
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/customer
```
#### Example response
```json
[{"id":1,"username":"gravida","firstName":"Christian","lastName":"Bennett","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"gravida@example.com","phone":"226-3333","dogs":[]},{"id":3,"username":"felis","firstName":"Kylie","lastName":"Kent","address":{"id":0,"street":"Orci St.","number":202,"city":"Milano","postalCode":539,"country":"Ghana"},"email":"felis@example.com","phone":"455-6193","dogs":[]},{"id":5,"username":"cubilia","firstName":"Judah","lastName":"Oneil","address":{"id":0,"street":"Enim. St.","number":271,"city":"Viersel","postalCode":960555,"country":"Luxembourg"},"email":"cubilia@example.com","phone":"122-5172","dogs":[]},{"id":7,"username":"suscipit","firstName":"Damian","lastName":"Walton","address":{"id":0,"street":"Lorem St.","number":218,"city":"Goslar","postalCode":99582,"country":"Saint Vincent and The Grenadines"},"email":"suscipit@example.com","phone":"930-4139","dogs":[]}]
```

### GET /customer/{id}
Returns detail about customer with the given id
#### Parameters
- id - id of the customer
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/customer/1
```
#### Example response
```json
{"id":1,"username":"gravida","firstName":"Christian","lastName":"Bennett","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"gravida@example.com","phone":"226-3333","dogs":[]}
```
#### Response codes
- 200 - if the resource was found
- 404 - if the resource was not found

### POST /customer
Creates new customer
#### Path parameters
- Request body should contain all required information to create customer
#### Example usage
```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8080/pa165/rest/customer -d '{"username":"ienze","firstName":"Dominik","lastName":"Gmiterko","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"example@example.com","phone":"206-3333","dogs":[]}'
```
#### Response codes
- 200 - if an entity was successfully created (returns created resource)
- 400 - if the input data was incorrect
- 500 - if a problem occurs
        
### PUT /customer/{id}
Updates customer with the given id
#### Path parameters
- id - id of the customer
- Request body should contain all inforamtion than should be modified
#### Example usage
```bash
curl -H "Content-Type: application/json" -X PUT http://localhost:8080/pa165/rest/customer/1 -d '{"username":"linda"}'
```
#### Response codes
- 200 - if an entity was successfully modified
- 500 - if a problem occurs

# Dog Barbershop REST API

All resources are avaleible under api root URL ```/pa165/rest```:

## Customer:
----
### GET /customer
Returns list of all customers
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/customer
```
#### Example response
```json
[{"id":1,"username":"gravida","firstName":"Christian","lastName":"Bennett","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"gravida@example.com","phone":"226-3333","dogs":[]},{"id":3,"username":"felis","firstName":"Kylie","lastName":"Kent","address":{"id":0,"street":"Orci St.","number":202,"city":"Milano","postalCode":539,"country":"Ghana"},"email":"felis@example.com","phone":"455-6193","dogs":[]},{"id":5,"username":"cubilia","firstName":"Judah","lastName":"Oneil","address":{"id":0,"street":"Enim. St.","number":271,"city":"Viersel","postalCode":960555,"country":"Luxembourg"},"email":"cubilia@example.com","phone":"122-5172","dogs":[]},{"id":7,"username":"suscipit","firstName":"Damian","lastName":"Walton","address":{"id":0,"street":"Lorem St.","number":218,"city":"Goslar","postalCode":99582,"country":"Saint Vincent and The Grenadines"},"email":"suscipit@example.com","phone":"930-4139","dogs":[]}]
```

### GET /customer/{id}
Returns detail about customer with the given id
#### Parameters
- id - id of the customer
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/customer/1
```
#### Example response
```json
{"id":1,"username":"gravida","firstName":"Christian","lastName":"Bennett","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"gravida@example.com","phone":"226-3333","dogs":[]}
```
#### Response codes
- 200 - if the resource was found
- 404 - if the resource was not found

### POST /customer
Creates new customer
#### Path parameters
- Request body should contain all required information to create customer
#### Example usage
```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8080/pa165/rest/customer -d '{"username":"ienze","firstName":"Dominik","lastName":"Gmiterko","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"example@example.com","phone":"206-3333","dogs":[]}'
```
#### Response codes
- 200 - if an entity was successfully created (returns created resource)
- 400 - if the input data was incorrect
- 500 - if a problem occurs
        
### PUT /customer/{id}
Updates customer with the given id
#### Path parameters
- id - id of the customer
- Request body should contain all inforamtion than should be modified
#### Example usage
```bash
curl -H "Content-Type: application/json" -X PUT http://localhost:8080/pa165/rest/customer/1 -d '{"username":"linda"}'
```
#### Response codes
- 200 - if an entity was successfully modified
- 500 - if a problem occurs



## Dog:
---
### GET /dog
Returns list of all dogs
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/dog
```
#### Example response
```json
[{"id":13,"name":"Barbarino","breed":"Bracco Italiano","age":5,"customer":{"id":7}},{"id":14,"name":"BigDipper","breed":"Nova Scotia Duck Tolling Retriever","age":15,"customer":{"id":5}},{"id":15,"name":"Skyrocket","breed":"Welsh Springer Spaniel","age":7,"customer":{"id":7}},{"id":16,"name":"Velcro","breed":"West Highland White Terrier","age":8,"customer":{"id":5}},{"id":17,"name":"Maggie","breed":"Pekingese","age":3,"customer":{"id":1}},{"id":18,"name":"Wizkid","breed":"Plott","age":3,"customer":{"id":1}},{"id":19,"name":"Sleestack","breed":"English Toy Spaniel","age":12,"customer":{"id":5}},{"id":20,"name":"Menage","breed":"Alaskan Malamute","age":10,"customer":{"id":5}},{"id":21,"name":"Funky","breed":"Ibizan Hound","age":7,"customer":{"id":5}},{"id":22,"name":"Hawk","breed":"Akita","age":7,"customer":{"id":3}}]
```

### GET /dog/{id}
Returns detail about dog with the given id
#### Parameters
- id - id of the dog
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/dog/13
```
#### Example response
```json
{"id":13,"name":"Barbarino","breed":"Bracco Italiano","age":5,"customer":{"id":7}}
```
#### Response codes
- 200 - if the resource was found
- 404 - if the resource was not found

### POST /dog
Creates new dog
#### Path parameters
- Request body should contain all required information to create dog
#### Example usage
```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8080/pa165/rest/dog -d '{name":"Barbarino","breed":"Bracco Italiano","age":5,"customer":{"id":7}}'
```
#### Response codes
- 200 - if an entity was successfully created (returns created resource)
- 400 - if the input data was incorrect
- 500 - if a problem occurs
        
### PUT /dog/{id}
Updates dog with the given id
#### Path parameters
- id - id of the dog
- Request body should contain all inforamtion than should be modified
#### Example usage
```bash
curl -H "Content-Type: application/json" -X PUT http://localhost:8080/pa165/rest/dog/1 -d '{"name":"Linda"}'
```
#### Response codes
- 200 - if an entity was successfully modified
- 500 - if a problem occurs



## Employee:
----
### GET /employee
Returns list of all employees
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/employee
```
#### Example response
```json
[{"id":9,"username":"admin","firstName":"Admin","lastName":"Admin","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"admin@example.com","phone":"1111","salary":50000.00},{"id":10,"username":"augue","firstName":"Blaine","lastName":"Wall","address":{"id":0,"street":"Orci St.","number":202,"city":"Milano","postalCode":539,"country":"Ghana"},"email":"augue@example.com","phone":"1-319-862-1855","salary":4200.00},{"id":11,"username":"justo","firstName":"Suki","lastName":"Nixon","address":{"id":0,"street":"Enim. St.","number":271,"city":"Viersel","postalCode":960555,"country":"Luxembourg"},"email":"justo@example.com","phone":"1-746-355-4541","salary":5100.00},{"id":12,"username":"accumsan","firstName":"Ahmed","lastName":"Morgan","address":{"id":0,"street":"Lorem St.","number":218,"city":"Goslar","postalCode":99582,"country":"Saint Vincent and The Grenadines"},"email":"accumsan@example.com","phone":"908-4474","salary":38300.00}]
```

### GET /employee/{id}
Returns detail about employee with the given id
#### Parameters
- id - id of the employee
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/employee/9
```
#### Example response
```json
{"id":9,"username":"admin","firstName":"Admin","lastName":"Admin","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"admin@example.com","phone":"1111","salary":50000.00}
```
#### Response codes
- 200 - if the resource was found
- 404 - if the resource was not found

### POST /employee
Creates new employee
#### Path parameters
- Request body should contain all required information to create employee
#### Example usage
```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8080/pa165/rest/employee -d '{"username":"ienze","firstName":"Dominik","lastName":"Gmiterko","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"example@example.com","phone":"206-3333","salary":50000.00}'
```
#### Response codes
- 200 - if an entity was successfully created (returns created resource)
- 400 - if the input data was incorrect
- 500 - if a problem occurs
        
### PUT /employee/{id}
Updates employee with the given id
#### Path parameters
- id - id of the employee
- Request body should contain all inforamtion than should be modified
#### Example usage
```bash
curl -H "Content-Type: application/json" -X PUT http://localhost:8080/pa165/rest/employee/1 -d '{"username":"linda"}'
```
#### Response codes
- 200 - if an entity was successfully modified
- 500 - if a problem occurs

## Dog:
---
### GET /dog
Returns list of all dogs
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/dog
```
#### Example response
```json
[{"id":13,"name":"Barbarino","breed":"Bracco Italiano","age":5,"customer":{"id":7}},{"id":14,"name":"BigDipper","breed":"Nova Scotia Duck Tolling Retriever","age":15,"customer":{"id":5}},{"id":15,"name":"Skyrocket","breed":"Welsh Springer Spaniel","age":7,"customer":{"id":7}},{"id":16,"name":"Velcro","breed":"West Highland White Terrier","age":8,"customer":{"id":5}},{"id":17,"name":"Maggie","breed":"Pekingese","age":3,"customer":{"id":1}},{"id":18,"name":"Wizkid","breed":"Plott","age":3,"customer":{"id":1}},{"id":19,"name":"Sleestack","breed":"English Toy Spaniel","age":12,"customer":{"id":5}},{"id":20,"name":"Menage","breed":"Alaskan Malamute","age":10,"customer":{"id":5}},{"id":21,"name":"Funky","breed":"Ibizan Hound","age":7,"customer":{"id":5}},{"id":22,"name":"Hawk","breed":"Akita","age":7,"customer":{"id":3}}]
```

### GET /dog/{id}
Returns detail about dog with the given id
#### Parameters
- id - id of the dog
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/dog/13
```
#### Example response
```json
{"id":13,"name":"Barbarino","breed":"Bracco Italiano","age":5,"customer":{"id":7}}
```
#### Response codes
- 200 - if the resource was found
- 404 - if the resource was not found

### POST /dog
Creates new dog
#### Path parameters
- Request body should contain all required information to create dog
#### Example usage
```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8080/pa165/rest/dog -d '{name":"Barbarino","breed":"Bracco Italiano","age":5,"customer":{"id":7}}'
```
#### Response codes
- 200 - if an entity was successfully created (returns created resource)
- 400 - if the input data was incorrect
- 500 - if a problem occurs
        
### PUT /dog/{id}
Updates dog with the given id
#### Path parameters
- id - id of the dog
- Request body should contain all inforamtion than should be modified
#### Example usage
```bash
curl -H "Content-Type: application/json" -X PUT http://localhost:8080/pa165/rest/dog/1 -d '{"name":"Linda"}'
```
#### Response codes
- 200 - if an entity was successfully modified
- 500 - if a problem occurs



## Employee:
----
### GET /employee
Returns list of all employees
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/employee
```
#### Example response
```json
[{"id":9,"username":"admin","firstName":"Admin","lastName":"Admin","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"admin@example.com","phone":"1111","salary":50000.00},{"id":10,"username":"augue","firstName":"Blaine","lastName":"Wall","address":{"id":0,"street":"Orci St.","number":202,"city":"Milano","postalCode":539,"country":"Ghana"},"email":"augue@example.com","phone":"1-319-862-1855","salary":4200.00},{"id":11,"username":"justo","firstName":"Suki","lastName":"Nixon","address":{"id":0,"street":"Enim. St.","number":271,"city":"Viersel","postalCode":960555,"country":"Luxembourg"},"email":"justo@example.com","phone":"1-746-355-4541","salary":5100.00},{"id":12,"username":"accumsan","firstName":"Ahmed","lastName":"Morgan","address":{"id":0,"street":"Lorem St.","number":218,"city":"Goslar","postalCode":99582,"country":"Saint Vincent and The Grenadines"},"email":"accumsan@example.com","phone":"908-4474","salary":38300.00}]
```

### GET /employee/{id}
Returns detail about employee with the given id
#### Parameters
- id - id of the employee
#### Example usage
```bash
curl http://localhost:8080/pa165/rest/employee/9
```
#### Example response
```json
{"id":9,"username":"admin","firstName":"Admin","lastName":"Admin","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"admin@example.com","phone":"1111","salary":50000.00}
```
#### Response codes
- 200 - if the resource was found
- 404 - if the resource was not found

### POST /employee
Creates new employee
#### Path parameters
- Request body should contain all required information to create employee
#### Example usage
```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8080/pa165/rest/employee -d '{"username":"ienze","firstName":"Dominik","lastName":"Gmiterko","address":{"id":0,"street":"Pellentesque, Avenue","number":178,"city":"Montemignaio","postalCode":44162,"country":"Ukraine"},"email":"example@example.com","phone":"206-3333","salary":50000.00}'
```
#### Response codes
- 200 - if an entity was successfully created (returns created resource)
- 400 - if the input data was incorrect
- 500 - if a problem occurs
        
### PUT /employee/{id}
Updates employee with the given id
#### Path parameters
- id - id of the employee
- Request body should contain all inforamtion than should be modified
#### Example usage
```bash
curl -H "Content-Type: application/json" -X PUT http://localhost:8080/pa165/rest/employee/1 -d '{"username":"linda"}'
```
#### Response codes
- 200 - if an entity was successfully modified
- 500 - if a problem occurs