pipeline {
    agent any

    stages {
        stage('clone') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-mkacunha', passwordVariable: 'password', usernameVariable: 'username')]) {
                    sh 'rm -r gradle-release-tag-demo'
                    sh 'git clone --branch ${env.BRANCH} --single-branch https://$username:$password@github.com/mkacunha/gradle-release-tag-demo.git gradle-release-tag-demo'
                    sh "ls"
                }
            }
        }
    }
}
