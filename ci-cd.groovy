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
                        def lastTagValue = sh(script:"git describe --tags $lastTagCommitId", returnStdout: true).toString().trim()
                        
                        if (lastTagCommitId != lastBranchCommitId) {
                            def lastTagValueSplited = lastTagValue.split("\\.")
                            def minorVersionIndex = lastTagValueSplited.size() - 1
                            lastTagValueSplited[minorVersionIndex] = lastTagValueSplited[minorVersionIndex].toInteger() + 1
                            def newTagVersion = lastTagValueSplited.join(".")

                            sh 'git tag -a $newTagVersion -m "new version"'
                            sh 'git tag'
                            echo "tag $newTagVersion created"
                        } else {
                            echo "there were no changes, tag to be delivered: $lastTagValue"
                        }                        
                    }
                }
            }
        }
    }
}
