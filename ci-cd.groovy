pipeline {
    agent any

    stages {
        stage('clone') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-mkacunha', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                    sh "git clone --branch master --single-branch https://${USERNAME}:${PASSWORD}@github.com/${env.ORGANIZATION}/${env.REPOSITORY}.git ${env.REPOSITORY}"
                    sh "ls"
                }
            }
        }
    }
}
