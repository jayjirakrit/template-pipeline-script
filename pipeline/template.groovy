pipeline {
    agent any
    environment{
    }
    stages {
        stage('Prepare parameters') {
            steps {
                script {
                    echo "Start prepare parameters .."
                }
            }
        }
    }
    stage('Build Artifact') {
        steps {
            script {
                echo "Start build artifact .."

            }
        }
    }
    stage('Build Docker Image') {
        steps {
            script {
                echo "Start build docker image .."

            }
        }
    }
}