

// def started = new Date()

// parallel firstBranch: {
//         // do something
//         println 'Hello 1'
//         println new Date() - started
//     }, secondBranch: {
//         // do something else
//         println 'Hello 2'
//         println new Date() - started
//     },
//     failFast: true

pipelineJob('job-dsl-plugin') {
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/Nuttipong/jenkins2-pipeline.git')
          }
          branch('*/master')
        }
      }
      lightweight()
    }
  }
}