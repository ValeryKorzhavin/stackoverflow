
.DEFAULT_GOAL := build-run

run:
	java -jar ./target/spring-demo-*.jar

clean:
	rm -rf ./target

build-run: build run

build:
	./mvnw clean package

update:
	./mvnw versions:update-properties versions:display-plugin-updates

test:
	./mvnw clean test

initial-migration:
	 ./mvnw clean compile liquibase:diff -DskipTests=true && rm /tmp/liquibase_migration*

generate-migration:
	 ./mvnw clean compile liquibase:update liquibase:diff -DskipTests=true && rm /tmp/liquibase_migration*