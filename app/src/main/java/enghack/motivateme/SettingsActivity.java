package enghack.motivateme;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enrico.colorpicker.colorDialog;

import java.util.ArrayList;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import enghack.motivateme.CustomViews.SettingOptionCustomView;
import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialogFragment;

public class SettingsActivity extends AppCompatActivity implements colorDialog.ColorSelectedListener {
    LinearLayout _root;
    SettingOptionCustomView _textColourSetting;
    SettingOptionCustomView _backgroundImageSetting;
    SettingOptionCustomView _timingSetting;
    SettingOptionCustomView _textSizeSetting;
    SettingOptionCustomView _textLocationSetting;
    SettingOptionCustomView _fontSetting;
    SettingOptionCustomView _categorySetting;
    ArrayList<SettingOptionCustomView> _optionsList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        _root = (LinearLayout) findViewById(R.id.settings_root);
        _textColourSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your text colour", R.drawable.colour_wheel);
        _backgroundImageSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your background", R.drawable.background_image);
        _timingSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your quote refresh interval", R.drawable.timing);
        _textSizeSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your text size", R.drawable.textsize);
        _textLocationSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your text location", R.drawable.textplacement);
        _fontSetting = new SettingOptionCustomView(getApplicationContext(), "Choose your font", R.drawable.fontchoice);
        _categorySetting = new SettingOptionCustomView(getApplicationContext(), "Choose your quote category", R.drawable.categoryicon);
        _optionsList.add(_textColourSetting);
        _optionsList.add(_backgroundImageSetting);
        _optionsList.add(_timingSetting);
        _optionsList.add(_textSizeSetting);
        _optionsList.add(_textLocationSetting);
        _optionsList.add(_fontSetting);
        _optionsList.add(_categorySetting);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        for (int i = 0; i < _optionsList.size(); i++) {
            _optionsList.get(i).setLayoutParams(lp);
        }
        for (int i = 0; i < _optionsList.size(); i++) {
            _root.addView(_optionsList.get(i));
        }

        setupClicks();
    }

    private void setupClicks() {
        setupTextColourClick();
        setupBackgroundClick();
        setupTimingClick();
        setupTextSizeClick();
        setupTextLocationClick();
        setupFontClick();
        setupCatClick();
    }

    private void setupCatClick() {
        _categorySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence colors[] = new CharSequence[]{"Inspirational Quotes", "Love Quotes", "Sport Quotes", "Book Quotes", "Uplifting Quotes", "Philosophy Quotes"};

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Pick a Quote Category");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SettingsActivity.this.getSharedPreferences(Constants.MASTER_SP_KEY, 0).edit().putInt(Constants.QUOTE_CATEGORY_SP_KEY, which);
                    }
                });
                builder.show();
            }
        });
    }

    private void setupTextLocationClick() {
        _textLocationSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence colors[] = new CharSequence[]{"Top", "Middle", "Bottom"};

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Pick a Quote Position");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SettingsActivity.this.getSharedPreferences(Constants.MASTER_SP_KEY, 0).edit().putInt(Constants.TEXT_POSITION_SP_KEY, which);
                    }
                });
                builder.show();
            }
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
        if(font.equals("fonts/americana.ttf")){
            tv.setBackgroundDrawable(getDrawable(R.drawable.border));
        }
        else {
            tv.setBackgroundDrawable(getDrawable(R.drawable.border_bottom_only));
        }
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setGravity(Gravity.CENTER);
        layout.addView(tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                SettingsActivity.this.getSharedPreferences(Constants.MASTER_SP_KEY, 0).edit().putString(Constants.TEXT_FONT_SP_KEY, font);
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
        final MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(this)
                .minValue(12)
                .maxValue(100)
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
                        SettingsActivity.this.getSharedPreferences(Constants.MASTER_SP_KEY, 0).edit().putInt(Constants.TEXT_SIZE_SP_KEY, fontSize);
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
            SharedPreferences sp = getSharedPreferences(Constants.MASTER_SP_KEY, 0);
            sp.edit().putString(Constants.BACKGROUND_URI_SP_KEY, data.getData().toString()).commit();
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
        SharedPreferences sp = getSharedPreferences(Constants.MASTER_SP_KEY, 0);
        sp.edit().putInt(Constants.TEXT_COLOR_SP_KEY, selectedColor);
    }


    private class PickerDialogFragment extends TimeDurationPickerDialogFragment {

        @Override
        protected long getInitialDuration() {
            return 15 * 60 * 1000;
        }


        @Override
        protected int setTimeUnits() {
            return TimeDurationPicker.HH_MM;
        }


        @Override
        public void onDurationSet(TimeDurationPicker view, long duration) {
            SettingsActivity.this.getSharedPreferences(Constants.MASTER_SP_KEY, 0).edit().putLong(Constants.REFRESH_INTERVAL_SP_KEY, duration).commit();
        }
    }
}
