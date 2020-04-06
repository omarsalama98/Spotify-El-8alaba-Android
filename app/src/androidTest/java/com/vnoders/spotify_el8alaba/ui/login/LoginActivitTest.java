package com.vnoders.spotify_el8alaba.ui.login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import com.vnoders.spotify_el8alaba.R;
import com.vnoders.spotify_el8alaba.firstScreen;
import org.junit.Rule;
import org.junit.Test;

public class LoginActivitTest {

    @Test
    public void testLoginInView() {
        ActivityScenario activityScenario=ActivityScenario.launch(LoginActivit.class);
        onView(withId(R.id.login_form)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmailEditText(){
        ActivityScenario activityScenario=ActivityScenario.launch(LoginActivit.class);
        onView(withId(R.id.email_edit_text)).check(matches(isDisplayed()));
    }

    @Test
    public void testPasswordEditText(){
        ActivityScenario activityScenario=ActivityScenario.launch(LoginActivit.class);
        onView(withId(R.id.password_edit_text)).check(matches(isDisplayed()));
    }

    @Test
    public void testLogInButton(){
        ActivityScenario activityScenario=ActivityScenario.launch(LoginActivit.class);
        onView(withId(R.id.Login_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testForgetPasswordButton(){
        ActivityScenario activityScenario=ActivityScenario.launch(LoginActivit.class);
        onView(withId(R.id.forget_password)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmailTextView(){
        ActivityScenario activityScenario=ActivityScenario.launch(LoginActivit.class);
        onView(withId(R.id.email_text)).check(matches(withText(R.string.LoginFirstText)));

    }

    @Test
    public void testPasswordTextView(){
        ActivityScenario activityScenario=ActivityScenario.launch(LoginActivit.class);
        onView(withId(R.id.password_text)).check(matches(withText(R.string.LoginSecondText)));

    }
    @Test
    public void testForgotYourPasswordButtonNavigation(){
        ActivityScenario activityScenario=ActivityScenario.launch(LoginActivit.class);
        onView(withId(R.id.forget_password)).perform(click());
        onView(withId(R.id.forgot_password_activity)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.login_form)).check(matches(isDisplayed()));
    }

    @Test
    public void testBackButtonNavigation(){
        ActivityScenario activityScenario=ActivityScenario.launch(LoginActivit.class);
        onView(withId(R.id.back_button)).perform(click()) ;
        onView(withId(R.id.first_screen)).check(matches(isDisplayed()));
    }

    @Test
    public void testInvalidEmailEntry(){
        ActivityScenario activityScenario=ActivityScenario.launch(LoginActivit.class);
        onView(withId(R.id.email_edit_text)).perform(typeText("example"));
        onView(withId(R.id.password_edit_text)).perform(typeText("1234"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.Login_button)).perform(click());
        onView(withId(R.id.invalid_email)).check(matches(withText(containsString("Please enter a valid email or username"))));

    }




}