package jobs

import jobs.BaseJob
import logic.Job

class Mock extends BaseJob implements Job {
  Mock(def pipelineJob) {
    super(pipelineJob)
  }

  void build() {

  }
}