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
        echo "$ORGANIZATION"
        echo "$REPOSITORY"
        echo "$BRANCH"
      }
    }
  }
}