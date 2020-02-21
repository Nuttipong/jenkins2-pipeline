package jobs

import jobs.BaseJob
import jobs.Job

class Mock extends BaseJob implements Job {
  Mock(def pipelineJob) {
    super(pipelineJob)
  }

  void build() {

  }
}