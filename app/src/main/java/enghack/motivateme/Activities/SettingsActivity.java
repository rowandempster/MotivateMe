package enghack.motivateme.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
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

import com.enrico.colorpicker.colorDialog;

import java.util.ArrayList;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import enghack.motivateme.BuildConfig;
import enghack.motivateme.CustomViews.SettingOptionCustomView;
import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableInterface;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.Models.QuoteDatabaseModel;
import enghack.motivateme.R;
import enghack.motivateme.Tasks.PullTweets.PullTweetsParams;
import enghack.motivateme.Tasks.PullTweets.PullTweetsTask;
import enghack.motivateme.Util.Constants;
import enghack.motivateme.Util.UserFontSize;
import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialogFragment;

public class SettingsActivity extends AppCompatActivity implements colorDialog.ColorSelectedListener {
    LinearLayout _root;
    SettingOptionCustomView _textColourSetting;
    SettingOptionCustomView _backgroundImageSetting;
    SettingOptionCustomView _timingSetting;
    SettingOptionCustomView _textSizeSetting;
    SettingOptionCustomView _fontSetting;
    SettingOptionCustomView _categorySetting;
    SettingOptionCustomView _getAQuote;
    ArrayList<SettingOptionCustomView> _optionsList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MotivateMeDbHelper.openHelper(this);
        PullTweetsTask.pullTweetsIfNeeded(MotivateMeDbHelper.getInstance().getReadableDatabase(), new PullTweetsParams(Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(0), Constants.TWEETS_TO_PULL_NORMAL_AMOUNT));

        setContentView(R.layout.activity_settings);
        setupViews();
        setupClicks();
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
        _root = (LinearLayout) findViewById(R.id.settings_root);
        _textColourSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your text colour", R.drawable.colour_wheel);
        _backgroundImageSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your background", R.drawable.background_image);
        _timingSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your quote refresh interval", R.drawable.timing);
        _textSizeSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your text size", R.drawable.textsize);
        _fontSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your font", R.drawable.fontchoice);
        _categorySetting = new SettingOptionCustomView(getApplicationContext(), "Choose your quote category", R.drawable.categoryicon);
        _getAQuote = new SettingOptionCustomView(getApplicationContext(), "Get a quote!", R.drawable.categoryicon);
        _optionsList.add(_textColourSetting);
        _optionsList.add(_backgroundImageSetting);
        _optionsList.add(_timingSetting);
        _optionsList.add(_textSizeSetting);
        _optionsList.add(_fontSetting);
        _optionsList.add(_categorySetting);
        if ("debug".equals(BuildConfig.BUILD_TYPE)) {
            _optionsList.add(_getAQuote);
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        for (int i = 0; i < _optionsList.size(); i++) {
            _optionsList.get(i).setLayoutParams(lp);
        }
        for (int i = 0; i < _optionsList.size(); i++) {
            _root.addView(_optionsList.get(i));
        }
    }

    private void setupClicks() {
        setupTextColourClick();
        setupBackgroundClick();
        setupTimingClick();
        setupTextSizeClick();
        setupFontClick();
        setupCategoryClick();
        setupNextQuoteClick();
    }

    private void setupNextQuoteClick() {
        _getAQuote.setOnClickListener(view -> {
            QuoteDatabaseModel quote = QuotesToUseTableInterface.getAndRemoveFirstQuoteAndPullMoreIfNeeded();
            String quoteText = quote == null ? "No more quotes :(" : quote.getText();
            Log.d("asdf", "Top quote : " + quoteText);
        });
    }

    private void setupCategoryClick() {
        _categorySetting.setOnClickListener(view -> {
            CharSequence categories[] = new String[]{"Inspirational Quotes", "Love Quotes", "Sport Quotes", "Book Quotes", "Uplifting Quotes", "Philosophy Quotes"};

            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
            builder.setTitle("Pick a Quote Category");
            builder.setItems(categories, (dialog, categoryChosen) -> {
                UserPreferencesTableInterface.writeQuoteCategory(categoryChosen);
                QuotesToUseTableInterface.clearTable();
                PullTweetsTask.pullTweetsNotSafe(new PullTweetsParams(Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(categoryChosen), Constants.TWEETS_TO_PULL_NORMAL_AMOUNT));
            });
            builder.show();
        });
    }

    private void setupFontClick() {
        _fontSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
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

    private void setupTextSizeClick() {
        _textSizeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNumberPicker();
            }
        });
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

    private void setupTimingClick() {
        _timingSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickerDialogFragment().show(getFragmentManager(), "dialog");
            }
        });
    }

    private void setupBackgroundClick() {
        _backgroundImageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
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

    private void setupTextColourClick() {
        _textColourSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorDialog.showColorPicker(SettingsActivity.this, 0);
            }
        });
    }

    @Override
    public void onColorSelection(DialogFragment dialogFragment, @ColorInt int selectedColor) {
        UserPreferencesTableInterface.writeTextColourAndRefreshWallpaper(selectedColor, SettingsActivity.this);
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
                UserPreferencesTableInterface.writeRefreshIntervalAndRefreshWallpaper(duration, SettingsActivity.this);
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
