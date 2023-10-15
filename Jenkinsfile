pipeline {
    agent {
        node {
            label "jenkins-agent"
        }
    }

    tools{
        maven "maven3.9.5"
        jdk "jdk17"
        docker "docker"
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

        stage("build docker image"){
            steps {
                sh "mvn compile jib:dockerBuild"
                withDockerRegistry(credentialsId: 'a95daabf-e024-41bb-b4f5-009d343e38df', toolName: 'docker') {
                    sh "docker push redahimmi/spring-reddit-clone"
                }
            }
        }

    }
}
