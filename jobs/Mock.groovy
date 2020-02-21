package jobs

import jobs.BaseJob
import logic.Job

class Mock extends BaseJob implements Job {
  def name

  Mock(def pipelineJob) {
    super(pipelineJob)
  }

  void build() {
      this.addLogRotator(100)
      this.addChoiceParam("COMPONENT", [this.name], "Component to Build")
      this.addChoiceParam("ENVIRONMENT", ["maint", "maint" + "@AWS"], "")
      this.addConfig("ASCENDING", "origin/release.*|origin/master")
      this.addChoiceParam("BUILD_OPTIONS", [
        "BUILD_FROM_BRANCH",
        "BUILD_FROM_UAT_TAG",
        "BUILD_FROM_TAG",
        "BUILD_FROM_COMMIT_ID"
        ], "")
      this.addStringParam("BUILD_SPECIFIER", "", "Specify version number of MAINT tag or branch name. this field will be ignored if WITHOUT_MAKING_TAG is chosen.")
      this.addStringParam("COMMIT_ID", "", "BUILD_FROM_COMMIT_ID or MAKE_TAG_ONLY (MAKE_TAG_ONLY -> will make a tag with this commit id)")
      this.addDefinition("hexalite/group_solution", "refs/remotes/\${DEFAULT_BRANCH}", false, "jenkins-script/Jenkinsfile_release_single_3.5.groovy")
  }
}