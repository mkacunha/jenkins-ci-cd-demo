def applicationScripts
def newApplicationVersion

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
                    newApplicationVersion = applicationScripts.createTag()
                    echo 'tag $newApplicationVersion created'
                }
            }
        }

        stage('build docker image') {
            steps {
                echo 'foi'
            }
        }

        stage('deploy QA') {
            steps {
                echo 'foi'
            }
        }

        stage('QA acceptance test') {
            steps {
                echo 'foi'
            }
        }

        stage('deploy SANDBOX') {
            steps {
                echo 'foi'
            }
        }

        stage('SANDBOX acceptance test') {
            steps {
                echo 'foi'
            }
        }

        stage('deploy PROD') {
            steps {
                echo 'foi'
            }
        }

        stage('PROD acceptance test') {
            steps {
                echo 'foi'
            }
        }

        stage('complete pull request') {
            steps {
                echo 'foi'
            }
        }
    }
}
