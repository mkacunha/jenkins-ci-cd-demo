pipeline {
  agent any

  stages {

    stage('first') {
        steps {
            echo 'Hello world'
            echo "${env.TYPE}"
        }
    }

  }  
}