pipeline {
    agent any
    tools {
        maven 'Maven 3.6.2'
		jdk 'jdk8'
    }
    stages {
        stage ('Build') {
            steps {
                sh 'mvn clean package -Dmaven.test.skip=true'
            }
        }
	    
	stage ('Tests') {
            steps {
                sh 'mvn test' 
            }
        }
		
	stage('SonarQube analysis') {
		steps {
			withSonarQubeEnv('sonarqube') {
				sh 'mvn sonar:sonar -Dsonar.projectKey=first-project -Dsonar.host.url=http://192.168.0.117:9001 -Dsonar.login=0c94c378ddb718b1f49695505e0ba8ba5d4f4340'
			}
		}
	}
	    
	stage('SonarQube Quality Gate') {
	    steps {
		script {
		    sleep(15)
		    timeout(time: 1, unit: 'HOURS') {
			def qg = waitForQualityGate()
			if (qg.status != 'OK') {
			    error "Pipeline aborted due to quality gate failure: ${qg.status}"
			}
		    }
		}
	    }
	}
	    
	stage('Deploy QA') {
            steps {
                sh 'echo "Enviando para ambiente de QA"'
            }
        }
	    
	stage('Deploy approval') {
            steps {
		timeout(time: 24, unit: 'HOURS') {
                	input "Everything OK to deploy to Production?"
		}
            }
        }
	    
	stage('Deploy PROD') {
            steps {
                sh 'echo "Enviando para ambiente de PROD"'
            }
        }
	    
    }
    
    post {
	failure {
          slackSend channel: '#bbpm',
                    color: 'danger',
                    message: "${currentBuild.currentResult}: ${delivery.kubernetes.configuration.appName} #${env.VERSION}: ${env.BUILD_URL}"
        }
	success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true                     
        }
        always {
	    emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
		
            junit 'target/surefire-reports/*.xml'
        }
    }
}
