# Build

```maven
mvn clean install
```

# Run Tests

```maven
mvn verify
```

# Launch Application

```maven
mvn spring-boot:run
```

# Usage

### Add Person

#### Endpoint

```Java
POST: /v1/exercise/addPerson
```
#### Request Body

```JSON
{
 "firstName" : "John",
 "lastName : "Smith"
}
```

### Edit Person

#### Endpoint

```Java
PATCH: /v1/exercise/editPerson
```

#### Request Parameter

```Java
long: id
```

#### Request Body

```JSON
{
 "firstName" : "Sean",
 "lastName : "Smith"
}
```

### Delete Person

#### Endpoint

```Java
DELETE: /v1/exercise/deletePerson
```

#### Request Parameter

```Java
long: id
```

### Add Address

#### Endpoint

```Java
POST: /v1/exercise/addAddress
```

#### Request Parameter

```Java
long: id
```

#### Request Body

```JSON
{
 "street" : "STREET",
 "city: "CITY",
 "state: "STATE",
 "postalCode: "POSTAL CODE",
}
```

### Edit Address

#### Endpoint

```Java
PATCH: /v1/exercise/editAddress
```

#### Request Parameter

```Java
long: id
```

#### Request Body

```JSON
{
 "street" : "STREET",
 "city: "CITY",
 "state: "STATE",
 "postalCode: "POSTAL CODE",
}
```

### Delete Address

#### Endpoint

```Java
DELETE: /v1/exercise/deleteAddress
```

#### Request Parameter

```Java
long: id
```

### Count Persons

#### Endpoint

```Java
GET: /v1/exercise/count
```

### List Persons

#### Endpoint

```Java
GET: /v1/exercise/persons
```

