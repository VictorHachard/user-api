[![Alpha](https://raster.shields.io/badge/maturity-Alpha-red.png)]()
[![MIT license](https://img.shields.io/badge/license-MIT-green)](https://mit-license.org/)
[![BCH compliance](https://bettercodehub.com/edge/badge/VictorHachard/user-api?branch=main)](https://bettercodehub.com/)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c3b473253d5f47f1b8eecd1b9f5f200c)](https://www.codacy.com/gh/VictorHachard/user-api/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=VictorHachard/user-api&amp;utm_campaign=Badge_Grade)
[![CI/CD](https://github.com/VictorHachard/user-api/actions/workflows/actions-ci-cd-spring-boot-app.yml/badge.svg)](https://github.com/VictorHachard/user-api/actions/workflows/actions-ci-cd-spring-boot-app.yml)

# User API

## Build

### Build and Deploy with Nginx on Linux

Edit the `application.properties` located in the */src/main/resources* folder:

```
mvn compile
mvn clean package
mvn exec:java -Dexec.mainClass=com.user.RunApplication
```

Then go to the `localhost:8080` page.

### Build and Deploy using Github Actions - CI/CD

Using the `actions-ci-cd-spring-boot-app.yml` workflow:

Make sure on that the target directory was the right permission (`sudo chmod 777 target_directory`).

#### Github secrets

-   HOST
-   PASSWORD
-   PATH
-   PORT
-   USERNAME
-   RESTART: command to restart service

## Documentation

-   [User API - local](http://localhost:8080/swagger-ui/index.htm)

## What I Learned

-   Java advanced
-   Building an API with Spring Boot
-   Swagger
-   PostgreSQL
-   JPA

## Authors & Contributors

-   **Hachard Victor** - *Initial work* - [VictorHachard](https://github.com/VictorHachard)

## License

This project is licensed under the MIT License - see the [LICENSE.md](../master/LICENSE) file for details.
