import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification

class JenkinsfilePromote extends JenkinsPipelineSpecification {
	def Jenkinsfile = null

    def setup() {
        Jenkinsfile = loadPipelineScriptForTest("Jenkinsfile.promote")
    }

	def "getBuildBranch: no BUILD_BRANCH_NAME parameter" () {
		setup:
            def params = [:]
            def deployProperties = [:]
            params['BUILD_BRANCH_NAME'] = ''
            deployProperties['git.branch'] = "branch"
            Jenkinsfile.getBinding().setVariable("params", params)
            Jenkinsfile.getBinding().setVariable("deployProperties", deployProperties)
		when:
			def branchName = Jenkinsfile.getBuildBranch()
		then:
            branchName == 'branch'
	}

	def "getBuildBranch: BUILD_BRANCH_NAME parameter" () {
		setup:
            def params = [:]
            def deployProperties = [:]
            params['BUILD_BRANCH_NAME'] = 'param branch'
            deployProperties['git.branch'] = "branch"
            Jenkinsfile.getBinding().setVariable("params", params)
            Jenkinsfile.getBinding().setVariable("deployProperties", deployProperties)
		when:
			def branchName = Jenkinsfile.getBuildBranch()
		then:
            branchName == 'param branch'
	}
}
