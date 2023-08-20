#!/bin/bash

# Export and store in docker container
docker exec -it keycloak-dev /bin/bash -c "cd /opt/keycloak/bin/ && ./kc.sh export --dir ./export/realm-export.json"

# Copy to local machine
docker cp keycloak-dev:/opt/keycloak/bin/export/realm-export.json ./keycloak
