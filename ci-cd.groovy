pipeline {
    agent any
    node() {
        stages {
            stage('foi') {
                echo 'foiii'
            }
        }
    }
}
