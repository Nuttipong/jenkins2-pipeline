package jobs

import logic.Job
import logic.AddLogRotator
import logic.AddChoiceParam
import logic.AddConfig
import logic.AddStringParam
import logic.AddDefinition

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