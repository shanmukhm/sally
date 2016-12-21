/*
 * Copyright (c) CASABOTS 2016
 * All Rights Reserved
 */

node {
    stage('Build') {
        checkout scm
        sh 'mvn clean install'
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }
}