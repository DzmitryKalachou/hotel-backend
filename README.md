#**Hotel backend**
**Java:** 8  
**Spring Boot:** `2.0.4.RELEASE`  
**Gradle:** `gradle-4.8.1`. Project has gradle wrapper inside.    
**Docker:** `src/main/docker/Dockerfile`. 
Dockerfile has just basic configurations. 
All environment variables, configurations, healtcheck and etc was moved to docker-compose file that lay in the root of the project.  
**Healtcheck:**  `http://localhost:8080/actuator/health`  (endpoint from Spring Boot actuator)  
**Logging:** Log levels configured in `application.yaml` file  
**Swagger:** available at http://localhost:8080/swagger-ui.html  
**Testing:** project has unit tests in src/test/java folder. 
These are unit tests that test each service method internal logic by configuration and verification of dependent mocks.
**Security:** project use JWT tokens to authorize access for secured api endpoints. 
JWT token can be obtained at `Login` endpoint from `AuthController`. 
This endpoint returns `Authorization` header with JWT token, each issued token has expiration time limited to 10 days.
In current implementation project has just two secured endpoints in `GuestsController`. 
The security of requests are achieved through verification in controllers code that checks existence of current user id in `SecurityContextHolder`. `SecurityContextHolder` implements something like session holder(similar implementation like in spring security)  
Authorization token checking and validation implemented in `AuthInterceptor`, also this interceptor fills `SecurityContextHolder` with current request authorization data.
  
**Admin user credentials:**  
`email: admin@gmail.com`  
`password: admin`

**Bash script for local launch:** start.sh   
Work of this script is built around `docker-compose` util.
While this script is working it does the following actions:
1. run postgres database
2. build backend application with gradle(gradlew setup in project) 
!!! Need JAVA 8 in local PATH, it doesn't work with java 9 or 10 because gradle plugin 
that generates classes from database schema have problems with newest java versions.
3. build docker image with application and hold it in local registry 
4. run application with docker-compose
