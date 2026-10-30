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

The root `Jenkinsfile` uses the Docker Cloud agent labeled
`maven-docker-agent` to check out the repository and run
`mvn --batch-mode --no-transfer-progress clean verify`. This resolves
dependencies, compiles the project, and runs any tests in `src/test/java`;
JUnit 5 tests run during verification, and JaCoCo writes XML coverage to
`target/site/jacoco/jacoco.xml` for SonarQube to import.

Configure the Jenkins job as **Pipeline script from SCM** with this repository
and the script path set to `Jenkinsfile`.

### Run the local Jenkins controller with Docker support

The local controller setup uses `compose.jenkins.yaml` and `Dockerfile.jenkins`.
The image is pinned to the running Jenkins version and installs the Docker CLI,
Docker Cloud, Docker Pipeline, Pipeline: Stage View, SonarQube Scanner for
Jenkins, and Warnings Next Generation plugins. The Compose file also starts a local
SonarQube Community Build at `http://localhost:9000`; its data, extensions, and
logs persist in separate Docker volumes.

The Jenkins controller, SonarQube, and Docker Cloud Maven agents share a private
Docker network. Jenkins also mounts the Docker socket, which grants Jenkins
control over the local Docker daemon; only use this setup on a trusted
development machine.

From the repository root, run:

```powershell
docker compose -f compose.jenkins.yaml build
docker compose -f compose.jenkins.yaml up -d
```

The Jenkins UI is at `http://localhost:8080`, and SonarQube is at
`http://localhost:9000`. Sign in to SonarQube with its initial `admin` /
`admin` credentials and change the password when prompted.

In SonarQube, generate a user token under your account's **Security** settings.
In Jenkins, add it as a **Secret text** credential with ID `sonarqube-token`.
Then go to **Manage Jenkins > System > SonarQube servers**, add a server named
`Local SonarQube` with URL `http://sonarqube:9000`, select that credential, and
save. In SonarQube, add a webhook with URL
`http://jenkins:8080/sonarqube-webhook/` so Jenkins can receive quality gate
results. The pipeline uses the `Sonar way` quality gate and fails the build if
that gate fails. Do not put the SonarQube token in source control.

SonarQube blocks webhook destinations on private networks by default. For this
localhost-only setup, disable **Administration > Configuration > General
settings > Security > Enable local webhooks validation** before creating the
webhook. This disables destination validation for all webhooks on this
SonarQube instance, so keep its port bound to `127.0.0.1` and do not expose the
instance to untrusted users or networks.

The `DevOpsPractice` job reports Maven console warnings in Jenkins and runs
SonarQube analysis for project key `devops-project`.
This local setup is intended for learning and uses SonarQube's embedded
database; use a supported external database for a production deployment.

### Docker Cloud Maven agent

Build the local Java 21/Maven agent image from the repository root:

```powershell
docker build -f Dockerfile.maven-agent -t devops-maven-agent:3.9.9-java21 .
```

In **Manage Jenkins > Clouds**, add a Docker cloud with Docker Host URI
`unix:///var/run/docker.sock`. Test the connection, then add an enabled agent
template with label `maven-docker-agent`, image
`devops-maven-agent:3.9.9-java21`, remote filesystem root
`/home/jenkins/agent`, instance capacity `1`, pull strategy **Never pull**, and
container network `devops-ci`. Use **Attach Docker container** as the connection
method. The network setting lets the agent reach SonarQube at
`http://sonarqube:9000`.

## Notes

The compiler release is pinned to Java 21 in `pom.xml`, so using a matching JDK is required for successful builds.
