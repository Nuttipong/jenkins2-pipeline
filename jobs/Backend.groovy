package jobs

import jobs.BaseJob
import logic.Job

class Backend extends BaseJob implements Job {
  def name

  Backend(def pipelineJob) {
    super(pipelineJob)
  }

  void build() {
      this.addLogRotator(100)
      this.addChoiceParam("COMPONENT", [this.name], "Component to Build")
      this.addChoiceParam("ENVIRONMENT", ["maint", "maint" + "@AWS"], "")
      // this.addConfig("DESCENDING", "origin/release.*")
      // this.addChoiceParam("BUILD_OPTIONS", [
      //   "BUILD_LATEST_FROM_RELEASE40_BRANCH_WITHOUT_MAKING_TAG",
      //   "BUILD_LATEST_FROM_RELEASE40_BRANCH_AND_MAKE_TAG",
      //   "BUILD_FROM_BRANCH",
      //   "BUILD_FROM_COMMIT_ID",
      //   "BUILD_FROM_TAG",
      //   "MAKE_TAG_ONLY",
      //   "DELETE_TAG"
      //   ], "")
      // this.addStringParam("BUILD_SPECIFIER", "", "version number of SIT or UAT or MAINT tag, or branch name")
      // this.addStringParam("COMMIT_ID", "", "BUILD_FROM_COMMIT_ID or MAKE_TAG_ONLY (MAKE_TAG_ONLY -> will make a tag with this commit id)")
      // this.addDefinition("hexalite/group_solution", "refs/remotes/\${DEFAULT_BRANCH}", false, "jenkins-script/Jenkinsfile_release_single_3.5.groovy")
  }
}