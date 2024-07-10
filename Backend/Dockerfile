FROM openjdk:17 AS builder

WORKDIR /home/app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM tomcat:10.1

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY --from=builder /home/app/target/LoginBackend.war /usr/local/tomcat/webapps/ROOT.war


EXPOSE 8080

CMD ["catalina.sh", "run"]