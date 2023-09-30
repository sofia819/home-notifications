### Home Notifications

Personal project to set up a REST API to send and receive status udpates for home devices.

---

#### Run In IntelliJ Locally

```
mvn clean verify
java -jar Services/target/Services-1.0-SNAPSHOT.jar server
```

#### Run Docker Locally

```
docker build --tag sofia819/home-notifications:latest .
docker run -p 8080:8080 sofia819/home-notifications
```
