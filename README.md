# Selenium Custom Framework

Java Selenium framework matching the structure and SauceDemo flows in `Puppeteer_custom_framework`.

## Requirements

- Java 17 or later
- Maven 3.9 or later
- Google Chrome

Selenium Manager automatically downloads and manages the compatible ChromeDriver.

## Run tests

```powershell
mvn test
```

Tests run with a visible Chrome browser. The XML validation report is written to `reports/validation-results.xml`.

## Run a script

```powershell
mvn compile exec:java -Dexec.mainClass=OpenChrome
mvn compile exec:java -Dexec.mainClass=LoginCheck
mvn compile exec:java -Dexec.mainClass=FailedLoginCheck
```