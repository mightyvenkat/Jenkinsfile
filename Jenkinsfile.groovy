pipeline {
    agent any
    environment {
        AWS_ACCESS_KEY_ID = credentials('access')  // Jenkins credentials ID
        AWS_SECRET_ACCESS_KEY = credentials('secret')  // Jenkins credentials ID
        AWS_DEFAULT_REGION = 'us-east-2'  // Set your AWS region
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out code..."
                    git branch: 'main', credentialsId: 'git-cred', url: 'https://github.com/mightyvenkat/vpc.tf.git'
                }
            }
        }
        
        stage('Terraform Init') {
            steps {
                script {
                    echo "Initializing Terraform..."
                    sh "terraform init"
                }
            }
        }
        
        stage('Terraform Validate') {
            steps {
                script {
                    echo "Validating Terraform configuration..."
                    sh "terraform validate"
                }
            }
        }
        
        stage('Terraform Plan') {
            steps {
                script {
                    echo "Running Terraform plan..."
                    sh "terraform plan"
                }
            }
        }

        stage('Terraform Apply') {
            steps {
                script {
                    echo "Applying Terraform configuration..."
                    sh "terraform apply -auto-approve"
                }
            }
        }
        
        // stage('Terraform destroy') {
        //     steps {
        //         script {
        //             echo "Applying Terraform configuration..."
        //             sh "terraform destroy -auto-approve"
        //         }
        //     }
        // }
    }
}
