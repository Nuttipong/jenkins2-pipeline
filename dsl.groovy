
import jobs.Portal
import jobs.IA as Ia
import jobs.Backend
import jobs.Mock
import logic.Job

final header = "hxl_maint_4.0_"

def tasks = [
  portal: ["provider_portal", "", Portal],
  ia: ["integrated_app", "", Ia],
  apiGatewayMock: ["gbmf-apigateway-mock", "", Backend],
  // gbmfConfigServer: ["gbmf-config-server", "", WebApi],
  // gbmfServiceDiscovery: ["gbmf-service-discovery", "", WebApi],
  // gbmfFleetManagement: ["gbmf-fleet-management", "", WebApi],
  // gbmfGeoTracking: ["gbmf-geo-tracking", "", WebApi],
  // gbmfGuac: ["gbmf-guac", "", WebApi],
  // gbmfNotification: ["gbmf-notification", "", WebApi],
  // gbmfServiceBroker: ["gbmf-service-broker", "", WebApi],
  // gbmfTranslation: ["gbmf-translation", "", WebApi],
  // gbmfUserManagement: ["gbmf-user-management", "", WebApi],
  // gbmfJobMonitoring: ["gbmf-job-monitoring", "", WebApi],
]

tasks.values().each {
  task -> 
      def pipeline = pipelineJob(header + task[0])
      Job job
      switch(task[2]) {
        case Portal:
          job = new Portal(pipeline)
        break;
        case Ia:
          job = new Ia(pipeline)
        break;
        case Backend:
          job = new Backend(pipeline)
          job.name = task[0]
        break;
      } 
      job.build()
}


