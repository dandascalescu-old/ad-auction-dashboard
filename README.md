[![pipeline status](https://git.soton.ac.uk/fb3g18/seg-2020/badges/master/pipeline.svg)](https://git.soton.ac.uk/fb3g18/seg-2020/-/commits/master)

# Ad Auction Dashboard
SEG 2020 - Group 23

A dashboard for viewing advertisement campaign data. 

## Project Structure
- `model` - Classes used to define app data
- `service` - Providers for external affects, e.g. database service
- `view` - JavaFX views
- `viewmodel` - Bind data to the views and provide logic for views
## Requirementsc
- Java 13+
- Maven (If installing using source code)

## Installation
### Using [Maven](https://maven.apache.org/):
Go to project source and run:
```
mvn clean install
```
## Usage
### Maven
On installation, Maven will create a file called `seg-2020-1.0-jar-with-dependencies.jar`. Run this to launch the dashboard.

CSVs containing campaign data can be imported through the within the Database view of the application, and data will be displayed on the main Dashboard.

Note: `data` and `io` packages possibly to consolidated into service/repository structure