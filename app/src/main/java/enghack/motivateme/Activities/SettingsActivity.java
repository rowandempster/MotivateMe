package enghack.motivateme.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enghack.motivateme.BuildConfig;
import enghack.motivateme.CustomViews.ColourPickerFragment;
import enghack.motivateme.CustomViews.SettingOption;
import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableInterface;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.Managers.MotivateMeWallpaperManager;
import enghack.motivateme.Managers.SchedulingManager;
import enghack.motivateme.Models.QuoteDatabaseModel;
import enghack.motivateme.R;
import enghack.motivateme.Tasks.PullTweets.PullTweetsCallback;
import enghack.motivateme.Tasks.PullTweets.PullTweetsParams;
import enghack.motivateme.Tasks.PullTweets.PullTweetsTask;
import enghack.motivateme.Util.Constants;
import enghack.motivateme.Util.UserFontSize;
import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialogFragment;

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.settings_option_pick_new_quote)
    SettingOption _getAQuoteSetting;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MotivateMeDbHelper.openHelper(this);
        PullTweetsTask.pullTweetsIfNeeded(MotivateMeDbHelper.getInstance().getReadableDatabase(), new PullTweetsParams(Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(0), Constants.TWEETS_TO_PULL_NORMAL_AMOUNT));

        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);
        setupViews();
        setupPermissions();

    }

    private void setupPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    33);
            return;
        }
    }

    private void setupViews() {
        if (!("debug".equals(BuildConfig.BUILD_TYPE))) {
            _getAQuoteSetting.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.settings_option_pick_new_quote)
    public void setupNextQuoteClick() {
        QuoteDatabaseModel quote = QuotesToUseTableInterface.getAndRemoveFirstQuoteAndPullMoreIfNeeded();
        String quoteText = quote == null ? "No more quotes :(" : quote.getText();
        Log.d("asdf", "Top quote : " + quoteText);
    }

    @OnClick(R.id.settings_option_pick_category)
    public void setupCategoryClick() {
        CharSequence categories[] = new String[]{"Inspirational Quotes", "Love Quotes", "Sport Quotes", "Book Quotes", "Uplifting Quotes", "Philosophy Quotes"};

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle("Pick a Quote Category");
        builder.setItems(categories, (dialog, categoryChosen) -> {
            SchedulingManager.stopJobs(SettingsActivity.this);
            UserPreferencesTableInterface.writeQuoteCategory(categoryChosen);
            QuotesToUseTableInterface.clearTable();
            ProgressDialog gettingTweetsProgress = new ProgressDialog(SettingsActivity.this);
            gettingTweetsProgress.setMessage("Getting your new quotes...");
            PullTweetsCallback callback = new PullTweetsCallback() {
                @Override
                public void start() {
                    gettingTweetsProgress.show();
                }

                @Override
                public void done() {
                    gettingTweetsProgress.dismiss();
                    MotivateMeWallpaperManager.updateWallPaperWithNewQuoteAndAddToUsedIfBackgroundIsSet(SettingsActivity.this);
                    SchedulingManager.cancelJobsAndStart(SettingsActivity.this);
                }
            };
            PullTweetsTask.pullTweetsNotSafe(new PullTweetsParams(Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(categoryChosen), Constants.TWEETS_TO_PULL_NORMAL_AMOUNT), callback);
        });
        builder.show();
    }

    @OnClick(R.id.settings_option_pick_font)
    public void setupFontClick() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(SettingsActivity.this);
        alert.setTitle("Select you Quote Font");
        LinearLayout layout = new LinearLayout(SettingsActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);


        alert.setView(layout);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();
        setupFontBox(layout, "Americana Quotes", "fonts/americana.ttf", alertDialog);
        setupFontBox(layout, "Block Quotes", "fonts/block.ttf", alertDialog);
        setupFontBox(layout, "Serif Quotes", "fonts/serif.ttf", alertDialog);
        setupFontBox(layout, "Dancing Quotes", "fonts/dancing.ttf", alertDialog);
        setupFontBox(layout, "Typerwriter Quotes", "fonts/typewriter.ttf", alertDialog);
        setupFontBox(layout, "Caviar Quotes", "fonts/caviar.ttf", alertDialog);
    }

    private void setupFontBox(LinearLayout layout, String name, final String font, final AlertDialog alertDialog) {
        TextView tv = new TextView(SettingsActivity.this);
        tv.setPadding(0, 50, 0, 50);
        tv.setText(name);
        tv.setTypeface(Typeface.createFromAsset(SettingsActivity.this.getAssets(), font));
        tv.setTextSize(30);
        if (font.equals("fonts/americana.ttf")) {
            tv.setBackgroundDrawable(getDrawable(R.drawable.border));
        } else {
            tv.setBackgroundDrawable(getDrawable(R.drawable.border_bottom_only));
        }
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setGravity(Gravity.CENTER);
        layout.addView(tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                DisplayMetrics dm = new DisplayMetrics();
                SettingsActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);

                final int oldMaxFontSize = UserFontSize.getMaxFontSize(dm.widthPixels,
                        dm.heightPixels, SettingsActivity.this.getApplicationContext());

                UserPreferencesTableInterface.writeTextFontAndRefreshWallpaper(font, SettingsActivity.this);
                final int maxFontSize = UserFontSize.getMaxFontSize(dm.widthPixels,
                        dm.heightPixels, SettingsActivity.this.getApplicationContext());
                if (oldMaxFontSize > maxFontSize) {
                    UserPreferencesTableInterface.writeTextSizeAndRefreshWallpaper(maxFontSize, SettingsActivity.this);
                }

            }
        });
    }

    @OnClick(R.id.settings_option_pick_text_size)
    public void setupTextSizeClick() {
        showNumberPicker();
    }

    public void showNumberPicker() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        final int maxFontSize = UserFontSize.getMaxFontSize(dm.widthPixels,
                dm.heightPixels, this.getApplicationContext());

        final MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(this)
                .minValue(50)
                .maxValue(maxFontSize)
                .defaultValue(18)
                .backgroundColor(Color.WHITE)
                .separatorColor(Color.TRANSPARENT)
                .textColor(Color.BLACK)
                .textSize(20)
                .enableFocusability(false)
                .wrapSelectorWheel(true)
                .build();
        new AlertDialog.Builder(this)
                .setTitle("Pick a Text Size")
                .setView(numberPicker)
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int fontSize = numberPicker.getValue();
                        UserPreferencesTableInterface.writeTextSizeAndRefreshWallpaper(fontSize, SettingsActivity.this);
                    }
                })
                .show();

    }

    @OnClick(R.id.settings_option_pick_refresh)
    public void setupTimingClick() {
        new PickerDialogFragment().show(getFragmentManager(), "dialog");
    }

    @OnClick(R.id.settings_option_pick_background)
    public void setupBackgroundClick() {
        pickImage();
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 99);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            UserPreferencesTableInterface.writeBackgroundUriAndRefreshWallpaper(data.getData().toString(), SettingsActivity.this);

        }
    }

    @OnClick(R.id.settings_option_pick_colour)
    public void setupTextColourClick() {
        startSettingFragment();
    }

    private void startSettingFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        ColourPickerFragment fragment = new ColourPickerFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private class PickerDialogFragment extends TimeDurationPickerDialogFragment {

        @Override
        protected long getInitialDuration() {
            return 24 * 60 * 60 * 1000;
        }


        @Override
        protected int setTimeUnits() {
            return TimeDurationPicker.HH_MM_SS;
        }


        @Override
        public void onDurationSet(TimeDurationPicker view, long duration) {
            if (duration > 5000) {
                UserPreferencesTableInterface.writeRefreshInterval(duration);
                SchedulingManager.cancelJobsAndStart(SettingsActivity.this, duration);
            } else {
                Toast.makeText(SettingsActivity.this, "Please enter more than 5 seconds", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MotivateMeDbHelper.closeHelper();
    }
}
