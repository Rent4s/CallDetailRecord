#Prieš naudojantis iš "db/postgresql/src/main/" paketo paleisti docker-compose build ir docker-compose up komandas. 
#Taip pat paleisti maven import ir maven install

Sistemos "endpointai" (adresai aprašyti RestUrl klasėje)

http://localhost:8080/callRecords/import 
- Http metodas - POST
- Parametrai -  (RequestParam) failas arba nuorodą į failą
- Rezultatai - Perskaito iš failo įrašus ir juos išsaugo į duomenų bazę. Grąžina išsaugotų rezultatų sąrašą. Jei yra klaidų, grąžina klaidas

http://localhost:8080/callRecords
- Http metodas - POST
- Parametrai -  (Body) CallRecordFilter filtras
- Rezultatai - Pagal filtre nustatytus parametrus išfiltruoja norimus įrašus ir juos grąžina. Rezultatai yra puslapiuojami

http://localhost:8080/callRecords
- Http metodas - PUT
- Parametrai -  (Body) CallRecordDetails įrašų sąrašas
- Rezultatai - Išsaugo pateiktus įrašus į duomenų bazę. Grąžina išsaugotų rezultatų sąrašą. Jei yra klaidų, grąžina klaidas

http://localhost:8080/callRecord/{uuid}
- Http metodas - GET
- Parametrai -  (Path variable) pageidaujamo įrašo uuid
- Rezultatai - Grąžina išsaugota callRecord pagal uuid


http://localhost:8080/callRecord/{uuid}
- Http metodas - DELETE
- Parametrai -  (Path variable) pageidaujamo įrašo uuid
- Rezultatai - Ištrina pageidaujama callRecord pagal uuid

http://localhost:8080/callRecords/average/cost
- Http metodas - POST
- Parametrai -  (Body) AverageCallRecordAttributeFilter filtras
- Rezultatai - Pagal filtre nustatytus parametrus įrašus ir suskaičiuoją atfiltruotų įrašų vidutinę kainą už minutę. Grąžina vidutinę kainą
