final space = "maint"
final header = "hxl_maint_4.0_"
final numbBuildToKeep = 100
final defaultBranch = "master" //"$DEFAULT_BRANCH"
final beJenkinsfile = "jenkins-script/Jenkinsfile_release_single_3.5.groovy"
def ia_versions = ["4.0.1", "4.0.0"]

class Portal implements AddChoiceParam, AddConfig, AddStringParam, AddDefinition, AddLogRotator {
  def pipelineJob

  Portal(def pipelineJob) {
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
          it / 'properties' / "hudson.model.ParametersDefinitionProperty" / 'parameterDefinitions' / "net.uaznia.lukanus.hudson.plugins.gitparameter.GitParameterDefinition" {
          name ("DEFAULT_BRANCH")
          description ("Default branch to be used. This option will be only for BUILD_LATEST.")
          type ("PT_BRANCH")
          branchFilter (filter)
          sortMode (sortMode)
          selectedValue("TOP")
          listSize("5")
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

  void build() {
      this.addLogRotator(100)
      this.addChoiceParam("ENVIRONMENT", ["maint", "maint" + "@AWS"], "")
      this.addConfig("DESCENDING", "origin/release.*")
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

def tasks = [
  portal: ["${header}provider_portal", "", Portal],
  // ia: ["${header}integrated_app", ""]
]

tasks.values().each {
  task -> 
      def pipeline = pipelineJob(task[0])
      //if (task[0] == Portal) {
        def job = new Portal(pipeline)
        job.build()
      //}
}



