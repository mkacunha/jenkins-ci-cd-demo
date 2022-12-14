def applicationScripts
def newApplicationVersion
def newDockerImgage

pipeline {
    agent any

    options {
        lock resource: "${env.AGENT}"
    }

    stages {
        stage("clone") {            
            steps {
                script {
                    def label = "$AGENT"
                    lock(label: label, variable: "resource_name") {
                        echo "Locked resource name is ${env.resource_name}"
                    }
                }

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
                dir(env.PWD) {
                    script {                            
                        def lastTagCommitId = sh(script: 'git rev-list --tags --max-count=1', returnStdout: true)
                        def lastBranchCommitId = sh(script:'git rev-parse HEAD', returnStdout: true)
                        def lastTagValue = sh(script:"git describe --tags $lastTagCommitId", returnStdout: true).toString().trim()
                        newApplicationVersion = lastTagValue

                        if (lastTagCommitId != lastBranchCommitId) {
                            def lastTagValueSplited = lastTagValue.split("\\.")
                            def minorVersionIndex = lastTagValueSplited.size() - 1
                            lastTagValueSplited[minorVersionIndex] = lastTagValueSplited[minorVersionIndex].toInteger() + 1
                            def newTagVersion = lastTagValueSplited.join(".")
                            newApplicationVersion = newTagVersion

                            sh("git tag -a $newTagVersion -m 'Jenkins'")
                            echo "tag $newTagVersion created"
                        } else {
                            echo "there were no changes, tag to be delivered: $lastTagValue"
                        }                        
                    }
                }

                withCredentials([gitUsernamePassword(credentialsId: 'github-mkacunha', gitToolName: 'git-tool')]) {
                    dir(env.PWD) {
                        sh "git push origin --tags"
                    }
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
