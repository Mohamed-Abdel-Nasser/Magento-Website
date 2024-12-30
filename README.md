# AutoFramework_3
## Table of Contents
1. [Introduction](#introduction)
2. [Project Overview](#project-overview)
    - [Project Name](#project-name)
    - [Project Description](#project-description)
    - [Release Notes](#release-notes)
3. [Project Design](#project-design)
    - [Page Object Model (POM)](#page-object-model-pom)
    - [Data-Driven Testing](#data-driven-testing)
4. [Project Structure](#project-structure)
5. [Tools and Technologies](#tools-and-technologies)
6. [Contributing](#contributing)
---
## Project Name:
####  Magento Website

## Project Description:
(Magento AutoFramework) is an highly modular, and scalable test automation framework specifically engineered for comprehensive end-to-end web application testing. Developed using Selenium WebDriver.
it offers a robust foundation for creating clean, maintainable, and reusable automated test cases.
Its modular design ensures flexibility, enabling seamless integration with various tools and technologies, while maintaining high standards of performance, reliability, and test coverage.

# Release Notes
## Version 1.0
This marks the first release of the AutoFramework-2 project.
I am committed to continually enhancing and evolving this framework to ensure it remains updated, robust, and professional.
With each iteration, the project will integrate modern features, improved functionality, and industry best practices to meet the dynamic needs of test automation.

Future updates will focus on:
- Adding advanced automation features.
- Enhancing performance and efficiency.
- Ensuring compatibility with emerging tools and technologies.
- Maintaining alignment with industry standards to deliver a consistently professional and dependable solution.

Thank you for your interest, contributions, and support as we advance this project together.
### Project Design:
- Page Object Model (POM)
- Data-Driven Testing
---

## Project Structure
```
MagentoAutoFramework/
├── Logs Files                           # Log files generated during test execution
├── allure-results/                      # Allure report results
├── src/main/java/engine/                # Core framework components (actions, constants, logger, browser setup)
├── src/main/java/pages/                 # Page Object Model (POM) classes
├── src/main/resources/properties/       # Configuration files
├── src/test/java/tests/                 # Test cases (e.g., TCs, BaseTest)
├── src/test/resources/data/             # Test data files (Excel, configurations)
├── pom.xml                              # Maven configuration
├── generate_allure_report.bat           # Script to generate Allure reports
└── README.md                            # Project documentation
```

---
## Tools and Technologies
- **Java**: Programming language
- **Selenium WebDriver**: For browser automation
- **IntelliJ IDEA**: A powerful IDE for efficient Java development with advanced features and intelligent code assistance.
- **Maven Dependencies**: For dependency management
- **Maven Assembly Plugin**: For creating distributable JARs or ZIPs with dependencies included.
    - **TestNG**: For executing tests and generating reports.
    - **Apache POI**:
        - **POI**: For handling Microsoft Office documents, especially Excel.
        - **POI-OOXML**: For working with newer Excel file formats (e.g., .xlsx).
        - **POI-OOXML-Schemas**: For managing schema definitions used in .xlsx files.
    - **Selenium**:
        - **Selenium-Java**: For automating web browsers during testing.
    - **JavaFaker**: For generating random test data like names, addresses, and numbers.
    - **Commons-IO**: For file and stream operations.
    - **Log4j**:
        - **Log4j-Core**: Core module for logging implementation and configuration.
        - **Log4j-API**: API for writing consistent logs.
    - **Allure**:
        - **Allure-TestNG**: For integrating TestNG with the Allure reporting framework.
        - **Allure-Java-Commons**: For core functionalities of the Allure reporting.
    - **AspectJ Weaver**: For enabling aspect-oriented programming features.
    - **Maven Surefire Plugin**: For executing unit tests and generating test reports in Maven projects.

---

## Contributing
Contributions are welcome!
We welcome contributions from QA Automation Testing Engineers! To contribute:

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your test enhancements or fixes (`git checkout -b test-enhancement-name`).
3. Write clear, maintainable test scripts and ensure thorough test coverage.
4. Run all tests and confirm they pass successfully.
5. Commit your changes with meaningful messages (`git commit -m 'Add new test or fix'`).
6. Push your branch to your fork (`git push origin test-enhancement-name`).
7. Open a pull request, including a description of your improvements or fixes.

We look forward to your contributions and appreciate your attention to quality!

---
