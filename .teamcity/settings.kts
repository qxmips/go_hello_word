import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2018.2"

project {

    vcsRoot(HttpsGithubComQxmipsGoHelloWordRefsHeadsMaster)

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGithubComQxmipsGoHelloWordRefsHeadsMaster)
    }

    steps {
        script {
            name = "Build"
            scriptContent = """
                #!/bin/bash
                 /usr/local/go/bin/go build hello-world.go
            """.trimIndent()
        }
        script {
            name = "run app"
            scriptContent = "./hello-world"
        }
    }

    triggers {
        vcs {
            triggerRules = "+:**"
            branchFilter = ""
            perCheckinTriggering = true
            enableQueueOptimization = false
        }
    }

    requirements {
        equals("system.agent.name", "regular_agent")
    }
})

object HttpsGithubComQxmipsGoHelloWordRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/qxmips/go_hello_word#refs/heads/master"
    url = "https://github.com/qxmips/go_hello_word"
})

