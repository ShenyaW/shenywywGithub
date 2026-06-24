pipeline {
    agent any
    tools{
        maven 'Maven3.6.3'
        jdk 'JDK21'
    }

    environment{
        MAVEN_OPT='-Xmx1024m'
        APP_NAME='demo'
        APP_VERSION='0.0.1-SNAPSHOT'
    }

    options{
        buildDiscarder(logRotator(numTokeepStr:'10'))
        timestamp()
        timeout(time:30,unit:'MINUTES')
    }
    stages {
        stage('checkout') {
            steps {
                echo 'checking out code....'
                git credentialsId: 'github-ssh',branch:'testBranch',url:'git@github.com:ShenyaW/shenywywGithub.git'
                echo 'checked'
            }
        }
        stage('Build') {
            steps {
                echo 'build success'
            }
        }
    }
}