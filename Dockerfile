FROM maven:3.9.4-amazoncorretto-21

COPY . /usr/src/app/
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package

COPY "./Services/target/Services-1.0-SNAPSHOT.jar"  /usr/src/app.jar

EXPOSE 8080

CMD java -jar /usr/src/app.jar server  