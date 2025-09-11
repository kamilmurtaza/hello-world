pipeline {
    agent any
    tools {
            jdk 'JDK8'
     }
    environment {
        APP_NAME = "hello-world"
        DOCKER_IMAGE = "my-dev-ops/hello-world:${BUILD_NUMBER}"
        KUBE_CONFIG = "'C:\\Users\\TK-LPT-284\\.kube\\config"
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
                bat "docker build -t %DOCKER_IMAGE% ."
                bat "docker push %DOCKER_IMAGE% "
            }
        }
        stage('Deploy Container') {
            steps {
                bat '''
                    docker rm -f %APP_NAME% || true
                    docker run -d --name %APP_NAME% -p 8080:8080 %DOCKER_IMAGE%
                '''
            }
        }
        stage('Update Deployment with Image') {
                    steps {
                        powershell """
                            (Get-Content deployment.yml) `
                                -replace 'my-dockerhub-user/hello-world:1.0', '${DOCKER_IMAGE}' `
                                | Set-Content deployment.yml
                        """
                    }
         }
         stage('Deploy to Kubernetes') {
                     steps {
                         // Apply manifests in correct order
                         bat """
                             kubectl --kubeconfig=${KUBE_CONFIG} apply -f namespace.yml
                             kubectl --kubeconfig=${KUBE_CONFIG} apply -f configmap.yml
                             kubectl --kubeconfig=${KUBE_CONFIG} apply -f deployment.yml
                             kubectl --kubeconfig=${KUBE_CONFIG} apply -f service.yml
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
