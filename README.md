BUILDING JAVA PROJECT:

I use gradle in order to build the project. SpringBoot is used in order to provide API functionality and will be needed to run this project. Using the build.gradle file available and choosing auto-import settings in IntelliJ should provide an environment with all dependencies available. Running the application will choose port 8443 by default.  


DATABASE:

I used postgres for my database. I assume that the username and password will be the default postgres/postgres values. I also assume that postgres is running on port 5432 and that the database it is trying to connect to is named 'test'. "jdbc:postgresql://localhost:5432/test". These values can be changed in 'SQLConnector' and 'TicketController', respectively. The tables should be created in the order they appear. The purchases table is not actually used, but provides a concept for how to track tickets a user owns. In order to create the tables I used the below commands:


TABLE CREATION:

CREATE TABLE event(
 event_id serial PRIMARY KEY,
 event_name VARCHAR (350) NOT NULL,
 location VARCHAR (350) NOT NULL,
 date TIMESTAMP NOT NULL
);


CREATE TABLE users(
 user_id serial PRIMARY KEY,
 location VARCHAR (350) NOT NULL,
 referral VARCHAR (100) NOT NULL,
 name VARCHAR (150) NOT NULL
);


CREATE TABLE ticket(
 ticket_id serial PRIMARY KEY,
 event_id integer references event(event_id),
 section VARCHAR (50) NOT NULL,
 available integer NOT NULL,
 price integer NOT NULL,
 row VARCHAR (50) NOT NULL,
 seller_id integer references users(user_id) NOT NULL
);


CREATE TABLE purchases(
 ticket_id integer references ticket(ticket_id),
 user_id integer references users(user_id),
 quantity integer NOT NULL
);

'events_data.csv', 'user_data.csv', and 'sampleTickets.csv' are all available in the root directory of this repository and should be used to populate data.

COPY DATA INTO TABLES:

COPY event(event_id, event_name, location, date) FROM '/events_data.csv' DELIMITER ',' CSV HEADER;

COPY users(user_id, location, referral, name) FROM '/user_data.csv' DELIMITER ',' CSV HEADER;

COPY ticket(ticket_id, event_id, section, available, price, row, seller_id) FROM '/sampleTickets.csv' DELIMITER ',' CSV HEADER;

EXAMPLE POSTS TO INTERACT WITH ENDPOINTS:

curl -i -X POST -H 'Content-Type: application/json' -d '{"eventID": 107}' localhost:8443/vivid/availableTickets

curl -i -X POST -H 'Content-Type: application/json' -d '{"ticketID": 108}' localhost:8443/vivid/updateTicket

curl -i -X POST -H 'Content-Type: application/json' -d '{"eventID": 107}' localhost:8443/vivid/bestTicket

curl -i -X POST -H 'Content-Type: application/json' -d '{"eventID": 107, "ticketID": 1582, "available": 4, "section": "GA2", "row": "42", "price": 32, "sellerID": 4}' localhost:8443/vivid/postTicket
