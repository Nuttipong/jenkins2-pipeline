import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.*

def giturl = 'https://github.com/Nuttipong/jenkins2-pipeline.git'
def jobs = [
  name: 'job-dsl-1', 
  name: 'job-dsl-2'
]
def started = new Date()
final header = "hxl_maint_4.0_"

jobs.each {
  job -> jobs[job.name] = {
    println job.name
    new BaseJobBuilder(
      name: header + job.name,
      description: 'test'
    ).build(this)
  }
}
println jobs
parallel jobs

class BaseJobBuilder {
    String name
    String description

    Job build(DslFactory factory) {
        factory.pipelineJob(name) {
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