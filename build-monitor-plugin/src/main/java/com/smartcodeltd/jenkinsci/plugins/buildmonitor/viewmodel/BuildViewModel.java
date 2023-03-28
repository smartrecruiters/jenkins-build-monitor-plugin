package com.smartcodeltd.jenkinsci.plugins.buildmonitor.viewmodel;

import com.smartcodeltd.jenkinsci.plugins.buildmonitor.viewmodel.duration.Duration;
import hudson.model.Action;
import hudson.model.Result;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BuildViewModel {
    String name();
    String url();
    Result result();

    boolean isRunning();
    Duration elapsedTime();
    Duration timeElapsedSince();
    Duration duration();
    Duration estimatedDuration();
    int progress();
    String description();

    boolean isPipeline();
    List<String> pipelineStages();

    boolean hasPreviousBuild();
    BuildViewModel previousBuild();

    Set<String> culprits();
    Set<String> committers();

    <A extends Action> Optional<A> detailsOf(Class<A> jenkinsAction);
    <A extends Action> List<A> allDetailsOf(Class<A> jenkinsAction);
}
