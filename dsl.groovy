import jobs.Job

def tasks = [
  portal: ['provider_portal', ''],
  ia: ['job-dsl-2', 'desc']
]

tasks.values().each {
  task -> new Job(task[0], task[1]).build()
}



