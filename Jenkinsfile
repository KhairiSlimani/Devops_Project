pipeline {
    agent any
    tools{
        nodejs 'node'
    }

    stages {
        stage('Git') {
            steps {
                checkout scm
            }
        }
     
        stage('Unit Tests and Send Mail') {
            steps {
                dir('DevOps_Project') {
                    script {
                        try {
                            sh 'mvn clean install'
                        } catch (Exception e) {
                            currentBuild.result = 'FAILURE'
                            error("Build failed: ${e.message}")
                        }
                    }
                }
            }
            post {
                success {
                    script {
                        def subject = "Test Sending Email"
                        def body = "Success"
                        def to = 'khairi.slimani@esprit.tn'
                        def from = 'ks7.apps@gmail.com'
                        mail(
                            subject: subject,
                            body: body,
                            to: to,
                        )
                    }
                }
                failure {
                    script {
                        def subject = "Build Failure - ${currentBuild.fullDisplayName}"
                        def body = "The build has failed "
                        def to = 'khairi.slimani@esprit.tn'
                        def from = 'ks7.apps@gmail.com'
                        mail(
                            subject: subject,
                            body: body,
                            to: to,
                        )
                    }
                }
                
            }
        }

        stage('Sonarqube') {
            steps {
                dir('DevOps_Project') {
                    sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=khairi'
                }
            }
        }

        stage('Nexus') {
            steps {
                dir('DevOps_Project') {
                    sh 'mvn clean deploy -DskipTests'
                }
            }
        }
        
        stage('Build Frontend') {
            steps {
                dir('DevOps_Project_Front') {
                    script {
                        
                        sh 'npm install -g npm@latest'
                        sh 'npm install --force'
                        sh 'npm run build'      
                    }
                }
            }
        }

        stage('Login to Docker Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhubpass', variable: 'password')]) {
                        sh 'docker login -u khairislimani -p ${password}'
                    }
                }    
            }
        }

        stage('Build and Push Docker Image of Backend') {
            steps {
                dir('DevOps_Project') {
                    script {
                        sh 'docker build -t khairislimani/devopsbackend .'
                        sh 'docker push khairislimani/devopsbackend'
                    }
                }
            }
        }

        stage('Build and Push Docker Image of Frontend') {
            steps {
                dir('DevOps_Project_Front') {
                    script {
                        sh 'docker build -t khairislimani/devopsfrontend .'
                        sh 'docker push khairislimani/devopsfrontend'
                    }
                }
            }
        }
        
       stage('Deploy App') {
            steps {
                
                script {
                    sh 'docker compose -f docker-compose.yml up -d' 
                    sh 'docker compose -f docker-compose.yml start'                       
                }
                
            }
        }
    }
        
}




