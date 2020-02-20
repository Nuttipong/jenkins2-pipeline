import jobs.Job

def jobs = [
  portal: ['provider_portal', ''],
  ia: ['job-dsl-2', 'desc']
]

jobs.values().each {
  job -> new Job(job[0], job[1]).build()
}



