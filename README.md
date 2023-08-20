## <span style="color:Teal">Shortcut Commands</span>
```bash
docker compose up -d
```
```bash
docker compose up --build -d
```
```bash
docker compose down
```
* export realm
```bash
./keycloak_export.sh
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
docker compose up -d
```

### <span style="color:Teal">Keycloak Users</span>

- user = ```admin``` & password = ```admin``` (Have all admin privileged)
- user = ```google``` & password  = ```123456``` (Regular user)
- user = ```youtube``` & password  = ```123456``` (Regular user)



### <span style="color:Teal">Keycloak Config</span> 
- <strong style="color:Gray">Admin user role : </strong> Realm > Users > Role mapping > Assign Role > Filter by realm roles > Filter by clients > Check all and click to assign.  
<br>
- <strong style="color:Gray">Enable client authentication : </strong> Realm > clients > select targeted client > from setting tab go to Capability config section > enable Client authentication.  
<br>
- <strong  style="color:Gray">Client secret : </strong> Enable client authentication from previous instruction > Go to credential from same window > copy client secret 
(For development 10 star ********** is preferred because when we export and import realm, client secret automatically changed to 10 start)     
<br>



### <span style="color:Teal">Keycloak Realm Export & Import</span>
- <strong style="color:Gray">Manual export : </strong> Login keycloak > select targeted realm > realm settings > action (top right conner) > partial export > check all options > click on export.     
<br>
- <strong style="color:Gray">Auto export using kc.sh: </strong> Just run ```keycloak_export.sh``` from the root directory.  
    <br>
- <strong style="color:Gray">Manual import : </strong> Login keycloak > select targeted realm > realm settings > action (top right conner) > partial import > select file > check all boxes > select overwrite from dropdown > click on import.   
<br>
- <strong style="color:Gray">Auto import by docker compose : </strong> Just move the exported json file to keycloak folder, docker compose will automatically import the realm configurations.
  We have configured volumes inside docker compose file for auto importing realm configurations.