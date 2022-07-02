# Notes Backend

### How to run the backed
1. Install and Run Docker Desktop
2. Build the app by running ```./gradlew bootWar```
3. Run ```docker-compose up --build```
4. Navigate to [http://localhost:8080/notes-api/swagger-ui/index.html](http://localhost:8080/notes-api/swagger-ui/index.html)

### How to check the database
1. SSH into the database docker container
2. Run the command ```psql -d notes -U notesuser```


