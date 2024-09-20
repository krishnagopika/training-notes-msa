pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_VERSION = '1.29.2'
        GIT_REPO = 'https://github.com/krishnagopika/training-notes-msa.git'
        DOCKER_HUB_CREDENTIALS = credentials('docker-hub-credentials')
        username = 'krishnagopika4'
        RDS_USERNAME = credentials('RDS_USERNAME')
        DB_PASSWORD = credentials('DB_PASSWORD')
        DB_URL = credentials('DB_URL')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: "${GIT_REPO}"
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    def services = sh(script: "ls -d */ | grep -v '^prometheus/'", returnStdout: true).trim().split('\n')
                    for (service in services) {
                        dir(service.trim()) {
                            def serviceName = service.trim().replaceAll('/', '')
                            sh "docker build -t ${serviceName}:${BUILD_NUMBER} ."
                        }
                    }
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_HUB_USERNAME', passwordVariable: 'DOCKER_HUB_PASSWORD')]) {
                        sh "echo $DOCKER_HUB_PASSWORD | docker login -u $DOCKER_HUB_USERNAME --password-stdin"
                        def services = sh(script: "find . -maxdepth 1 -type d -name '*-service' -not -name '*@tmp' -not -name 'prometheus' -not -name '.git' -not -name 'grafana' -not -name '.'", returnStdout: true).trim().split('\n')
                            
                        for (service in services) {
                            def serviceName = service.trim().replaceAll('./', '')
                            sh "docker tag ${serviceName}:${BUILD_NUMBER} ${username}/${serviceName}:${BUILD_NUMBER}"
                            sh "docker push ${username}/${serviceName}:${BUILD_NUMBER}"
                        }
                    }
                }
            }
        }

        stage('Update Docker Compose File') {
            steps {
                script {
                     withCredentials([string(credentialsId: 'RDS_USERNAME', variable: 'RDS_USERNAME'), 
                                     string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD'), string(credentialsId: 'DB_URL', variable: 'DB_URL')]) {
                       def envContent = """
                    RDS_USERNAME=${RDS_USERNAME}
                    DB_PASSWORD=${DB_PASSWORD}
                    DB_URL=${DB_URL}
                    """
                    writeFile file: '.env', text: envContent
                        sh 'ls -al'
                        sh 'cat .env'

                     
                    def composeFile = readFile 'docker-compose.yml'
                    def services = sh(script: "ls -d */ | grep -v '^prometheus/'", returnStdout: true).trim().split('\n')
                    for (service in services) {
                        def serviceName = service.trim().replaceAll('./', '')

                        composeFile = composeFile.replaceAll("(image: .*${serviceName}:).*", "\$1${BUILD_NUMBER}")
                    }
                    writeFile file: 'docker-compose.yml', text: composeFile

                     }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                sh "docker-compose down || true"
                sh "docker-compose up -d"
            }
        }
    }

    post {
        always {
            sh "docker-compose logs"
        }
    }
}
