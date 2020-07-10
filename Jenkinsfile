pipeline {
    agent any
    tools {
        maven 'Maven 3.6.2'
		jdk 'jdk8'
    }
    stages {
        stage ('Build') {
            steps {
                sh 'mvn clean package -DskipTest' 
            }
        }
		
	stage('SonarQube analysis') {
		steps {
			withSonarQubeEnv('sonarqube') {
				sh 'mvn sonar:sonar -Dsonar.projectKey=desafio-allan-uol -Dsonar.host.url=http://192.168.0.117:9001 -Dsonar.login=6bb8679322478dd970483ed128f870ec68eb8dc4'
			}
		}
	}
	    
	stage('SonarQube Quality Gate') {
	    steps {
		script {
		    sleep(20)
		    timeout(time: 1, unit: 'HOURS') {
			def qg = waitForQualityGate()
			if (qg.status != 'OK') {
			    error "Pipeline aborted due to quality gate failure: ${qg.status}"
			}
		    }
		}
	    }
	}
	    
    }
}
