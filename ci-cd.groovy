pipeline {
    agent any

    stages {
        stage('first') {
            steps {
                echo 'Hello world pull request'
                echo "${env.ORGANIZATION}"
                echo "${env.REPOSITORY}"
            }
        }
    }
}
