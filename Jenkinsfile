pipeline {
    agent any
    environment {
        APP_NAME = "hello-world"
        DOCKER_IMAGE = "hello-world:latest"
    }
    stages {
        stage('Checkout') {
            steps {
              echo "Checking out Branch"
              git branch: 'main', url: 'https://github.com/kamilmurtaza/hello-world.git'
            }
        }
        stage('Build JAR') {
            steps {
                echo "Building JAR"
                bat 'mvn clean package -DskipTests'
            }
        }
        stage('Build Docker Image') {
            steps {
                bat "docker build -t ${DOCKER_IMAGE} ."
            }
        }
        stage('Deploy Container') {
            steps {
                bat '''
                    docker rm -f ${APP_NAME} || true
                    docker run -d --name ${APP_NAME} -p 8080:8080 ${DOCKER_IMAGE}
                '''
            }
        }
    }
    post {
        success {
            echo "✅ Build & Deploy Successful! Visit http://localhost:8080/hello"
        }
        failure {
            echo "❌ Build failed! Check logs."
        }
    }
}
