pipeline {
    agent none
    stages {
        stage('Checkout') {
            agent { label 'slave-node-1' }
            steps {
                script {
                    // Ensure Jenkins checks out a branch, not a detached HEAD
                    checkout([$class: 'GitSCM', 
                        branches: [[name: '*/master']], // Change to '*/main' if needed
                        userRemoteConfigs: [[
                            url: 'https://github.com/praveenmethraskar/java-hello-world-webapp.git',
                            credentialsId: 'git' // Ensure valid credentials are used
                        ]]
                    ])

                    // Switch to the correct branch to avoid detached HEAD state
                    sh "git checkout master" // Change to 'main' if your branch is 'main'

                    // Debugging: Print branch details
                    sh "git branch"
                    sh "git status"

                    // Get the current branch name
                    def branchName = sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
                    echo "Checked out branch: ${branchName}"

                    // Validate if it's an allowed branch
                    if (branchName == 'master' || branchName == 'main' || branchName.startsWith('feature/')) {
                        echo "✅ Valid branch: ${branchName}. Proceeding with build..."
                    } else {
                        error("❌ Build stopped! Not a valid branch for triggering.")
                    }
                }
            }
        }

        stage('Build') {
            agent { label 'slave-node-1' }
            steps {
                echo "Building the project..."
                sh 'mvn clean install'
            }
        }
                stage('deploy') {
            agent { label 'slave-node-1' }
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat', path: '', url: 'http://13.203.104.115:8080/')], contextPath: null, war: '**/*.war'
            }
        }
    }
}
