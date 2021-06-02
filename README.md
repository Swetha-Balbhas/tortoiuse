# service-layer-repo
Service layer has both security and business service APIS in it. The layer is implemented using Spring Boot Framework.

## Security Layer
   Application Security is implemented using Spring Security Framework. Authentication is based on JWT.

## Business Service APIS
  Business Service APIS are exposed as pure REST APIS. MySQL database is used. From service layer mysql database connected using myBatis framework.

# Build 

Use the below instructions to build the application.

Build prerequisites

Java version: 1.8.XX
Maven version : 3.5.XX

## Maven Build

Use Maven to build and test the application. 

Use the below command from project directory to build the jar file without invoking the testcases.

mvn clean install -Dmaven.test.skip=true

Use the below command from project directory to run the test cases of the application

mvn test

# Run the application 

Once the jar is successfully built run the below command in the project directory

java -jar target/services-0.0.1-SNAPSHOT.jar

Now test the application using the below command

curl localhost:8080/test/testapi

Or use SOAP API or PostMan to test the APIS.

Happy Coding :)

