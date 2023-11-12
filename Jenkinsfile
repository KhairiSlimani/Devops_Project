pipeline {
    agent any
    
    stages {
        stage('GIT') {
            steps {
                checkout scm
            }
        }
     
        stage(' UNIT TESTES AND NOTIF') {
            steps {
                dir('DevOpsBackend') {
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
                        def to = 'monemehamila@gmail.com'

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
                        def to = 'monemehamila@gmail.com'

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
                dir('DevOpsBackend') {
                sh 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=khairi'
            }
            }
        }
        
       
    }
}




