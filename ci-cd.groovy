def applicationScripts
def newApplicationVersion
def newDockerImgage

pipeline {
    agent any

    stages {
        stage('clone') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-mkacunha',  usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    sh 'git config --global credential.helper cache'
                    sh 'git config --global push.default simple'
                    sh 'rm -rf gradle-release-tag-demo'
                    sh 'git clone --branch $BRANCH --single-branch https://$USERNAME:$PASSWORD@github.com/$ORGANIZATION/$REPOSITORY.git $REPOSITORY'
                }

                script {
                    applicationScripts = load "./$REPOSITORY/Jenkinsfile"
                    env.PWD = "./$REPOSITORY"
                }

                dir(env.PWD) {
                    sh 'git tag'
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
                    echo "tag $newApplicationVersion created"
                    sh "git push origin $newApplicationVersion"
                }

                dir(env.PWD) {
                    sh 'git tag'
                }
            }
        }

        stage('build docker image') {
            steps {
                script {
                    newDockerImgage = applicationScripts.buildDockerImage(newApplicationVersion)
                    echo "docker image $newDockerImgage builded"
                }
                echo "docker push $newDockerImgage"
            }
        }

        stage('deploy QA') {
            steps {
                echo "call ecs-deploy or ecs-srvice-deploy with $newDockerImgage"
            }
        }

        stage('QA acceptance test') {
            steps {
                script {
                    def testsResult = applicationScripts.runAcceptanceTest("QA")

                    if (testsResult) {
                        echo "successfully executed QA acceptance test"
                    } else {
                        error "error running QA acceptance tests"
                    }
                    
                }
            }
        }

        stage('deploy SANDBOX') {
            steps {
                echo "call ecs-deploy or ecs-srvice-deploy with $newDockerImgage"
            }
        }

        stage('SANDBOX acceptance test') {
            steps {
                script {
                    def testsResult = applicationScripts.runAcceptanceTest("SANDBOX")

                    if (testsResult) {
                        echo "successfully executed SANDBOX acceptance test"
                    } else {
                        error "error running SANDBOX acceptance tests"
                    }                    
                }
            }
        }

        stage('deploy PROD') {
            steps {
                echo "call ecs-deploy or ecs-srvice-deploy with $newDockerImgage"
            }
        }

        stage('PROD acceptance test') {
            steps {
                script {
                    def testsResult = applicationScripts.runAcceptanceTest("PROD")

                    if (testsResult) {
                        echo "successfully executed PROD acceptance test"
                    } else {
                        error "error running PROD acceptance tests"
                    }                    
                }
            }
        }

        stage('complete pull request') {
            steps {
                echo 'foi'
            }
        }
    }
}
