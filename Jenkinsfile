pipeline {
    agent {
        node {
            label 'jenkins-agent'
        }
    }

    tools {
        maven 'maven3.9.5'
        jdk 'jdk17'
        // 'org.jenkinsci.plugins.docker.commons.tools.DockerTool' 'docker'
    }

    environment {
        def dockerHome = tool 'docker'
        PATH = "${dockerHome}/bin:${env.PATH}"
    }

    stages {
        stage('checkout code') {
            steps {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/HIMMIREDA/spring-reddit-clone-backend.git'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('run unit tests') {
            steps {
                sh 'mvn test'
            }
        }


        stage('build docker image'){
            steps {
                script {
                    sh 'mvn compile jib:dockerBuild'
                    withDockerRegistry(credentialsId: '996a93d4-f694-43b6-911e-f9a4be5e9181', toolName: 'docker') {
                        sh 'docker push redahimmi/spring-reddit-clone'
                    }
                }
            }
        }

    }
}²