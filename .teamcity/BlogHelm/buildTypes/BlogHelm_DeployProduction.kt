package BlogHelm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.finishBuildTrigger

object BlogHelm_DeployProduction : BuildType({
    uuid = "961a48d2-8e68-4405-85c9-6af47bda89ab"
    id("BlogHelm_DeployProduction")
    name = "Deploy To Production"
    templates = arrayListOf(AbsoluteId("BlogHelm_DeployTemplate"))

    params {
        param("app.env", "prod")
        param("app.host", "%app.name%.local")
    }

    dependencies {
        dependency(BlogHelm.buildTypes.BlogHelm_DeployAcceptance) {
            snapshot {
                onDependencyFailure = FailureAction.FAIL_TO_START
            }
        }
    }

    triggers {
        finishBuildTrigger {
            buildTypeExtId = "BlogHelm_DeployAcceptance"
            successfulOnly = true
        }
    }
})
