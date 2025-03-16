
## Technologies and Libraries

- **Java 21**

- **Spring Boot 3**

- **Spring Data JPA**

- **PostgreSQL**

- **PostGIS**

- **Lombok**

- **Exception Handling**

- **JUnit & Mockito**

- **Swagger**

- **Log4J**

## Prerequisites
- **Docker Desktop**
- **Java 21** (If you are running the application locally)

## Installation and Usage

1. Clone the repo
```powershell

https://github.com/sacidpak/courier-tracking-service.git

```

2. You need to run the
```powershell

 mvn clean package 
 
```

3. In the project directory
```powershell 

docker compose up --build

```


## Example Requests
### Update Courier Location
```powershell

curl -X 'POST' \
  'http://localhost:8080/api/couriers/location' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "courierId": 1,
  "latitude": 40.9923307,
  "longitude": 29.1244229
}'

```

### Total Distance Travelled By Courier
```powershell

curl -X 'GET' \
  'http://localhost:8080/api/couriers/total-distance/1' \
  -H 'accept: */*'

```


# Swagger Documentation
```powershell

http://localhost:8080/swagger-ui.html

```

# Connect Database In Bash
```powershell

> docker exec -it courier-tracking-service-postgis-1 /bin/bash
> psql -U postgres
> \c courier-tracking
> \dt                                                  Listing-tables
> select * from courier_location;
> select * from courier_distance;
> select * from courier_store_entrance;

```

## Contact
Muhammed Sacid PAK - sacidpak@gmail.com
