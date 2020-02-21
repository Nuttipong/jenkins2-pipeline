package jobs

import jobs.BaseJob
import logic.Job

class IA extends BaseJob implements Job {
  boolean isMock = true

  IA(def pipelineJob) {
    super(pipelineJob)
  }

  void build() {
      final sorted = isMock == true ? "ASCENDING" : "DESCENDING"
      final branchFilter = isMock == true ? "origin/release.*|origin/master" : "origin/release.*"

      this.addLogRotator(100)
      this.addChoiceParam("ENVIRONMENT", ["maint", "maint" + "@AWS"], "")
      // this.addConfig(sorted, branchFilter)
      // this.addChoiceParam("M_APP_VERSION", ["4.0.1", "4.0.0"], "tell Code-Push apply to which mobile package version")
      // this.addChoiceParam("PATCHING_OPTION", ["patch", "minor", "major"], "select type of version patching (major.minor.patch)")
      // this.addStringParam("BUILD_SPECIFIER", "", "input Branch-name or Tag-name or Commit-id")
      // this.addDefinition("hexalite/integrated_app2019", "refs/remotes/\${DEFAULT_BRANCH}", false, "jenkins-script/Jenkinsfile_release_3.5.groovy")
  }
}