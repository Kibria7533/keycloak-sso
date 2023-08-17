## <span style="color:Teal">Shortcut Commands</span>
```bash
docker compose up
```
```bash
docker compose up --build
```

## <span style="color:Teal">Documents</span>

### <span style="color:Teal">Install MySQL</span>

- Create volume for mysql to persist data.
```bash
docker volume create mysql_volume
```

- Create a network and install mysql inside this network. Because we need to have mysql and keycloak in the same network.
```bash
docker network create mysql_network
```

- Run below command to install mysql with user <span style="color:Green">root</span> and password <span style="color:Green">root</span>
```bash
docker run -d --name db_mysql -p 3306:3306 -p 33060:33060 --network mysql_network -v mysql_volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root --restart=always mysql
```

- Create the database <span style="color:Green">keycloak_db</span> manually

### <span style="color:Teal">Install Keycloak & mailhog</span>
- Run below command to install keycloak and mailhog
```bash
docker compose up
```
 
### <span style="color:Teal">Keycloak Realm Export & Import</span>
- <strong>Export : </strong> Login keycloak > select targeted realm > realm settings > action (top right conner) > partial export > check all options > click on export.     
<br>
- <strong>Manual import : </strong> Login keycloak > select targeted realm > realm settings > action (top right conner) > partial import > select file > check all boxes > select overwrite from dropdown > click on import.   
<br>
- <strong>Auto import by docker compose : </strong> Just move the exported json file to keycloak folder, docker compose will automatically import the realm configurations.
  We have configured volumes inside docker compose file for auto importing realm configurations.