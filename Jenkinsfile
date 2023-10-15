pipeline {
    agent {
        node {
            label "jenkins-agent"
        }
    }

    stages {
        stage('checkout code') {
            steps {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/HIMMIREDA/spring-reddit-clone-backend.git'
            }
        }

        stage("Compile") {
            sh "mvn clean compile"
        }

        stage("run unit tests") {
            sh "mvn test"
        }
    }
}
