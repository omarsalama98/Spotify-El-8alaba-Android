package com.vnoders.spotify_el8alaba.ui.signup;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import static org.hamcrest.core.StringContains.containsString;
import androidx.test.rule.ActivityTestRule;
import com.vnoders.spotify_el8alaba.R;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class signup_passwordTest {

    @Rule
    public ActivityTestRule<signup_email> activityTestRule=new ActivityTestRule<>(signup_email.class);
    @Before
    public void yourStetUpFragment(){
        FragmentActivity activity = (FragmentActivity) activityTestRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        signup_password signup_password_fragment = new signup_password();
        transaction.replace(R.id.fragment_container,signup_password_fragment, "SIGNUP_PASSWORD_FRAGMENT");
        transaction.commit();
    }
    @Test
    public void testPasswordFragmentInView(){
        onView(withId(R.id.password_signup_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testWeekPassword(){
    onView(withId(R.id.password_edit_text)).perform(typeText("12345678"),closeSoftKeyboard());
    onView(withId(R.id.next_button)).check(matches(not(isEnabled())));
    onView(withId(R.id.password_status)).check(matches(withText(containsString("This password is too weak. Try including numbers,"))));
    }

    @Test
    public void testShortPassword(){
        onView(withId(R.id.password_edit_text)).perform(typeText("123456"),closeSoftKeyboard());
        onView(withId(R.id.next_button)).check(matches(not(isEnabled())));
        onView(withId(R.id.password_status)).check(matches(withText(containsString("Use at least 8 characters."))));
    }

    @Test
    public void testStrongPassword(){
        onView(withId(R.id.password_edit_text)).perform(typeText("W12w&hhh"),closeSoftKeyboard());
        onView(withId(R.id.next_button)).check(matches((isEnabled())));
        onView(withId(R.id.password_status)).check(matches(withText(containsString(""))));
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.birthdate_signup_fragment)).check(matches(isDisplayed()));
    }

}