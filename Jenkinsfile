pipeline {
  agent any

  parameters {
    string(name: 'ORGANIZATION', defaultValue: 'mkacunha')
    string(name: 'REPOSITORY', defaultValue: 'gradle-release-tag-demo')
    string(name: 'BRANCH', defaultValue: 'master')
  }

  stages {
    stage('clone') {
      steps {
        // withCredentials([usernamePassword(credentialsId: 'github-mkacunha', passwordVariable: 'USERNAME', usernameVariable: 'USERNAME')]) {
        //   sh 'git clone --branch $BRANCH --single-branch https://$USERNAME:$USERNAME@github.com/$ORGANIZATION/$REPOSITORY.git $REPOSITORY'
        //   sh "ls"
        // }
      }
    }
  }
}