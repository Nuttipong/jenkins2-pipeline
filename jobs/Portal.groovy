package jobs

import jobs.BaseJob
import logic.Job

class Portal extends BaseJob implements Job {
  boolean isMock = true

  Portal(def pipelineJob) {
    super(pipelineJob)
  }

  void build() {
    final sorted = isMock == true ? "ASCENDING" : "DESCENDING"
    final branch = isMock == true ? "origin/release.*|origin/master" : "origin/release.*"
    def buildOptions = []
    if (isMock == true) {
      buildOptions = [ "BUILD_FROM_BRANCH", "BUILD_FROM_UAT_TAG", "BUILD_FROM_TAG", "BUILD_FROM_COMMIT_ID"]
    } else {
      buildOptions = ["BUILD_FROM_SIT_TAG", "BUILD_FROM_UAT_TAG", "BUILD_FROM_TAG", "BUILD_FROM_BRANCH", "DELETE_TAG"]
    }
    final buildSpecifier = isMock == true ? "master" : ""

    this.addLogRotator(100)
    this.addChoiceParam("ENVIRONMENT", ["maint", "maint" + "@AWS"], "")
    this.addConfig(sorted, branch)
    this.addChoiceParam("BUILD_OPTIONS", buildOptions, "")
    this.addStringParam("BUILD_SPECIFIER", buildSpecifier, "version number of SIT or UAT or MAINT tag, or branch name")
    this.addStringParam("COMMIT_ID", "", "BUILD_FROM_COMMIT_ID or MAKE_TAG_ONLY (MAKE_TAG_ONLY -> will make a tag with this commit id)")
    this.addDefinition("hexalite/provider_portal", "refs/remotes/\${DEFAULT_BRANCH}", false, "jenkins-script/Jenkinsfile_release_3.5.groovy")
  }
}