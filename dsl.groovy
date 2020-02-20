

final header = "hxl_maint_4.0_"
def giturl = 'https://github.com/Nuttipong/jenkins2-pipeline.git'
def jobs = [
  'job-dsl-1', 
  'job-dsl-2'
]




class BaseJobBuilder {
    String name
    String description

    def build() {
      job(this.name) {
        CommonUtils.addDefaults(this)
      }
    }
}

class CommonUtils {
  static void addDefaults(context) {
      context.with {
          wrappers {
              colorizeOutput()
              timestamps()
          }
          logRotator {
              numToKeep(100)
          }
          definition {
            cpsScm {
              scm {
                git {
                  remote {
                    url(giturl)
                  }
                  branch('*/master')
                }
              }
              lightweight()
            }
          }
      }
  }
}

parallel firstBranch: {
        println new Date()
    }, secondBranch: {

        println new Date()
    },
    failFast: true

// pipelineJob('job-dsl-plugin') {
//   definition {
//     cpsScm {
//       scm {
//         git {
//           remote {
//             url(giturl)
//           }
//           branch('*/master')
//         }
//       }
//       lightweight()
//     }
//   }
// }