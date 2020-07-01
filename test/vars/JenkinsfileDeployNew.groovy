import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification

class JenkinsfileDeployNew extends JenkinsPipelineSpecification {
	def Jenkinsfile = null

    def setup() {
        Jenkinsfile = loadPipelineScriptForTest("Jenkinsfile.deploy.new")
    }

	def "RELEASE and empty PROJECT_VERSION" () {
		setup:
			Jenkinsfile.getBinding().setVariable("RELEASE", "true")
			Jenkinsfile.getBinding().setVariable("PROJECT_VERSION", "a")
		when:
			Jenkinsfile.run()
		then:
            1 * getPipelineMock("assert")("params.PROJECT_VERSION != ''")
	}
}
