package jobs

import jobs.BaseJob
import jobs.Job

class Portal extends BaseJob implements Job {

  Portal(def pipelineJob) {
    super(pipelineJob)
  }

  void build() {
    this.addLogRotator(100)
    this.addChoiceParam("ENVIRONMENT", ["maint", "maint" + "@AWS"], "")
    //this.addConfig("DESCENDING", "origin/release.*")
    // job.addChoiceParam("BUILD_OPTIONS", ["BUILD_FROM_SIT_TAG", 
    //     "BUILD_FROM_UAT_TAG", 
    //     "BUILD_FROM_TAG", 
    //     "BUILD_FROM_BRANCH", 
    //     "DELETE_TAG"
    //   ], "")
    // job.addStringParam("BUILD_SPECIFIER", "", "version number of SIT or UAT or MAINT tag, or branch name")
    // job.addStringParam("COMMIT_ID", "", "BUILD_FROM_COMMIT_ID or MAKE_TAG_ONLY (MAKE_TAG_ONLY -> will make a tag with this commit id)")
    // job.addDefinition("hexalite/provider_portal", "refs/remotes/${defaultBranch}", false, "jenkins-script/Jenkinsfile_release_3.5.groovy")
  }
}