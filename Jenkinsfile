pipeline {
    agent {
        node {
            label "jenkins-agent"
        }
    }

    tools{
        maven "maven3.9.5"
        jdk "jdk17"
    }

    stages {
        stage('checkout code') {
            steps {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/HIMMIREDA/spring-reddit-clone-backend.git'
            }
        }

        stage("Compile") {
            steps {
                sh "mvn clean compile"
            }
        }

        stage("run unit tests") {
        steps {
            sh "mvn test"
        }
        }
    }
}
