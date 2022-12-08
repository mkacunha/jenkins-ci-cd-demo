pipeline {
  agent any

  stages {

    stage('first') {
        steps {
            echo 'Hello world'
            echo "${env.TYPE}"
            echo "${status.STARTED}"
        }
    }
  }  

    class status {
        final String STARTED = "STARTED"
        final String SUCCESS = "SUCCESS"
        final String FAILURE = "FAILURE"
        final String ABORTED = "ABORTED"
    }
}