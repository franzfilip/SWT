# Datenbank starten
```
docker run --name fhbay --rm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=FHBay -p 5432:5432 -it postgres:14.1-alpine
```
# Datenbankdiagram
![alt text](screens/db_diagram.png)

# Interfaces Service
![alt text](screens/services_interfaces.png)

# Services Implementierung
![alt text](screens/services_impl.png)

# Domain
![alt text](screens/domain.png)
