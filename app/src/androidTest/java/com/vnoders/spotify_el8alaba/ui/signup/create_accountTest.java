package com.vnoders.spotify_el8alaba.ui.signup;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static androidx.test.espresso.action.ViewActions.typeText;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.rule.ActivityTestRule;
import com.vnoders.spotify_el8alaba.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;

public class create_accountTest {

    @Rule
    public ActivityTestRule<signup_email> activityTestRule=new ActivityTestRule<>(signup_email.class);
    @Before
    public void yourStetUpFragment(){
        FragmentActivity activity = (FragmentActivity) activityTestRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        create_account create_account = new create_account();
        transaction.replace(R.id.fragment_container,create_account, "CREATE_ACCOUNT");
        transaction.commit();
    }

    @Test
    public void testCreateAccountFragmentInView(){
        onView(withId(R.id.create_account_signup_fragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testWithNoNameInput(){
        onView(withId(R.id.name_create_account)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.create_button)).check(matches(not(isEnabled())));
    }

    @Test
    public void testWithNameInput(){
        onView(withId(R.id.name_create_account)).perform(typeText("example"),closeSoftKeyboard());
        onView(withId(R.id.create_button)).check(matches(isEnabled()));
    }
}