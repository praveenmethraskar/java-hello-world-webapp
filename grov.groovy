pipeline{
    agent any
    
    stages{
        stage('clone'){
            steps{
                git 'https://github.com/praveenmethraskar/java-hello-world-webapp.git'
            }
        }
        stage('build maven'){
            steps{
                sh ''' mvn clean install '''
            }
        }
  
        
        stage('deploy'){
            steps{
                deploy adapters: [tomcat9(credentialsId: 'admin', path: '', url: 'http://3.110.119.107:8080/')], contextPath: 'myapp1', war: '**/*.war'
            }
        }
    }
}
