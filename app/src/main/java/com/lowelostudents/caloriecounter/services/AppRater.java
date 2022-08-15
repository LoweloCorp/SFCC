package com.lowelostudents.caloriecounter.services;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.lowelostudents.caloriecounter.R;

public class AppRater {
    private final static String APP_TITLE = "CalorieCounter";// App Name
    private final static String APP_PNAME = "com.lowelostudents.caloriecounter";// Package Name

    private final static int DAYS_UNTIL_PROMPT = 3;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 3;//Min number of launches

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }

        editor.commit();
    }

    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("Rate " + APP_TITLE);

        Resources.Theme theme = mContext.getTheme();
        TypedValue colorPrimaryVariant = new TypedValue();
        theme.resolveAttribute(R.attr.colorPrimaryVariant, colorPrimaryVariant, true);

        TypedValue colorAccent = new TypedValue();
        theme.resolveAttribute(R.attr.colorAccent, colorAccent, true);


        TypedValue colorOnPrimary = new TypedValue();
        theme.resolveAttribute(R.attr.colorOnPrimary, colorOnPrimary, true);

        TypedValue editTextColor = new TypedValue();
        theme.resolveAttribute(R.attr.editTextColor, editTextColor, true);

        LinearLayout ll = new LinearLayout(mContext);
        ll.setPadding(50, 50,50 ,0);
        ll.setMinimumWidth(900);
        ll.setBackgroundColor(mContext.getResources().getColor(colorAccent.resourceId, theme));
        ll.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(0, 0 ,0 ,100);
        TextView tv = new TextView(mContext);
        tv.setLayoutParams(layoutParams1);
        tv.setBackgroundTintList(mContext.getColorStateList(editTextColor.resourceId));
        tv.setTextColor(mContext.getResources().getColor(editTextColor.resourceId, theme));
        tv.setText("If you enjoy " + APP_TITLE + ", consider rating it! Thanks for your support!");
        tv.setWidth(240);
        tv.setPadding(4, 0, 4, 30);
        ll.addView(tv);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 25 ,0 ,0);

        Button b1 = new Button(mContext);
        b1.setText("Rate " + APP_TITLE);
        b1.setPadding(15, 15, 15 ,15 );
        b1.setTextColor(mContext.getResources().getColor(colorOnPrimary.resourceId, mContext.getTheme()));
        b1.setBackgroundColor(mContext.getResources().getColor(colorPrimaryVariant.resourceId, theme));
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                dialog.dismiss();
            }
        });
        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText("Remind me later");
        b2.setPadding(15, 15, 15 ,15 );
        b2.setLayoutParams(layoutParams);
        b2.setTextColor(mContext.getResources().getColor(colorPrimaryVariant.resourceId, mContext.getTheme()));
        b2.setBackgroundColor(mContext.getResources().getColor(colorAccent.resourceId, theme));
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ll.addView(b2);

        Button b3 = new Button(mContext);
        b3.setText("No, thanks");
        b3.setLayoutParams(layoutParams);
        b3.setPadding(15, 15, 15 ,15 );

        b3.setTextColor(mContext.getResources().getColor(colorPrimaryVariant.resourceId, mContext.getTheme()));
        b3.setBackgroundColor(mContext.getResources().getColor(colorAccent.resourceId, theme));
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });
        ll.addView(b3);

        b2.setBackgroundResource(android.R.color.transparent);
        b3.setBackgroundResource(android.R.color.transparent);

        dialog.setContentView(ll);
        dialog.show();
    }
}