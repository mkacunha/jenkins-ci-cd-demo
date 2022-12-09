def branch = "master"

pipeline {
    agent any

    stages {
        stage('clone') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-mkacunha', passwordVariable: 'password', usernameVariable: 'username')]) {
                    sh 'rm -rf gradle-release-tag-demo'
                    sh 'git clone --branch $BRANCH --single-branch https://$username:$password@github.com/mkacunha/gradle-release-tag-demo.git gradle-release-tag-demo'
                    sh "ls"
                }
            }
        }
    }
}
