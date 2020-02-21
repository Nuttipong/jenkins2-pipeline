package jobs

class IA extends BaseJob implements Job {

  IA(def pipelineJob) {
    super(pipelineJob)
  }

  void build() {
      this.addLogRotator(100)
      this.addChoiceParam("ENVIRONMENT", ["maint", "maint" + "@AWS"], "")
      this.addChoiceParam("M_APP_VERSION", ["4.0.1", "4.0.0"], "tell Code-Push apply to which mobile package version")
      this.addChoiceParam("PATCHING_OPTION", ["patch", "minor", "major"], "select type of version patching (major.minor.patch)")
      this.addStringParam("BUILD_SPECIFIER", "", "input Branch-name or Tag-name or Commit-id")
      //this.addDefinition("hexalite/integrated_app2019", "refs/remotes/master", false, "jenkins-script/Jenkinsfile_release_3.5.groovy")
  }
}