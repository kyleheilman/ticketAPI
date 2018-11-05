
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

COPY event(event_id, event_name, location, date) FROM '/events_data.csv' DELIMITER ',' CSV HEADER;

COPY users(user_id, location, referral, name) FROM '/user_data.csv' DELIMITER ',' CSV HEADER;

COPY ticket(ticket_id, event_id, section, available, price, row, seller_id) FROM '/sampleTickets.csv' DELIMITER ',' CSV HEADER;



curl -i -X POST -H 'Content-Type: application/json' -d '{"eventID": 107}' localhost:8443/vivid/availableTickets

curl -i -X POST -H 'Content-Type: application/json' -d '{"ticketID": 108}' localhost:8443/vivid/updateTicket

curl -i -X POST -H 'Content-Type: application/json' -d '{"eventID": 107}' localhost:8443/vivid/bestTicket


curl -i -X POST -H 'Content-Type: application/json' -d '{"eventID": 107, "ticketID": 1582, "available": 4, "section": "GA2", "row": "42", "price": 32, "sellerID": 4}' localhost:8443/vivid/postTicket