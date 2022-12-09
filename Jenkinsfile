pipeline {
  agent any
       
  parameters {
    string(name: 'STATEMENT', defaultValue: 'hello; ls /', description: 'What should I say?')
  }

  stages {
    stage('Test') {
      steps {
        sh 'echo "oiii"'
      }
    }

  }
}