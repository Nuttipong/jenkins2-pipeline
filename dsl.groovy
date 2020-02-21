
import jobs.Portal
import jobs.IA as Ia
import jobs.Backend
import logic.Job

/*
*  ===========================================================
*                           MAINT
*  ===========================================================
*/

def tasks = [
  portal: ["provider-portal", "", Portal, false],
  ia: ["integrated-app", "", Ia, false],
  portalMock: ["provider-portal-mock", "", Portal, true],
  iaMock: ["integrated-app-mock", "", Ia, true],
  apiGatewayMock: ["gbmf-apigateway-mock", "", Backend, false],
  gbmfConfigServer: ["gbmf-config-server", "", Backend, false],
  gbmfServiceDiscovery: ["gbmf-service-discovery", "", Backend, false],
  gbmfFleetManagement: ["gbmf-fleet-management", "", Backend, false],
  gbmfGeoTracking: ["gbmf-geo-tracking", "", Backend, false],
  gbmfGuac: ["gbmf-guac", "", Backend, false],
  gbmfNotification: ["gbmf-notification", "", Backend, false],
  gbmfServiceBroker: ["gbmf-service-broker", "", Backend, false],
  gbmfJobMonitoring: ["gbmf-job-monitoring", "", Backend, false],
  gbmfTranslation: ["gbmf-translation", "", Backend, false],
  gbmfUserManagement: ["gbmf-user-management", "", Backend, false],
  gbmfJobMonitoringMock: ["gbmf-job-monitoring-mock", "", Backend, true],
  gbmfServiceBrokerMock: ["gbmf-service-broker-mock", "", Backend, true]
]

def moduleList = ["not-build"]

tasks.values().each {
  task -> 
      def pipeline = pipelineJob(task[0])
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
          job.isMock = task[3]
          moduleList.push(task[0])
        break;
      } 
      job.build()
}

