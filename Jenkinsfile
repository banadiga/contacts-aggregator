#!/usr/bin/env groovy Jenkinsfile

def server = Artifactory.server "Artifactory"
def rtMaven = Artifactory.newMavenBuild()
rtMaven.tool = "default"
def buildInfo = Artifactory.newBuildInfo()

pipeline() {

  agent any

  triggers {
    pollSCM('H/10 * * * *')
  }

  options {
    disableConcurrentBuilds()
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }

  stages {

    stage('Configuration') {
      steps {
        script {
          buildInfo.env.collect()
        }
      }
    }

    stage('Build') {
      steps {
        script {
          rtMaven.deployer.deployArtifacts = false
          info = rtMaven.run pom: 'pom.xml', goals: 'clean verify'
          buildInfo.append(info)
        }
      }
    }

    stage('Publish SNAPSHOT') {
      steps {
        script {
          rtMaven.deployer.deployArtifacts = true
          rtMaven.deployer server: server, snapshotRepo: 'ibanadiga'
          info = rtMaven.run pom: 'pom.xml', goals: 'install'
          buildInfo.append(info)
        }
      }
    }
  }

  post {
    always {
      script {
        server.publishBuildInfo buildInfo
      }
    }

    success {
      script {
        dir("${env.WORKSPACE}") {
          archiveArtifacts '*/target/*.jar'
        }
        echo ("BUILD SUCCESS: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\nCheck console output at: ${env.BUILD_URL}")
      }
    }

    failure {
      script {
        echo ("BUILD FAILURE: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\nCheck console output at: ${env.BUILD_URL}")
      }
    }
  }
}
