pipeline {
    agent any
    tools {
        jdk 'JDK8'
    }
    environment {
        APP_NAME = "hello-world"
        KUBE_CONFIG = "C:\\Users\\TK-LPT-284\\.kube\\config"
        REGISTER_CREDENTIALS = "6ce4e6fc-9817-4ee3-b945-0e98b53e165b"
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

        stage('Build & Push Docker Image') {
            steps {
                script {
                    env.DOCKER_IMAGE = "my-dev-ops/hello-world:${BUILD_NUMBER}"
                    dockerImage = docker.build(env.DOCKER_IMAGE)

                    docker.withRegistry('https://index.docker.io/v1/', env.REGISTER_CREDENTIALS) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Update Deployment with Image') {
            steps {
                powershell """
                    (Get-Content deployment.yml) `
                        -replace 'my-dockerhub-user/hello-world:1.0', '$env:DOCKER_IMAGE' `
                        | Set-Content deployment.yml
                """
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                bat """
                    kubectl --kubeconfig="${KUBE_CONFIG}" apply -f namespace.yml
                    kubectl --kubeconfig="${KUBE_CONFIG}" apply -f configmap.yml
                    kubectl --kubeconfig="${KUBE_CONFIG}" apply -f deployment.yml
                    kubectl --kubeconfig="${KUBE_CONFIG}" apply -f service.yml
                """
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