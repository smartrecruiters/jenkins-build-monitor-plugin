package net.serenitybdd.screenplay.jenkins.user_interface;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.jenkins.targets.Label;
import net.serenitybdd.screenplay.targets.Target;

public class ViewConfigurationPage {
    public static final Target Recurse_In_Subfolders = Label.called("Recurse in subfolders");
    public static final Target Use_Regular_Expression =
            Label.called("Use a regular expression to include jobs into the view");
    public static final Target Regular_Expression =
            Target.the("the 'Regular expression' field").located(By.name("includeRegex"));
    public static final Target Enable_Show_Badges = Label.called("Enable Show badges");
    public static final Target Display_Badges =
            Target.the("the 'Display badges' field").located(By.name("displayBadges"));
    public static final Target Display_Badges_From =
            Target.the("the 'Display badges from' field").located(By.name("displayBadgesFrom"));
    public static final Target Display_JUnit_Realtime_Progress =
            Target.the("the 'Display JUnit Realtime progress' option").locatedBy("#displayJUnitProgress");
}
