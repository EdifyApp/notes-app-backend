# Notes Backend

### How to run the backed
1. Install and Run Docker Desktop
2. Build the app by running ```./gradlew bootWar```
3. Run ```docker-compose up --build```
4. Navigate to [http://localhost:8080/notes-api/swagger-ui/index.html](http://localhost:8080/notes-api/swagger-ui/index.html)

### How to check the database
1. SSH into the database docker container
2. Run the command ```psql -d notes -U notesuser```

### How to push a new image to Azure Container Registry
1. Run ```az acr login --name eruditioapps``` to login
2. Then run ```docker build . -t eruditioapps.azurecr.io/notes-app-backend:latest``` to build an image
3. Run ```docker push eruditioapps.azurecr.io/notes-app-backend:latest``` to publish the image to Azure Container Registry

A webhook has been set up to restart the app service when there is a new image

