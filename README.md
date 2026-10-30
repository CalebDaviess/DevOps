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

## Jenkins Pipeline

The root `Jenkinsfile` uses the Maven 3.9.9 and Java 21 Docker image to check out
the repository and run `mvn --batch-mode --no-transfer-progress clean verify`.
This resolves dependencies, compiles the project, and runs any tests in
`src/test/java`; Maven reports success when no tests have been added yet.

Configure the Jenkins job as **Pipeline script from SCM** with this repository
and the script path set to `Jenkinsfile`. The Jenkins controller or agent must
have the Docker Pipeline plugin installed and access to a Docker daemon.

### Run the local Jenkins controller with Docker Pipeline support

The local controller setup uses `compose.jenkins.yaml` and `Dockerfile.jenkins`.
The image is pinned to the running Jenkins version, adds the Docker CLI, Docker
Pipeline plugin, and Pipeline: Stage View plugin, and preserves the existing
`jenkins_home` Docker volume.
It mounts the Docker socket, which grants Jenkins control over the local Docker
daemon; only use this configuration on a trusted development machine.

From the repository root, run:

```powershell
docker compose -f compose.jenkins.yaml build
docker stop jenkins
docker rm jenkins
docker compose -f compose.jenkins.yaml up -d
```

The Jenkins UI remains at `http://localhost:8080`. The existing Jenkins volume
is external and is not removed by these commands.

## Notes

The compiler release is pinned to Java 21 in `pom.xml`, so using a matching JDK is required for successful builds.
