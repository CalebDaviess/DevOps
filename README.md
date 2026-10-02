# DevOps Project

This project uses the standard Maven directory layout and targets Java 21.

```text
src/
└── main/
    └── java/
        └── uk/ac/cardiffmet/devops/
            └── Main.java
```

Build and test with Maven:

```powershell
mvn clean test
```

Run the starter application after compiling:

```powershell
mvn compile
java -cp target/classes uk.ac.cardiffmet.devops.Main
```

Install Maven 3.9 or newer and use a JDK 21 installation. The compiler release is pinned to Java 21 in `pom.xml`.