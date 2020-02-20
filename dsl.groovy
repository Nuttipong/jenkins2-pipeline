import jobs.PipelineJob

def tasks = [
  portal: ['provider_portal', ''],
  ia: ['job-dsl-2', 'desc']
]

tasks.values().each {
  task -> 
    println task
    new PipelineJob(task[0], task[1]).build()
}



