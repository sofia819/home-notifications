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
docker run -p 8080:8080 -e START_HOUR="8" -e END_HOUR="18" -e DOOR_TIME_LIMIT="10" -e TWILIO_ACCOUNT_SID="TWILIO_ACCOUNT_SID" -e TEXTBELT_API_KEY="API_KEY" -e RECIPIENTS="COMMA_SEPARATED_PHONE_NUMBERS" sofia819/home-notifications
```
