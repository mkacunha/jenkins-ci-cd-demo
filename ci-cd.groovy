pipeline {
    agent any

    stages {
        String organization = env.ORGANIZATION
        String repository = env.REPOSITORY
        String branch = env.BRANCH
        
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
