pipeline {
    agent any

    environment {
        PULL_REQUEST_BUILD_TYPE = 'PULL REQUEST'
        DELIVERY_BUILD_TYPE = 'DELIVERY'
    }

    if ("$PULL_REQUEST_BUILD_TYPE" == "${env.TYPE}") {
        stages {

            stage('first') {
                steps {
                    echo 'Hello world pull request'
                    echo "${env.TYPE}"
                    echo "${status.STARTED}"
                }
            }
        }
    }

    if ("$DELIVERY_BUILD_TYPE" == "$env.TYPE") {
        stages {
            stage('first') {
                steps {
                    echo 'Hello world delivery'
                    echo "${env.TYPE}"
                    echo "${status.STARTED}"
                }
            }
        }
    }
}
