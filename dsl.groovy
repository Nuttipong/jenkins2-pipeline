
final header = "hxl_maint_4.0_"

def tasks = [
  portal: ["provider_portal", "", Web],
  // ia: ["integrated_app", "", Mobile],
  // apiGatewayMock: ["gbmf-apigateway-mock", "", WebApi],
  // gbmfConfigServer: ["gbmf-config-server", "", WebApi],
  // gbmfServiceDiscovery: ["gbmf-service-discovery", "", WebApi],
  // gbmfFleetManagement: ["gbmf-fleet-management", "", WebApi],
  // gbmfGeoTracking: ["gbmf-geo-tracking", "", WebApi],
  // gbmfGuac: ["gbmf-guac", "", WebApi],
  // gbmfNotification: ["gbmf-notification", "", WebApi],
  // gbmfServiceBroker: ["gbmf-service-broker", "", WebApi],
  // gbmfTranslation: ["gbmf-translation", "", WebApi],
  // gbmfUserManagement: ["gbmf-user-management", "", WebApi],
  // gbmfJobMonitoring: ["gbmf-job-monitoring", "", WebApi],
]

tasks.values().each {
  task -> 
      def pipeline = pipelineJob(header + task[0])
      def job
      if (task[2] == Web) {
        job = new Web(pipeline)
      }
      if (task[2] == Mobile) {
        job = new Mobile(pipeline)
      }
      if (task[2] == WebApi) {
        job = new WebApi(pipeline)
        job.name = task[0]
      }
      job.build()
}

interface AddChoiceParam {
  void addChoiceParam(Object[] args)
}

interface AddConfig {
  void addConfig(String sortMode, String filter)
}

interface AddStringParam {
  void addStringParam(Object[] args)
}

interface AddDefinition {
  void addDefinition(String repo, String branch, boolean lightweight, String jenkinsFile)
}

interface AddLogRotator {
  void addLogRotator(int number)
}

abstract class BaseJob implements AddChoiceParam, AddConfig, AddStringParam, AddDefinition, AddLogRotator {
  def pipelineJob

  BaseJob(def pipelineJob) {
    this.pipelineJob = pipelineJob
  }

  void addChoiceParam(Object[] args) {
    this.pipelineJob.with {
      parameters {
        choiceParam(args[0], args[1], args[2])
      }
    }
  }

  void addConfig(String sortMode, String filter) {
    this.pipelineJob.with {
      configure {
          it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' / 'net.uaznia.lukanus.hudson.plugins.gitparameter.GitParameterDefinition' {
          name ('DEFAULT_BRANCH')
          description ("Default branch to be used. This option will be only for BUILD_LATEST.")
          type ('PT_BRANCH')
          branchFilter ('origin/release.*')
          sortMode ('DESCENDING')
          selectedValue('TOP')
          listSize('5')
        }
      }
    }
  }

  void addStringParam(Object[] args) {
    this.pipelineJob.with {
      parameters {
        stringParam(args[0], args[1], args[2])
      }
    }
  }

  void addDefinition(String repo, String branch, boolean lightweight, String jenkinsFile) {
    this.pipelineJob.with {
      definition {
          cpsScm {
              scm {
                  git {
                      remote {
                          github(repo, "https", "github.developer.allianz.io")
                          credentials("git-token-credentials")
                      }
                      branch(branch)
                      extensions{
                          wipeOutWorkspace()
                          cloneOptions {
                              depth(2)
                              honorRefspec(false)
                              shallow(true)
                              noTags(true)
                              timeout(10)
                          }
                      }
                  }
                  scriptPath(jenkinsFile)
                  lightweight(lightweight)
              }
          }
      }
    }
  }

  void addLogRotator(int number) {
    this.pipelineJob.with {
      logRotator {
        numToKeep(number)
      }
    }
  }

  abstract void build()
}

class Web extends BaseJob {

  Web(def pipelineJob) {
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

class Mobile extends BaseJob {

  Mobile(def pipelineJob) {
    super(pipelineJob)
  }

  void build() {
      this.addLogRotator(100)
      this.addChoiceParam("ENVIRONMENT", ["maint", "maint" + "@AWS"], "")
      this.addChoiceParam("M_APP_VERSION", ["4.0.1", "4.0.0"], "tell Code-Push apply to which mobile package version")
      this.addChoiceParam("PATCHING_OPTION", ["patch", "minor", "major"], "select type of version patching (major.minor.patch)")
      this.addStringParam("BUILD_SPECIFIER", "", "input Branch-name or Tag-name or Commit-id")
      this.addDefinition("hexalite/integrated_app2019", "refs/remotes/master", false, "jenkins-script/Jenkinsfile_release_3.5.groovy")
  }
}

class WebApi extends BaseJob {
  def name

  WebApi(def pipelineJob) {
    super(pipelineJob)
  }

  abstract  build() {
      this.addLogRotator(100)
      this.addChoiceParam("COMPONENT", [this.name], "Component to Build")
      this.addChoiceParam("ENVIRONMENT", ["maint", "maint" + "@AWS"], "")
      //this.addConfig("DESCENDING", "origin/release.*")
      this.addChoiceParam("BUILD_OPTIONS", [
        "BUILD_LATEST_FROM_RELEASE40_BRANCH_WITHOUT_MAKING_TAG",
        "BUILD_LATEST_FROM_RELEASE40_BRANCH_AND_MAKE_TAG",
        "BUILD_FROM_BRANCH",
        "BUILD_FROM_COMMIT_ID",
        "BUILD_FROM_TAG",
        "MAKE_TAG_ONLY",
        "DELETE_TAG"
        ], "")
        this.addStringParam("BUILD_SPECIFIER", "", "version number of SIT or UAT or MAINT tag, or branch name")
        this.addStringParam("COMMIT_ID", "", "BUILD_FROM_COMMIT_ID or MAKE_TAG_ONLY (MAKE_TAG_ONLY -> will make a tag with this commit id)")
        //this.addDefinition("hexalite/group_solution", "refs/remotes/master", false, "jenkins-script/Jenkinsfile_release_single_3.5.groovy")
  }
}

class MockApi extends BaseJob {
  MockApi(def pipelineJob) {
    super(pipelineJob)
  }

  void build() {

  }
}
