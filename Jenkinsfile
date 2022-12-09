pipeline {
  agent any
  stages {
    stage('clone') {
      steps {
        echo "$ORGANIZATION"
        echo "$REPOSITORY"
        echo "$BRANCH"
      }
    }

  }
  parameters {
    string(name: 'ORGANIZATION', defaultValue: 'mkacunha')
    string(name: 'REPOSITORY', defaultValue: 'gradle-release-tag-demo')
    string(name: 'BRANCH', defaultValue: 'master')
  }
}