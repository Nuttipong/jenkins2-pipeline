
import jobs.Portal
import jobs.IA as Ia
import jobs.Backend
import jobs.Mock
import logic.Job

final header = "hxl_maint_4.0_"
final headerToMock = "hxl_maint_4.0_to_abs_mock"

def tasks = [
  portal: ["provider_portal", "", Portal, false],
  ia: ["integrated_app", "", Ia, false],
  // portal: ["provider_portal_to_mock", "", Portal, true],
  // ia: ["integrated_app_to_mock", "", Ia, true],
  // apiGatewayMock: ["gbmf-apigateway-mock", "", Backend],
  // gbmfConfigServer: ["gbmf-config-server", "", Backend],
  // gbmfServiceDiscovery: ["gbmf-service-discovery", "", Backend],
  // gbmfFleetManagement: ["gbmf-fleet-management", "", Backend],
  // gbmfGeoTracking: ["gbmf-geo-tracking", "", Backend],
  // gbmfGuac: ["gbmf-guac", "", Backend],
  // gbmfNotification: ["gbmf-notification", "", Backend],
  // gbmfServiceBroker: ["gbmf-service-broker", "", Backend],
  // gbmfJobMonitoring: ["gbmf-job-monitoring", "", Backend],
  // gbmfTranslation: ["gbmf-translation", "", Backend],
  // gbmfUserManagement: ["gbmf-user-management", "", Backend],
  // gbmfJobMonitoring: ["gbmf-job-monitoring", "", Mock],
  // gbmfServiceBroker: ["gbmf-service-broker", "", Mock],
]

tasks.values().each {
  task -> 
      final header = Mock == task[2] ? headerToMock + task[0] : header + task[0]
      def pipeline = pipelineJob()
      Job job
      switch(task[2]) {
        case Portal:
          job = new Portal(pipeline)
          job.isMock = task[3]
        break;
        case Ia:
          job = new Ia(pipeline)
          job.isMock = task[3]
        break;
        case Backend:
          job = new Backend(pipeline)
          job.name = task[0]
        break;
        case Mock:
          job = new Mock(pipeline)
          job.name = task[0]
        break;
      } 
      job.build()
}


