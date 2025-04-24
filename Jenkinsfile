pipeline {
    agent any

    tools {
        nodejs "NodeJS"
    }

    environment {
        DOCKER_IMAGE = 'react-ecommerce'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Setup Node.js') {
            steps {
                sh '''
                    node --version
                    npm --version
                    which node
                    which npm
                '''
            }
        }

        stage('Install Dependencies') {
            steps {
                sh '''
                    npm install
                    npm list
                '''
            }
        }

        stage('Run Tests') {
            steps {
                sh 'npm test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker --version
                    docker build -t ${DOCKER_IMAGE} .
                '''
            }
        }

        stage('Deploy to Production') {
            steps {
                sh '''
                    docker-compose down
                    docker-compose up -d frontend-prod
                '''
            }
        }
    }

    post {
        always {
            // Clean up workspace
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
