def applicationScripts
def newApplicationVersion
def newDockerImgage

pipeline {
    agent any

    stages {
        stage('clone') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-mkacunha',  usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh 'rm -rf gradle-release-tag-demo'
                    sh 'git clone --branch $BRANCH --single-branch https://$USERNAME:$PASSWORD@github.com/$ORGANIZATION/$REPOSITORY.git $REPOSITORY'
                }

                script {
                    applicationScripts = load "./$REPOSITORY/Jenkinsfile"
                    env.PWD = "./$REPOSITORY"
                }
            }
        }

        stage('check pull request') {
            steps {
                echo "foi"
            }
        }

        stage('tag') {
            steps {
                script {
                    def lastTagCommitId = sh 'git rev-list --tags --max-count=1'
                    def lastBranchCommitId = sh 'git rev-parse HEAD'
                    
                    echo "tag id $lastTagCommitId"
                    echo "branch id $lastBranchCommitId"
                }
            }
        }
    }
}
