

# Thinkific Technical Challenge 

## Incrementing Integers As A Service

### General Notes

This project is written in Java 8, using Spring Boot as the framework for the web service. Maven is used within the project to download dependencies, and Spring Boot uses a self contained server to run.

### Assumptions

- The first ID to return is 1.
	
	ex. If the user gets the current integer, they will get a value of 1.
	
	ex. If the user gets the next integer, they will get a value of 2.
- The IDs incremented are per user.
- The application uses an "in-memory" database. If ran locally, and then terminated, the database will set up as new on the next run.

### Running the Application

#### Running the application locally

1. To run the application locally, Java and Maven need to be installed 
2. Navigate to the root folder where the jar is located
2. In Terminal, run: **java -DJWT_SECRET=insert_secret_here -jar thinkific-0.0.1-SNAPSHOT.jar**
3. The project will run on localhost:8080

#### Test the application running on AWS

1. Access the API at **http://increment-integer-service.ca-central-1.elasticbeanstalk.com**

*NOTE:* The api is using HTTP (Not HTTPS) due to AWS Elastic Beanstalk needing a custom domain for issuing a certificate. The default URL cannot have a certificate assigned for access from a client. Please do not use your actual super secret passwords when making a user.


### API Documentation

*POST /v1/users*

Create a new user and retrieve an API key

JSON Body Format (with example values):

{
	"email": "test@abc.com",
	"password": "strawberry"
}


*GET /v1/current*

- Gets the current integer


*GET /v1/next*

- Gets the next integer in the sequence

*GET /v1/history*

- Gets all the values that were used as reset points


*PUT /v1/current*

- Resets the current integer the specified value passed in the Body

JSON Body Format (with example value):

{
	"value": 200
}

