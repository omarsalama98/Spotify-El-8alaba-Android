package com.vnoders.spotify_el8alaba.ui.login;

import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.pressBack;

import androidx.test.core.app.ActivityScenario;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Test;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;
import com.vnoders.spotify_el8alaba.R;
public class FirstScreenTest {
    @Test
    public void testFirstScreenInview() {
        ActivityScenario activityScenario=ActivityScenario.launch(FirstScreen.class);
        onView(withId(R.id.first_screen)).check(matches(isDisplayed()));

    }

    @Test
    public void testFirstScreenImage() {
        ActivityScenario activityScenario=ActivityScenario.launch(FirstScreen.class);
        onView(withId(R.id.first_screen_image)).check(matches(isDisplayed()));

    }

    @Test
    public void testFirstScreenSignUpButton() {
        ActivityScenario activityScenario=ActivityScenario.launch(FirstScreen.class);
        onView(withId(R.id.sign_up_button)).check(matches(isDisplayed()));

    }

    @Test
    public void testFirstScreenFacebookButton() {
        ActivityScenario activityScenario=ActivityScenario.launch(FirstScreen.class);
        onView(withId(R.id.facebook_button)).check(matches(isDisplayed()));

    }

    @Test
    public void testFirstScreenText() {
        ActivityScenario activityScenario=ActivityScenario.launch(FirstScreen.class);
        onView(withId(R.id.first_screen_text1)).check(matches(withText(R.string.firstPageTitle1)));
        onView(withId(R.id.first_screen_text2)).check(matches(withText(R.string.firstPageTitle2)));
    }

    @Test
    public void testLogInButtonNav(){
        ActivityScenario activityScenario=ActivityScenario.launch(FirstScreen.class);
        onView(withId(R.id.Login_button)).perform(click());
        onView(withId(R.id.login_form)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.first_screen)).check(matches(isDisplayed()));

    }

}