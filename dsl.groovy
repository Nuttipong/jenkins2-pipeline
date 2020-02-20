// import utilities.CommonUtils

final space = "maint"
final numbBuildToKeep = 100
final header = 'hxl_maint_4.0_'
final defaultBranch = 'master' //'$DEFAULT_BRANCH'
def giturl = 'https://github.com/Nuttipong/jenkins2-pipeline.git'
def jobs = [
  portal: ['provider_portal', ''],
  ia: ['job-dsl-2', 'desc']
]

Set ia_versions = ['4.0.1', '4.0.0']

// multiJob('build hxl jobs') {
//     steps {
//         phase('portal') {
          
//         }
//         phase('ia') {

//         }
//         phase('backend') {

//         }
//     }
// }


class JobBuilder {
    String name
    String description

    def build() {
      pipelineJob(this.name) {
        logRotator {
          numToKeep(numbBuildToKeep)
        }
      }
    }
}

interface CreateJob {
  void createJob(String name, String description)
}

interface AddChoiceParam {
  void addChoiceParam(String param1, String[] param2, String param3)
}

interface AddConfig {
  void addConfig(String sortMode)
}

interface AddStringParam {
  void addStringParam(String param1, String param2, String param3)
}

interface AddDefinition {
  void addDefinition(String repo, String branch, boolean lightweight)
}

class Job //implements AddChoiceParam, AddConfig, AddStringParam, AddDefinition {
  {
  def pj

  Job(def pj) {
    this.pj = pj
  }

  void addChoiceParam(Object[] args) {
    this.pj.with {
      choiceParam(args[0], args[1], args[2])
    }
  }

  void addConfig(String sortMode) {
    job.with = {
      configure {
          it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' / 'net.uaznia.lukanus.hudson.plugins.gitparameter.GitParameterDefinition' {
          name ('DEFAULT_BRANCH')
          description ("Default branch to be used. This option will be only for BUILD_LATEST.")
          type ('PT_BRANCH')
          branchFilter ('origin/release.*')
          sortMode (sortMode)
          selectedValue('TOP')
          listSize('5')
        }
      }
    }
  }

  void addStringParam(String param1 = '', String param2 = '', String param3 = '') {
    job.with = {
      stringParam(param1, param2, param3)
    }
  }

  void addDefinition(String repo, String branch, boolean lightweight) {
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

def addChoiceParam(String param1, String[] param2, String param3 = '') {
  choiceParam(param1, [param2], param3)
}


// def portal = new Job(pipelineJob: pipelineJob(jobs['portal'][0]))
// portal.addChoiceParam('ENVIRONMENT', [space, space + '@AWS'], '')

class A {
  String name

  String func() {
    name
  }
}

def a = new A(name: 'test')

println "---->${a}"
println "---->${a.func()}"
println "----> here"

def pipe = pipelineJob(jobs['portal'][0])
def job = new Job(pipe)
println "--Job-->${job}"
println "--pj-->${job.pj}"
job.addChoiceParam('ENVIRONMENT', [space, space + '@AWS'], '')


  // logRotator {
  //   numToKeep(numbBuildToKeep)
  // }
  // addChoiceParam('ENVIRONMENT', [space, space + '@AWS'], '')
  //job.addConfig('DESCENDING')
  // job.addChoiceParam('M_APP_VERSION', ia_versions, 'tell Code-Push apply to which mobile package version')
  // job.addChoiceParam('BUILD_OPTIONS', 
  //   ['BUILD_FROM_SIT_TAG','BUILD_FROM_UAT_TAG', 'BUILD_FROM_TAG', 'BUILD_FROM_BRANCH', 'DELETE_TAG'], '')
  // job.addStringParam('BUILD_SPECIFIER', '', 'version number of SIT or UAT or MAINT tag, or branch name')
  // job.addStringParam('COMMIT_ID', '', 'BUILD_FROM_COMMIT_ID or MAKE_TAG_ONLY (MAKE_TAG_ONLY -> will make a tag with this commit id)')
  // job.addDefinition('hexalite/provider_portal', "refs/remotes/${defaultBranch}", false)
//}

//job.createJob(jobs['portal'][0], jobs['portal'][1])
// job.addChoiceParam('ENVIRONMENT', [space, space + '@AWS'], '')
// job.addConfig('DESCENDING')
// job.addChoiceParam('M_APP_VERSION', ia_versions, 'tell Code-Push apply to which mobile package version')
// job.addChoiceParam('BUILD_OPTIONS', 
//   ['BUILD_FROM_SIT_TAG','BUILD_FROM_UAT_TAG', 'BUILD_FROM_TAG', 'BUILD_FROM_BRANCH', 'DELETE_TAG'], '')
// job.addStringParam('BUILD_SPECIFIER', '', 'version number of SIT or UAT or MAINT tag, or branch name')
// job.addStringParam('COMMIT_ID', '', 'BUILD_FROM_COMMIT_ID or MAKE_TAG_ONLY (MAKE_TAG_ONLY -> will make a tag with this commit id)')
// job.addDefinition('hexalite/provider_portal', "refs/remotes/${defaultBranch}", false)
