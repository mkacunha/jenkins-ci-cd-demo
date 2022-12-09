pipeline {
    agent any

    def organization = env.ORGANIZATION
    def repository = env.REPOSITORY
    def branch = env.BRANCH

    parameters {
        string(name: 'STATEMENT', defaultValue: 'hello; ls /', description: 'What should I say?')
    }

    // stages {
    //     stage('clone') {
    //         steps {
    //             withCredentials([usernamePassword(credentialsId: 'github-mkacunha', passwordVariable: 'password', usernameVariable: 'username')]) {
    //                 sh 'git clone --branch $branch --single-branch https://$username:$password@github.com/$organization/$repository.git $repository'
    //                 sh "ls"
    //             }
    //         }
    //     }
    // }

    stages {
        stage('clone') {
           steps {
             echo "${STATEMENT}"
           }
        }
    }
}
