package com.vnoders.spotify_el8alaba.ui.signup;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static org.hamcrest.core.StringContains.containsString;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import com.vnoders.spotify_el8alaba.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class signup_email_fragmentTest {

    @Rule
    public ActivityTestRule<signup_email> activityTestRule=new ActivityTestRule<>(signup_email.class);

    @Before
    public void yourStetUpFragment(){
        activityTestRule.getActivity().getFragmentManager().beginTransaction();
    }
    @Test
    public void testEmailSignUpInView() {
        onView(withId(R.id.email_signup_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.next_button)).check(matches(isDisplayed()));

    }
    @Test
    public void testTypingCorrectEmail(){
        onView(withId(R.id.email_edit_text)).perform(typeText("example@spotify.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.password_signup_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testTypingWrongEmail(){
        onView(withId(R.id.email_edit_text)).perform(typeText("example"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.sign_up_email_status)).check(matches(withText(containsString("This email is invalid.Make sure it's written like"))));
        onView(withId(R.id.next_button)).check(matches(isDisplayed()));
    }

}