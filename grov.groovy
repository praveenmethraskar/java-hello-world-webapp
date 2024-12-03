pipeline{
    agent any
    environment{
        PATH = "/opt/maven3/bin:$PATH"
    }
    stages{
        stage('clone'){
            steps{
                git 'https://github.com/praveenmethraskar/java-hello-world-webapp.git'
            }
        }
        stage('build maven'){
            steps{
                sh "mvn clean deploy"
                sh "mv target/*.war target/myweb.war"
            }
        }
  
        
        stage('deploy'){
            steps{
                sshagent(['DEPLOYMENT']) {
                sh """
            scp -o StrictHostKeyChecking=no target/myweb.war  root@3.110.119.107:/home/ubuntu/tomcat/webapps/
                    
                    ssh ec2-user@3.110.119.107 /home/ec2-user/tomcat9/bin/shutdown.sh
                    
                    ssh ec2-user@3.110.119.107 /home/ec2-user/tomcat9/bin/startup.sh
                     """
                }
            }
        }
    }
}
