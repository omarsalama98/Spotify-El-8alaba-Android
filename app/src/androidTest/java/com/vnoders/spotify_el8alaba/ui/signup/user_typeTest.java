package com.vnoders.spotify_el8alaba.ui.signup;

import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

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

public class user_typeTest {

    @Rule
    public ActivityTestRule<SignUpEmail> activityTestRule=new ActivityTestRule<>(SignUpEmail.class);
    @Before
    public void yourStetUpFragment(){
        FragmentActivity activity = (FragmentActivity) activityTestRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        UserType userType = new UserType();
        transaction.replace(R.id.fragment_container,userType, "USER_TYPE");
        transaction.commit();
    }

    @Test
    public void testGenderFragmentInView(){
        onView(withId(R.id.usertype_signup_fragment)).check(matches(isDisplayed()));
    }

}