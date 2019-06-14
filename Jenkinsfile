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

  environment {
    JENKINS_HOOKS = credentials("morning-at-lohika-jenkins-ci-hooks")
  }

  stages {

    stage('Configuration') {
      steps {
        script {
          buildInfo.env.filter.addExclude("*TOKEN*")
          buildInfo.env.filter.addExclude("*HOOK*")
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
      when {
        branch 'master'
        expression { params.release == false }
      }
      steps {
        script {
          rtMaven.deployer.deployArtifacts = true
          rtMaven.deployer server: server, snapshotRepo: 'morning-at-lohika-snapshots'
          info = rtMaven.run pom: 'pom.xml', goals: 'install'
          buildInfo.append(info)
        }
      }
    }
  }

  post {
    always {
      script {
        publishHTML(target: [
            allowMissing         : true,
            alwaysLinkToLastBuild: false,
            keepAll              : true,
            reportDir            : 'build/reports/tests/test',
            reportFiles          : 'index.html',
            reportName           : "Test Summary"
        ])
        junit testResults: '**/target/surefire-reports/TEST-*.xml', allowEmptyResults: true
        server.publishBuildInfo buildInfo
      }
    }

    success {
      script {
        dir("${env.WORKSPACE}") {
          archiveArtifacts '*/target/*.jar'
        }
      }
    }

    failure {
      script {
        echo ("BUILD FAILURE: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\nCheck console output at: ${env.BUILD_URL}")
      }
    }
  }
}
