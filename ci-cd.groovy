pipeline {
    agent any

    stages {
        stage('clone') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-mkacunha', passwordVariable: 'password', usernameVariable: 'username')]) {
                    sh 'git clone --branch $branch --single-branch https://$username:$password@github.com/$env.ORAGANIZATION/$env.REPOSITORY.git $env.REPOSITORY'
                    sh "ls"
                }
            }
        }
    }
}
