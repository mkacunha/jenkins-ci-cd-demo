pipeline {
  agent any
       
  parameters {
    string(name: 'ORGANIZATION', defaultValue: 'mkacunha')
  }

  stages {
    stage('clone') {
      steps {
        echo "$ORGANIZATION"
        echo "$REPOSITORY"
        echo "$BRANCH"
      }
    }
  }
}