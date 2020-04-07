package com.vnoders.spotify_el8alaba.ui.login;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;
import androidx.test.core.app.ActivityScenario;
import com.vnoders.spotify_el8alaba.R;
import org.junit.Test;


public class forget_passwordTest {

    @Test
    public void testForgetPasswordInView(){
        ActivityScenario activityScenario=ActivityScenario.launch(forget_password.class);
        onView(withId(R.id.forgot_password_activity)).check(matches(isDisplayed()));

    }

    @Test
    public void testBackButtonBehavior(){
        ActivityScenario activityScenario=ActivityScenario.launch(forget_password.class);
        onView(withId(R.id.back_button)).perform(click());
        onView(withId(R.id.login_form)).check(matches(isDisplayed()));
    }

    @Test
    public void testGetLinkButtonDisable(){
        ActivityScenario activityScenario=ActivityScenario.launch(forget_password.class);
        onView(withId(R.id.email_edit_text)).perform(typeText(""));
        onView(withId(R.id.get_link)).check(matches(not(isEnabled())));
    }

    @Test
    public void testGetLinkButtonEnable(){
        ActivityScenario activityScenario=ActivityScenario.launch(forget_password.class);
        onView(withId(R.id.email_edit_text)).perform(typeText("example"));
        onView(withId(R.id.get_link)).check(matches(isEnabled()));
    }

    @Test
    public void testForgetPasswordStatusText(){
        ActivityScenario activityScenario=ActivityScenario.launch(forget_password.class);
        onView(withId(R.id.forgot_password_status)).check(matches((withText(containsString("We'll send a link to your email that will log you in")))));
    }
}