import jobs.Portal

final space = "maint"
final header = 'hxl_maint_4.0_'
final numbBuildToKeep = 100
final defaultBranch = 'master' //'$DEFAULT_BRANCH'
def ia_versions = ['4.0.1', '4.0.0']
def jobs = [
  portal: ['provider_portal', ''],
  ia: ['job-dsl-2', 'desc']
]

jobs.each {
  job -> {
    new Portal(pipelineJob(job[0])).build()
  }
}



