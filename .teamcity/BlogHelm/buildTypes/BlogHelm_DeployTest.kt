package BlogHelm.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.*

object BlogHelm_DeployTest : BuildType({
    template(BlogHelm.buildTypes.BlogHelm_DeployTemplate)
    uuid = "2c559e38-b9b4-4dcf-a79f-2faa91c9f5af"
    id = "BlogHelm_DeployTest"
    name = "Deploy To Test"

    params {
        param("env", "test")
    }
})