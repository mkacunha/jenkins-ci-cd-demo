pipeline {
    agent any

    organization = env.ORGANIZATION
    repository = env.REPOSITORY
    branch = env.BRANCH

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
