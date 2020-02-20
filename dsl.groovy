

final header = "hxl_maint_4.0_"
def giturl = 'https://github.com/Nuttipong/jenkins2-pipeline.git'
def jobs = [
  'job-dsl-1', 
  'job-dsl-2'
]
def tasks = [:]

jobs.each {
  job -> tasks[job] = {
    new BaseJobBuilder(
      name: header + job,
      description: 'test'
    ).build(this)
  }
}
parallel tasks

class BaseJobBuilder {
    String name
    String description

    def build(def factory) {
        factory.pipelineJob(this.name) {
            it.description this.description
            CommonUtils.addDefaults(factory)
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
      }
  }
}

// parallel firstBranch: {
//         println new Date() - started
//     }, secondBranch: {

//         println new Date() - started
//     },
//     failFast: true
// }

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