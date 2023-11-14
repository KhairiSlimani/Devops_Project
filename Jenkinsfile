pipeline {
    agent any
    tools{
        nodejs 'DevOpsfrontend'
    }

    stages {
        stage('GIT') {
            steps {
                checkout scm
            }
        }
     
        stage(' UNIT TESTES AND SEND MAIL') {
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
                        def subject = "TESTES"
                        def body = "SUCCESS"
                        def to = 'khairi.slimani@esprit.tn'

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

                        mail(
                            subject: subject,
                            body: body,
                            to: to,
                        )
                    }
                }
                
            }
        }
        stage('SONARQUBE') {
            steps {
                dir('DevOps_Project') {
                sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=khairi'
            }
            }
        }

        stage('NEXUS') {
            steps {
                dir('DevOps_Project') {
                sh 'mvn clean deploy -DskipTests'
            }
            }
        }
        
        stage('BBUILD FRONT') {
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

        stage('LOGIN DOCKER') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u khairislimani -p ${dockerhubpwd}'
                    }
                }    
            }
        }

        stage('CREATE DOCKER IMAGE BACK') {
            steps {
                dir('DevOps_Project') {
                    script {
                        sh 'docker build -t khairislimani/devopsbackendimage .'
                        sh 'docker push khairislimani/devopsbackendimage'
                    }
                }
            }
        }
        stage('CREATE DOCKER IMAGE FRONT') {
            steps {
                dir('DevOps_Project_Front') {
                    script {
                        sh 'docker build -t khairislimani/devopsfrontendimage .'
                        sh 'docker push khairislimani/devopsfrontendimage'
                        
                    }
                }
            }
        }
        
       stage('DEPLOY APP') {
            steps {
                
                script {
                    sh 'docker-compose -f docker-compose.yml up -d' 
                    sh 'docker-compose -f docker-compose.yml start'                       
                }
                
            }
        }
    }
        
}




