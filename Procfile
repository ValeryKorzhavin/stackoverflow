web: java -jar target/spring-demo-0.0.1-SNAPSHOT.jar -Dadmin.password=$ADMIN_PASSWORD
release: ./mvnw liquibase:update -Dadmin.password=$ADMIN_PASSWORD -DskipTests=true