# NISUM INTEGRATION BCI - JAVA EXAM

## Getting started

### Stack
| Reference |     Value     |
|-----------|:-------------:|
| JDK       |      17       |
| Gradle    |    8.1.1      |

## BULD
Para compilar el proyecto se debe usar el siguiente comando:
```agsl
gradle build
```

## RUN
Para ejecutar el proyecto se debe usar el siguiente comando:
```agsl
gradle bootRun
```

## TEST
Para ejecutar las test del proyecto se debe usar el siguiente comando:
```agsl
gradle test
```

Para probar el endpoint de registro puede ejeuctar el siguiente curl o importarlo en postman.

### Contract validation

Regex por defecto como validacion de negocio.

```agsl
      email: "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$" #Minimum six characters, can use a dot before @ and ending with google.com or gmail.com
      password: "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$" #Minimum eight characters, at least one letter and one number
```

#### Request

```agsl
curl --location 'http://localhost:8080/v1/users/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Carlos Montoya",
    "email": "andres.montoya@gmail.com",
    "password": "a123456789",
    "phones": [
        {
            "number": "300",
            "cityCode": "1",
            "countryCode": "57"
        }
    ]
}'
```

#### Response
```agsl
{
    "id": "55e18805-3495-48d2-80a4-9a4f5f18c422",
    "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJuaXN1bSIsInN1YiI6ImFuZHJlcy5tb250b3lhQGdtYWlsLmNvbSIsImlhdCI6MTY4ODU5MzEyMywiZXhwIjoxNjg4NTk0MzIzfQ.C6iV5z-JT2jNhYWSkc0PnUPFXrAuVoL7MBQNUTyKEYgDWRRBuIzaOVdaqkaqPf24sX2Zs4l5srGOdcinGAvFkGXSa5gVUbVt_QZsZ8TbpnoqXvP97ZVC8PNky-lg2scMPb9gTS9HNv9QTJHZrPpdA8copkqTipx9HQy7bmqCAkuHye_4qmM7qbO_gGBqkZZ_Y-TwzavDEF4TXOwzMuRsI0ZRutYl8IHVL93FgHLnnnWR73jxdCOU0ujdccIFmTqm_3nWQXM-oqSt6jVbnmh9wtCAZlih3QXUhKlscR1YITJtkDhyP0aLIesd8UCgguKORNiDtKJLqUWtCulSrnj6lg",
    "created": "2023-07-05T16:38:43.774023",
    "modified": null,
    "lastLogin": "2023-07-05T16:38:43.774023",
    "active": false
}
```