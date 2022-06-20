## UrlShortener

Url shortener demo application using PERN stack (PostgreSQL, Express, React, Nodejs).

### Run application
1. Run `docker-compose up -d --build`
2. Open http://localhost:8080 \
   ![Frontend](screenshots/frontend.jpg)
3. To stop the application, run `docker-compose down`

### Run unit tests
1. Server: `cd server && npm t`\
   ![Server test coverage](screenshots/server-test-coverage.jpg)
2. Web: `cd web && npm t`\
   ![Web test coverage](screenshots/web-test-coverage.jpg)

### Run api test
1. `docker-compose up -d --build`
2. `cd api-test && npm t`\
   ![Api test coverage](screenshots/api-test-result.jpg)
    
### Entity relation diagrams
View `architecture/entity.puml` for details


