pipeline {
    agent any

    stages {
        stage("Clean"){
            steps{
                sh './gradlew clean'
            }
        }
        stage('Test') {
            parallel{
                stage("test: chrome"){
                    sh './gradlew test'
                    //por defecto esta puesto el chrome
                }
                stage("test: firefox"){
                    sh './gradlew testFirefox'
                }
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                }
            }
        }
       
    }
}
