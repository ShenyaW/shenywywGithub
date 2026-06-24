pipeline {
    agent any
    tools{
        maven 'Maven 3.9.5'
        jdk 'JDK17'
    }

    environment{
        MAVEN_OPT='-Xmx1024m'
        APP_NAME='demo'
        APP_VERSION='0.0.1-SNAPSHOT'
    }

    option{
        ubilldDiscarder(logRotator(numTokeepStr:'10'))
        timestamp()
        timeout(time:30,unit:'MINUTES')
    }
    stages {
        stage('checkout') {
            steps {
                echo 'checking out code....'
                git branch:'testBranch',url:'https://github.com/ShenyaW/shenywywGithub.git'
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