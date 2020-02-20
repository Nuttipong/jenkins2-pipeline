import models.Common

package jobs

class Portal implements AddChoiceParam, AddConfig, AddStringParam, AddDefinition, AddLogRotator {
  def pipelineJob

  Portal(def pipelineJob) {
    this.pipelineJob = pipelineJob
  }

  void build() {
    this.addLogRotator(numbBuildToKeep)
    this.addChoiceParam('ENVIRONMENT', [space, space + '@AWS'], '')
    // job.addConfig('DESCENDING', 'origin/release.*')
    // job.addChoiceParam('M_APP_VERSION', ia_versions, 'tell Code-Push apply to which mobile package version')
    // job.addChoiceParam('BUILD_OPTIONS', 
    //   ['BUILD_FROM_SIT_TAG','BUILD_FROM_UAT_TAG', 'BUILD_FROM_TAG', 'BUILD_FROM_BRANCH', 'DELETE_TAG'], '')
    // job.addStringParam('BUILD_SPECIFIER', '', 'version number of SIT or UAT or MAINT tag, or branch name')
    // job.addStringParam('COMMIT_ID', '', 'BUILD_FROM_COMMIT_ID or MAKE_TAG_ONLY (MAKE_TAG_ONLY -> will make a tag with this commit id)')
    // job.addDefinition('hexalite/provider_portal', "refs/remotes/${defaultBranch}", false)
  }

  void addChoiceParam(Object[] args) {
    this.pipelineJob.with {
      parameters {
        choiceParam(args[0], args[1], args[2])
      }
    }
  }

  void addConfig(String sortMode, String filter) {
    this.pipelineJob.with = {
      configure {
          it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' / 'net.uaznia.lukanus.hudson.plugins.gitparameter.GitParameterDefinition' {
          name ('DEFAULT_BRANCH')
          description ("Default branch to be used. This option will be only for BUILD_LATEST.")
          type ('PT_BRANCH')
          branchFilter (filter)
          sortMode (sortMode)
          selectedValue('TOP')
          listSize('5')
        }
      }
    }
  }

  void addStringParam(Object[] args) {
    this.pipelineJob.with = {
      parameters {
        stringParam(args[0], args[1], args[2])
      }
    }
  }

  void addDefinition(String repo, String branch, boolean lightweight) {
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
                  scriptPath(feJenkinsfile)
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
}