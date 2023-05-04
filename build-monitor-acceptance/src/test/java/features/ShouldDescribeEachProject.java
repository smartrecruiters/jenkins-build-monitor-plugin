package features;

import static net.serenitybdd.screenplay.GivenWhenThen.givenThat;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.GivenWhenThen.then;
import static net.serenitybdd.screenplay.GivenWhenThen.when;
import static org.hamcrest.Matchers.is;

import com.smartcodeltd.jenkinsci.plugins.build_monitor.questions.ProjectWidget;
import com.smartcodeltd.jenkinsci.plugins.build_monitor.tasks.HaveABuildMonitorViewCreated;
import hudson.plugins.descriptionsetter.tasks.SetBuildDescription;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.jenkins.HaveAProjectCreated;
import net.serenitybdd.screenplay.jenkins.tasks.ScheduleABuild;
import net.serenitybdd.screenplay.jenkins.tasks.configuration.build_steps.ExecuteAShellScript;
import net.serenitybdd.screenplay.jenkins.tasks.configuration.build_steps.ShellScript;
import net.serenitybdd.screenplayx.actions.Navigate;
import org.junit.Before;
import org.junit.Test;

public class ShouldDescribeEachProject extends BuildMonitorAcceptanceTest {

    private Actor dave = Actor.named("Dave");

    @Before
    public void actorCanBrowseTheWeb() {
        dave.can(BrowseTheWeb.with(browser));
    }

    @Test
    public void displaying_a_custom_build_description() {
        givenThat(dave)
                .wasAbleTo(
                        Navigate.to(jenkins.url()),
                        HaveAProjectCreated.called("Example Github Project")
                                .andConfiguredTo(
                                        ExecuteAShellScript.that(outputsGithubProjectLog()),
                                        SetBuildDescription.to("Revision: \\1")
                                                .basedOnLogLineMatching("Checking out Revision ([\\w]{6})")),
                        ScheduleABuild.of("Example Github Project"));

        when(dave).attemptsTo(HaveABuildMonitorViewCreated.showingAllTheProjects());

        then(dave).should(seeThat(ProjectWidget.of("Example Github Project").details(), is("Revision: 67f4b3")));
    }

    private ShellScript outputsGithubProjectLog() {
        return ShellScript.that("simulates checking out a project from github")
                .andOutputs(
                        "Started by an SCM change",
                        "[EnvInject] - Loading node environment variables.",
                        "Building remotely on 6479ff42 (lxc-fedora17 m1.small small) in workspace /scratch/jenkins/workspace/metricsd",
                        "Cloning the remote Git repository",
                        "Cloning repository git@github.com:jan-molak/metricsd.git",
                        " > git init /scratch/jenkins/workspace/metricsd # timeout=10",
                        "Fetching upstream changes from git@github.com:jan-molak/metricsd.git",
                        " > git --version # timeout=10",
                        " > git -c core.askpass=true fetch --tags --progress git@github.com:jan-molak/metricsd.git +refs/heads/*:refs/remotes/origin/*",
                        " > git config remote.origin.url git@github.com:jan-molak/metricsd.git # timeout=10",
                        " > git config --add remote.origin.fetch +refs/heads/*:refs/remotes/origin/* # timeout=10",
                        " > git config remote.origin.url git@github.com:jan-molak/metricsd.git # timeout=10",
                        "Fetching upstream changes from git@github.com:jan-molak/metricsd.git",
                        " > git -c core.askpass=true fetch --tags --progress git@github.com:jan-molak/metricsd.git +refs/heads/*:refs/remotes/origin/*",
                        " > git rev-parse refs/remotes/origin/master^{commit} # timeout=10",
                        " > git rev-parse refs/remotes/origin/origin/master^{commit} # timeout=10",
                        "Checking out Revision 67f4b364e14e4282115d8e518cfe75d86e9d3f2f (refs/remotes/origin/master)",
                        " > git config core.sparsecheckout # timeout=10",
                        " > git checkout -f 67f4b364e14e4282115d8e518cfe75d86e9d3f2f",
                        " > git rev-list be0d664d303bfe3f66e58be1e3f3e723308f5108 # timeout=10");
    }
}
