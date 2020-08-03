#First from "db/postgresql/src/main/" path, run the following commands: "docker-compose build" and "docker-compose up" (Docker must be installed). 

#Also run maven import and maven install

Available endpoints (Urls are stored in RestUtl class)

http://localhost:8080/callRecords/import 
- Http method - POST
- Parameters -  (RequestParam) File or file Url as a string
- Results - Reads recods from file and saves the to database. Returns saved record list, or if any exceptions occur, returs the exceptions.

http://localhost:8080/callRecords
- Http method - POST
- Parameters -  (Body) CallRecordFilter filter
- Results - Finds records from the database according to the specified filter. Results are paginated

http://localhost:8080/callRecords
- Http method - PUT
- Parameters -  (Body) CallRecordDetails  record list
- Results - Saves given records to database. Returns the results. If any exceptions are thrown, returns the exception.

http://localhost:8080/callRecord/{uuid}
- Http method - GET
- Parameters -  (Path variable) uuid of the desired record
- Results - Returns the desired call record by uuid


http://localhost:8080/callRecord/{uuid}
- Http method - DELETE
- Parameters -  (Path variable) uuid of the desired record
- Results - Deletes the desired call record by uuid

http://localhost:8080/callRecords/average/cost
- Http method - POST
- Parameters -  (Body) AverageCallRecordAttributeFilter filter
- Results - Finds records from the database according to the specified filter and calculates the average call cost for the found records. Returns the average call cost
