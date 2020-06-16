import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification
import hudson.plugins.git.GitSCM

class GithubScmSpec extends JenkinsPipelineSpecification {
    def groovyScript = null

    def setup() {
        groovyScript = loadPipelineScriptForTest("vars/githubscm.groovy")

        // shared setup for tagRepository 
        explicitlyMockPipelineVariable("out")
        getPipelineMock("sh")([returnStdout: true, script: 'git log --oneline -1']) >> {
            return 'commitIdMock'
        }

        // shared setup for pushObject  
        explicitlyMockPipelineVariable("GIT_USERNAME")
        explicitlyMockPipelineVariable("GIT_PASSWORD")
        /* getPipelineMock("usernamePassword")(_) >> {
            return 'usernamePassword'
        }
        getPipelineMock("withCredentials")("usernamePassword") >> {
            return 'credentials'
        } */
    }

    /* def "[githubscm.groovy] tagRepository with buildTag"() {
        when:
            groovyScript.tagRepository('userName', 'user@email.com', 'tagName', 'buildTag')
        then:
            1 * getPipelineMock("sh")("git config user.name 'userName'")
            1 * getPipelineMock("sh")("git config user.email 'user@email.com'")
            1 * getPipelineMock("sh")("git tag -a 'tagName' -m 'Tagged by Jenkins in build \"buildTag\".'")
    }

    def "[githubscm.groovy] tag repository without build tag"() {
        when:
            groovyScript.tagRepository('userName', 'user@email.com', 'tagName')
        then:
            1 * getPipelineMock("sh")("git config user.name 'userName'")
            1 * getPipelineMock("sh")("git config user.email 'user@email.com'")
            1 * getPipelineMock("sh")("git tag -a 'tagName' -m 'Tagged by Jenkins.'")
    } */

    def "[githubscm.groovy] pushObject without credentialsId"() {
        setup:
            getPipelineMock("usernamePassword")(['credentialsId': 'kie-ci', 'usernameVariable':'GIT_USERNAME', 'passwordVariable':'GIT_PASSWORD']) >> {
                return 'kie-ci'
            }
            getPipelineMock("withCredentials")("kie-ci") >> {
                return 'kie-creds'
            }
        when:
            groovyScript.pushObject('remote', 'object')
        then:
            1 * getPipelineMock("usernamePassword")(['credentialsId': 'kie-ci', 'usernameVariable':'GIT_USERNAME', 'passwordVariable':'GIT_PASSWORD'])
            1 * getPipelineMock("withCredentials")("kie-ci")
            1 * getPipelineMock("sh")("git config --local credential.helper \"!f() { echo username=\\$GIT_USERNAME; echo password=\\$GIT_PASSWORD; }; f\"")
            1 * getPipelineMock("sh")("git push remote object")
    }

    /* def "[githubscm.groovy] push object with credentials"() {
        setup:
        groovyScript.getBinding().setVariable("GIT_USERNAME", 'username')
        groovyScript.getBinding().setVariable("GIT_PASSWORD", 'password')
        when:
        groovyScript.pushObject('remote', 'object', 'other-credentials')
        then:
        1 * getPipelineMock("withCredentials")([usernamePassword(credentialsId: 'other-credentials', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')])
        1 * getPipelineMock("sh")("""
        git config --local credential.helper .... and so on
    """)
    }

    def "[githubscm.groovy] push object exception"() {
        setup:
        groovyScript.getBinding().setVariable("GIT_USERNAME", 'username')
        groovyScript.getBinding().setVariable("GIT_PASSWORD", 'password')
        getPipelineMock("sh")(_) >> {
            throw new Exception("amazing exception")
        }
        when:
        groovyScript.pushObject('remote', 'object', 'other-credentials')
        then:        
        thrown(Exception)
    } */
}
