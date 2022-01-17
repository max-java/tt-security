This service demonstrates work for SpringSecurity. 
It is a single service without sso server and possibility to 
communicate with other services. Need to prove basic Spring Security concepts.

Service to track office staff
Predefined data is in `data.sql`

To update status of the person
``curl -X PATCH "http://localhost:8082/api/card/5555" -H "accept: */*" -H "Content-Type: text/plain" -d "true"``