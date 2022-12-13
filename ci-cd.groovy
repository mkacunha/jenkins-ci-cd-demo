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
                dir(env.PWD) {
                    script {                            
                        def lastTagCommitId = sh(script: 'git rev-list --tags --max-count=1', returnStdout: true)
                        def lastBranchCommitId = sh(script:'git rev-parse HEAD', returnStdout: true)
                        def lastTagValue = sh(script:"git describe --tags $lastTagCommitId", returnStdout: true)
                        
                        if (lastTagCommitId != lastBranchCommitId) {
                            echo "${lastTagValue.trim()}"
                            def lastTagValueSplited = lastTagValue.split('.')
                            echo "$lastTagValueSplited"
                            def minorVersionIndex = lastTagValueSplited.size() - 1
                            echo "$minorVersionIndex"
                            lastTagValueSplited[minorVersionIndex] = lastTagValueSplited[minorVersionIndex] + 1
                            echo "$lastTagValueSplited"
                            def newTagVersion = lastTagValueSplited.toString() 
                            echo "$newTagVersion"
                            
                            echo "tag id $lastTagCommitId"
                            echo "branch id $lastBranchCommitId"
                            echo "precisa gerar nova TAG. Tag atual: $lastTagValue"
                            echo "Nova TAG gerada: $newTagVersion"
                        } else {
                            echo "não precisa gerar nova TAG: $lastTagValue"
                        }
                        
                    }
                }
            }
        }
    }
}
