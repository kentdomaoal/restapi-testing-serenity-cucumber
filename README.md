# restapi-testing-serenity-cucumber
Sample Rest API Test automation using [Serenity BDD (Rest Assured) + Cucumber](https://serenity-bdd.github.io/docs/tutorials/rest).

Data-driven using Excel, CSV, JSON file.

[Github actions](https://github.com/kentdomaoal/restapi-testing-serenity-cucumber/actions) were setup for its CI/CD Pipeline.

---
## How to run in local machine

## 💻 *Pre-requisites*
- [![Git](https://img.shields.io/badge/-Git-F05032?style=flat&logo=git&logoColor=FFFFFF)](https://git-scm.com/downloads)
- [![Java 8](https://img.shields.io/badge/-Java%208-red?style=flat&logo=java&logoColor=FFFFFF)](https://www.oracle.com/ph/java/technologies/downloads) or higher
- [![Maven](https://img.shields.io/badge/-Maven-C71A36?style=flat&logo=apache-maven&logoColor=FFFFFF)](https://maven.apache.org/download.cgi)

### 1. Clone the project
```
git clone https://github.com/kentdomaoal/restapi-testing-serenity-cucumber.git
```
``` 
cd restapi-testing-serenity-cucumber
```

### 2. Run the test
``` 
mvn clean verify
``` 

### 3. View html report
After test execution, SERENITY REPORTS can be found on the following:
- Full Report:

      /target/site/serenity/index.html

- Single Page HTML Summary:

      /target/site/serenity/serenity-summary.html


- Full Report As React Based Single Page Application:

      /target/site/serenity/navigator/index.html



