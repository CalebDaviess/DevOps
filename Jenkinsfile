pipeline {
    agent {
        docker {
            image 'maven:3.9.9-eclipse-temurin-21'
        }
    }

    options {
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code from SCM'
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                echo 'Resolving Maven dependencies, compiling, and running tests'
                sh 'mvn --batch-mode --no-transfer-progress clean verify'
            }
        }
    }

    post {
        success {
            echo 'Build and test pipeline completed successfully'
        }
        failure {
            echo 'Build and test pipeline failed; review the stage output above'
        }
    }
}
