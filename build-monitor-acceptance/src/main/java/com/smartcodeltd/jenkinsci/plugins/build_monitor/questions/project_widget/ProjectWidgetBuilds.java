package com.smartcodeltd.jenkinsci.plugins.build_monitor.questions.project_widget;

import com.smartcodeltd.jenkinsci.plugins.build_monitor.user_interface.BuildMonitorDashboard;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

@Subject("builds executed for '#projectName'")
public class ProjectWidgetBuilds implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        Target builds = BuildMonitorDashboard.Project_Widget_Builds.of(projectName);

        return Text.of(builds).answeredBy(actor);
    }

    public ProjectWidgetBuilds(String projectName) {
        this.projectName = projectName;
    }

    private final String projectName;
}
