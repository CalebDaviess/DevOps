# DevOps Project

This repository is a simple Java-based starter project for learning and practicing DevOps workflows. It is intentionally lightweight so it can be used for hands-on exercises involving Git, Maven, CI/CD, automated testing, and deployment basics.

## Overview

The project uses the standard Maven directory layout and targets Java 21. It includes a minimal application entry point that prints a list of DevOps tasks, making it easy to demonstrate builds and local execution before expanding the project with more advanced tooling.

```text
src/
└── main/
    └── java/
        └── uk/ac/cardiffmet/devops/
            └── Main.java
```

## Prerequisites

Before running the project, ensure you have:

- Java 21 JDK installed
- Maven 3.9 or newer installed
- A Git client and a terminal or IDE

## Project Structure

- `src/main/java` — application source code
- `src/test/java` — test source code (if added during exercises)
- `pom.xml` — Maven project definition and Java version configuration
- `.gitignore` — excludes generated build files and local environment artifacts

## Build and Test

Run the project tests and compile the code with Maven:

```powershell
mvn clean test
```

## Run the Application

Compile the code and execute the entry point:

```powershell
mvn compile
java -cp target/classes uk.ac.cardiffmet.devops.Main
```

## Typical DevOps Workflow

1. Clone the repository
2. Create a feature branch
3. Make code or configuration changes
4. Run Maven checks locally
5. Commit changes with clear messages
6. Push the branch and create a pull request
7. Validate CI/CD checks before merging

## Notes

The compiler release is pinned to Java 21 in `pom.xml`, so using a matching JDK is required for successful builds.
