pipeline {
    agent any
    tools {
        jdk 'JDK8'
    }
    environment {
        APP_NAME = "hello-world"
        KUBE_CONFIG = "C:\\Users\\TK-LPT-284\\.kube\\config"
        REGISTER_CREDENTIALS = "6ce4e6fc-9817-4ee3-b945-0e98b53e165b"
        DOCKER_USER = "kamil.murtaza@tkxel.io"
        DOCKER_PASSWORD = "Docker123"
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
                  env.DOCKER_IMAGE = "kamilmurtaza/hello-world:latest"
                }
/*
                    dockerImage = docker.build(env.DOCKER_IMAGE)

                   docker.withRegistry('https://index.docker.io/v1/', env.REGISTER_CREDENTIALS) {
                   dockerImage.push()
                   } registry will use org docker registry which requires GKE
                    instead we will use the below to connect to local docker desktop
*/
                  bat """
                      docker build -t %DOCKER_IMAGE% .
                      docker login -u %DOCKER_USER% -p %DOCKER_PASSWORD%
                      docker push %DOCKER_IMAGE%
                  """
               }
        }

        stage('Update Deployment with Image') {
            steps {
                // Jenkins always search for the files in root, so if files are in a sub folder
                // we have to add subfoldername/ before file
                powershell """
                    (Get-Content config/deployment.yaml) `
                        -replace 'kamilmurtaza/hello-world:1.0', "kamilmurtaza/hello-world:${BUILD_NUMBER}" `
                        | Set-Content config/deployment.yaml
                """

                echo "Deployed Image:"
                echo "${DOCKER_IMAGE}"
            }
        }

        stage('Remove Unused docker image') {
            steps {
                bat "docker rmi %DOCKER_IMAGE%"
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                // Jenkins always search for the files in root, so if files are in a sub folder
                // we have to add subfoldername/ before file
                bat """
                    kubectl --kubeconfig="${KUBE_CONFIG}" apply -f config/namespace.yaml
                    kubectl --kubeconfig="${KUBE_CONFIG}" apply -f config/configmap.yaml
                    kubectl --kubeconfig="${KUBE_CONFIG}" apply -f config/deployment.yaml
                    kubectl --kubeconfig="${KUBE_CONFIG}" apply -f config/service.yaml
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