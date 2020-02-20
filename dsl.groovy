import common.CommonUtils

final header = "hxl_maint_4.0_"
def giturl = 'https://github.com/Nuttipong/jenkins2-pipeline.git'
def jobs = [
  'job-dsl-1',
  'job-dsl-2'
]

multiJob('build job') {
    steps {
        phase('Second') {
            phaseJob('JobZ') { println 'JobZ' + new Date() }
        }
        phase('Third') {
            phaseJob('JobB') { println 'JobB' + new Date() }
            phaseJob('JobA') { println 'JobA' + new Date() }
            phaseJob('JobC') { println 'JobC' + new Date() }
        }
    }
}
      


// class BaseJobBuilder {
//     String name
//     String description

//     def build() {
//       (this.name) {
//         CommonUtils.addDefaults(this)
//       }
//     }
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