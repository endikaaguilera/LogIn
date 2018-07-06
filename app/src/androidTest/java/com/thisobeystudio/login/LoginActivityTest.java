package com.thisobeystudio.login;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;

import com.thisobeystudio.login.login.LoginActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    // This test runs two sign in attempts,
    // the first one fails to validate user credentials and
    // the second one validates user credentials successfully.

    @Rule
    public final ActivityTestRule<LoginActivity> mActivityTestRule
            = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void checkLoginError_MainActivityTest() {

        // set recycler view visible
        onView(withId(R.id.loginUsernameEditText))
                .check(matches(isDisplayed()))
                .check(matches(withEffectiveVisibility(VISIBLE)))
                .perform(requestFocus(true))
                .perform(typeTextIntoFocusedView("Mati"))
                .perform(requestFocus(false));

        onView(withId(R.id.loginPasswordEditText))
                .check(matches(isDisplayed()))
                .check(matches(withEffectiveVisibility(VISIBLE)))
                .perform(requestFocus(true))
                .perform(typeTextIntoFocusedView("1234567890"))
                .perform(requestFocus(false));

        onView(withId(R.id.signInBtn))
                .check(matches(withEffectiveVisibility(VISIBLE)))
                .perform(requestFocus(true))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.progressDialogCardView))
                .check(doesNotExist());

        onView(withId(R.id.loginUsernameEditText))
                .check(matches(withEffectiveVisibility(VISIBLE)))
                .check(matches(hasErrorText("❌ Unable to validate user. Try 'admin'")));

        onView(withId(R.id.loginUsernameEditText))
                .check(matches(isDisplayed()))
                .check(matches(withEffectiveVisibility(VISIBLE)))
                .perform(requestFocus(true))
                .perform(setText(null))
                .perform(requestFocus(false));

        onView(withId(R.id.loginPasswordEditText))
                .check(matches(isDisplayed()))
                .check(matches(withEffectiveVisibility(VISIBLE)))
                .perform(requestFocus(true))
                .perform(setText(null))
                .perform(requestFocus(false));

        onView(withId(R.id.loginUsernameEditText))
                .check(matches(isDisplayed()))
                .check(matches(withEffectiveVisibility(VISIBLE)))
                .perform(requestFocus(true))
                .perform(typeTextIntoFocusedView("admin"))
                .perform(requestFocus(false));

        onView(withId(R.id.loginPasswordEditText))
                .check(matches(isDisplayed()))
                .check(matches(withEffectiveVisibility(VISIBLE)))
                .perform(requestFocus(true))
                .perform(typeTextIntoFocusedView("HelloWorld"))
                .perform(requestFocus(false));

        onView(withId(R.id.signInBtn))
                .check(matches(isDisplayed()))
                .check(matches(withEffectiveVisibility(VISIBLE)))
                .perform(requestFocus(true))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.progressDialogCardView))
                .check(doesNotExist());

        // we are on MainActivity
        onView(withId(R.id.signInBtn))
                .check(doesNotExist());
    }

    public ViewAction requestFocus(final boolean focus) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled();
            }

            @Override
            public String getDescription() {
                return "Set text";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadUntilIdle();
                if (focus) view.requestFocus();
                else view.clearFocus();
                uiController.loopMainThreadUntilIdle();
            }
        };
    }

    public ViewAction setText(final String text) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isEnabled();
            }

            @Override
            public String getDescription() {
                return "Set text";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadUntilIdle();
                view.requestFocus();
                ((EditText) view).setText(text);
                uiController.loopMainThreadUntilIdle();
            }
        };
    }

}
