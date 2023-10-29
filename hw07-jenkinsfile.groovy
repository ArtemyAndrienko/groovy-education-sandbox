node {
    def selectedRepo = "https://github.com/ArtemyAndrienko/groovy-education-sandbox.git"
    def subprojects = ["hw02-gradle","hw03-gradle","hw04-json-gradle","hw07-gradle","hw08-dsl-gradle"]

    def builder = tool name: "gradle-${params.GRADLE_VERSION}", type: "gradle"
    def javaJDK = tool name: "openjdk-${params.JAVA_VERSION}", type: "jdk"

    CalculateFilesCount(subprojects)

    stage('Checkout') {
        git branch: params.GIT_BRANCH,
                credentialsId: "git",
                url: selectedRepo
    }


    // Split the list into batches
    def parallelBatches = subprojects.collate(subprojects.size())

    for (parallelBatch in parallelBatches) {
        def tasks = [:]

        for (task in parallelBatch) {
            def submodule = task
            tasks["${submodule}"] = {


                stage('Unit & Integration Tests') {
                    dir(submodule){
                        try {
                            PrintSysTime(params.GIT_BRANCH)
                            sh "${builder}/bin/gradle clean test --no-daemon"
                        } finally {
                            catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                                junit '**/build/test-results/test/*.xml' //make the junit test results available in any case (success & failure)
                            }
                        }
                    }
                }
                stage("build app") {
                    println "build app - ${submodule}"
                    dir(submodule){
                        PrintSysTime(params.GIT_BRANCH)
                        sh "${builder}/bin/gradle clean build"
                    }
                }

                stage("publish app") {
                    println "build image and push to Dockerhub - ${submodule}"
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh(script: "${builder}/bin/gradle -p ${submodule} jib -Djib.to.auth.username=${USERNAME} -Djib.to.auth.password='${PASSWORD}'", returnStdout: true)
                    }
                }

            }
        }

        parallel tasks

    }


}


def PrintSysTime(String param){
    final String currentTime = sh(returnStdout: true, script: 'date +%Y-%m-%dT%T').trim()
    println "timestamp: ${currentTime} -> branch_name: ${params.GIT_BRANCH}"
}


def CalculateFilesCount(List list){
    for (String elem: list){
        def cnt = sh(script: "set +x; ls -1R ${elem} | wc -l ", returnStdout: true)
        println "Файлов в каталоге ${elem}: ${cnt}"
    }
}