pipeline {
    agent any

    String organization = env.ORGANIZATION
    String repository = env.REPOSITORY
    String branch = env.BRANCH

    stages {
        stage('clone') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-mkacunha', passwordVariable: 'password', usernameVariable: 'username')]) {
                    sh 'git clone --branch $branch --single-branch https://$username:$password@github.com/$organization/$repository.git $repository'
                    sh "ls"
                }
            }
        }
    }
}
