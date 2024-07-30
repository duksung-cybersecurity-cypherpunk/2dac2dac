spring:
  primary-datasource:
    jdbc-url: jdbc:mysql://database/doctech
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

  secondary-datasource:
    jdbc-url: jdbc:mysql://my-data-db.cxqu0wqkkk7l.ap-northeast-2.rds.amazonaws.com:3306/mydata
    username: admin
    password: doctech1111
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    generate-ddl: true
    database: mysql
    database-platform: org.hibernate.dialect.MySQL8Dialect

open-api:
  base-url: http://apis.data.go.kr/B552657/
  pharmacy:
    key: dLQnuKPjPiEMKrca60jg3f28s5bFSpVGACzbhwSFoUek2RaXv7OpW00pvUhaaV1jgWbjpkwVsL2G6EPq8JZbuA==
    endpoint: ErmctInsttInfoInqireService/getParmacyFullDown
  hospital:
    key: dLQnuKPjPiEMKrca60jg3f28s5bFSpVGACzbhwSFoUek2RaXv7OpW00pvUhaaV1jgWbjpkwVsL2G6EPq8JZbuA==
    endpoint: HsptlAsembySearchService/getHsptlMdcncFullDown

FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]