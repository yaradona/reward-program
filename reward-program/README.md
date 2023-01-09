# Application to Calculate Reward Points Based on CustomerID

Spring Boot Restful Web Service for a Reward Points Calculate

1.	Create a service to get monthly reward points for the last three months and total reward points for the given customerId.
2.	Tech Stack: JDK 11, Spring boot 2.7.7, Spring Web, Spring Data JPA, H2, Lombok, Junit5
3.	Create table in DB: create table customertranscation (
       transcation_id bigint not null,
        customer_id varchar(255),
        reward_points integer not null,
        transacation_amount double not null,
        transacation_date timestamp,
        primary key (transcation_id)
    )
4. Insert couple of values to the customertranscation table
5. To start the application, 
	
	run mvn spring-boot:run
	
6.Verify the application console log started on port 8080

To Test Application:

	Open a Postman, import this curl 
	--location --request GET 'http://localhost:8080/api/rewardpoints/customerId'.
	Pass customerId in the above curl 
	
Sample Test results:

{
    "customerId": "11",
    "monthyRewards": {
        "March": 250,
        "February": 110,
        "January": 130
    },
    "totalRewardPoints": 490
}