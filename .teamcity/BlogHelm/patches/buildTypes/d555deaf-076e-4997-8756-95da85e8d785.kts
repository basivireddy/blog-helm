package BlogHelm.patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, create a buildType with uuid = 'd555deaf-076e-4997-8756-95da85e8d785' (id = 'BlogHelm_SmokeTest')
in the project with uuid = 'd3c230cf-b4cd-4a9e-8017-4b4b945b3a3c' and delete the patch script.
*/
create("d3c230cf-b4cd-4a9e-8017-4b4b945b3a3c", BuildType({
    uuid = "d555deaf-076e-4997-8756-95da85e8d785"
    id = "BlogHelm_SmokeTest"
    name = "Smoke Test"

    vcs {
        root("BlogHelm_BlogHelm")

    }

    dependencies {
        dependency("BlogHelm_CommitStage") {
            snapshot {
                onDependencyFailure = FailureAction.FAIL_TO_START
            }
        }
    }
}))

