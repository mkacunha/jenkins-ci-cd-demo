def branch = "master"

pipeline {
    agent any

    stages {
        stage('clone') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-mkacunha',  usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh 'rm -rf gradle-release-tag-demo'
                    sh 'git clone --branch $BRANCH --single-branch https://$USERNAME:$PASSWORD@github.com/$ORGANIZATION/$REPOSITORY.git $REPOSITORY'
                    sh "ls"
                }
            }
        }
    }
}
