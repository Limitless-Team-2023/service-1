pipeline {
    agent any
    environment {
        DOCKER_USER = credentials("docker-user")
        DOCKER_PASSWORD = credentials("docker-pass")
        GITHUB_TOKEN = credentials("github-token")
    }

    stages {
        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Tag image') {
              steps {
                script {
                   sh([script: 'git fetch --tag', returnStdout: true]).trim()
                   env.MAJOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 1', returnStdout: true]).trim()
                   env.MINOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2', returnStdout: true]).trim()
                   env.PATCH_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3', returnStdout: true]).trim()
                   env.IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
                }
              sh "docker login docker.io -u ${DOCKER_USER} -p ${DOCKER_PASSWORD}"
              sh "docker build -t ${DOCKER_USER}/hello-img:${env.IMAGE_TAG} ."
              sh "docker push ${DOCKER_USER}/hello-img:${env.IMAGE_TAG}"
              sh "git tag ${env.IMAGE_TAG}"
              sh "git push https://${GITHUB_TOKEN}@github.com/Limitless-Team-2023/service-1.git ${env.IMAGE_TAG}"
            }
        }
        stage('Docker compose up') {
            steps {
                sh "IMAGE_TAG=${env.IMAGE_TAG} docker-compose up -d hello"
            }
        }
        stage('Run integration tests') {
            steps {
                sh "./gradlew testE2E"
            }
        }
    }
}
