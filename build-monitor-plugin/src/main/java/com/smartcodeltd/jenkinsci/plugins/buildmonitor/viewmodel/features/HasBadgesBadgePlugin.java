package com.smartcodeltd.jenkinsci.plugins.buildmonitor.viewmodel.features;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jenkinsci.plugins.badge.action.BadgeAction;
import com.smartcodeltd.jenkinsci.plugins.buildmonitor.viewmodel.JobView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Daniel Beland
 */
public class HasBadgesBadgePlugin implements Feature<HasBadgesBadgePlugin.Badges> {

    private final com.smartcodeltd.jenkinsci.plugins.buildmonitor.Config config;
    private ActionFilter filter = new ActionFilter();
    private JobView job;

    public HasBadgesBadgePlugin(com.smartcodeltd.jenkinsci.plugins.buildmonitor.Config config) {
        this.config = config;
    }

    @Override
    public HasBadgesBadgePlugin of(JobView jobView) {
        this.job = jobView;

        return this;
    }

    @Override
    public Badges asJson() {
        Iterator<BadgeAction> badges = config.getDisplayBadgesFrom().from(job).allDetailsOf(BadgeAction.class).stream()
                .filter(filter)
                .iterator();

        return badges.hasNext()
                ? new Badges(badges)
                : null; // `null` because we don't want to serialise an empty object
    }

    public static class Badges {
        private final List<Badge> badges = new ArrayList<>();

        public Badges(Iterator<BadgeAction> badgeActions) {
            while (badgeActions.hasNext()) {
                badges.add(new Badge(badgeActions.next()));
            }
        }

        @JsonValue
        public List<Badge> value() {
            return List.copyOf(badges);
        }
    }

    public static class Badge {
        private final BadgeAction badge;
        private static final Pattern BADGE_STYLE_PATTERN =
                Pattern.compile("(border: (?<border>.*) solid (?<borderColor>.*);)?"
                        + "(background: (?<background>.*);)?" + "(color: (?<color>.*);)?");

        public Badge(BadgeAction badge) {
            this.badge = badge;
        }

        @JsonProperty
        public final String text() {
            return badge.getText();
        }

        @JsonProperty
        public final String color() {
            String style = badge.getStyle();
            Matcher matcher = BADGE_STYLE_PATTERN.matcher(style);
            return matcher.matches() ? matcher.group("color") : null;
        }

        @JsonProperty
        public final String background() {
            String style = badge.getStyle();
            Matcher matcher = BADGE_STYLE_PATTERN.matcher(style);
            return matcher.matches() ? matcher.group("background") : null;
        }

        @JsonProperty
        public final String border() {
            String style = badge.getStyle();
            Matcher matcher = BADGE_STYLE_PATTERN.matcher(style);
            return matcher.matches() ? matcher.group("border") : null;
        }

        @JsonProperty
        public final String borderColor() {
            String style = badge.getStyle();
            Matcher matcher = BADGE_STYLE_PATTERN.matcher(style);
            return matcher.matches() ? matcher.group("borderColor") : null;
        }
    }

    private static class ActionFilter implements Predicate<BadgeAction> {
        @Override
        public boolean test(BadgeAction action) {
            return action.getIcon() == null;
        }
    }
}
