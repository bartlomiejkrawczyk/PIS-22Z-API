# Setup

Before starting application you need to specify `workspace.properties` file inside `resources/` directory.
The file should contain these properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db
spring.datasource.username=user
spring.datasource.password=password
spring.elasticsearch.uris=http://localhost:9200
```
You can change these values accordingly.
