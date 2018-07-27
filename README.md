**Starting the project**

Both applications should be started in different consoles. \
Clone the repository, enter it and then execute following steps:

Backend:\
`cd RekrutacjaConstada-Core` \
`..\gradlew.bat build bootRun`

Depending on your system use `gradlew.bat` or `gradlew`.

Frontend:\
`cd RekrutacjaConsdata-Web/newsapp` \
`npm run serve`

**Usefull links**

* Main application page: http://localhost:8080 
* Swagger REST documentation: http://localhost:9080/swagger-ui.html


**Technological stack**
1. Backend \
    Package / build manager: gradle
    * Spring Boot (Spring Web, Spring REST)
    * Lombok
    * Vavr
    * Swagger
    * JUnit
    * Mockito
    * Hamcrest
2. Frontend \
    Package / build manager: npm
    * Vue.js
    * VueMaterial
    * VueResource
    * Webpack
    
**Additional points completed**
* Pagination
* Category switching
* News search