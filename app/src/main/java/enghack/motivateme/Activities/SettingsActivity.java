package enghack.motivateme.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;


import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enghack.motivateme.BuildConfig;
import enghack.motivateme.CustomViews.SettingOption;
import enghack.motivateme.Database.MotivateMeDbHelper;
import enghack.motivateme.Database.QuotesToUseTable.QuotesToUseTableInterface;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.Managers.MotivateMeWallpaperManager;
import enghack.motivateme.Managers.SchedulingManager;
import enghack.motivateme.Models.FontPickerResult;
import enghack.motivateme.R;
import enghack.motivateme.Tasks.PullTweets.PullTweetsCallback;
import enghack.motivateme.Tasks.PullTweets.PullTweetsParams;
import enghack.motivateme.Tasks.PullTweets.PullTweetsAndPutInDbTask;
import enghack.motivateme.Util.Constants;
import enghack.motivateme.Util.DisplayUtils;

public class SettingsActivity extends Activity {
    public static final int CHOOSE_BACKGROUND_REQUEST_CODE = 99;
    public static final int CHOOSE_TEXT_COLOUR_REQUEST_CODE = 101;
    public static final int CHOOSE_REFRESH_INTERVAL_REQUEST_CODE = 102;
    public static final int CHOOSE_FONT_REQUEST_CODE = 103;
    public static final int CHOOSE_CATEGORY_REQUEST_CODE = 104;


    @BindView(R.id.settings_option_pick_new_quote)
    SettingOption _getAQuoteSetting;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MotivateMeDbHelper.openHelper(this);
        PullTweetsAndPutInDbTask.pullTweetsIfNeeded(MotivateMeDbHelper.getInstance().getReadableDatabase(), new PullTweetsParams(Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(0), Constants.TWEETS_TO_PULL_NORMAL_AMOUNT));

        setContentView(R.layout.settings_activity_layout);
        ButterKnife.bind(this);
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

    @OnClick(R.id.settings_option_pick_new_quote)
    public void setupNextQuoteClick() {
        MotivateMeWallpaperManager.updateWallPaperWithNewQuoteAndAddToUsedIfBackgroundIsSet(this);
    }

    @OnClick(R.id.settings_option_pick_category)
    public void setupCategoryClick() {
        Intent intent = new Intent(this, CategoryPickerActivity.class);
        startActivityForResult(intent, CHOOSE_CATEGORY_REQUEST_CODE);
    }

    @OnClick(R.id.settings_option_pick_font)
    public void setupFontClick() {
        Intent intent = new Intent(this, FontPickerActivity.class);
        startActivityForResult(intent, CHOOSE_FONT_REQUEST_CODE);
    }

    @OnClick(R.id.settings_option_pick_refresh)
    public void setupTimingClick() {
        Intent intent = new Intent(this, RefreshIntervalPickerActivity.class);
        startActivityForResult(intent, CHOOSE_REFRESH_INTERVAL_REQUEST_CODE);
    }

    @OnClick(R.id.settings_option_pick_background)
    public void setupBackgroundClick() {
        pickImage();
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_BACKGROUND_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_BACKGROUND_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            DisplayUtils.WidthAndHeight dims = DisplayUtils.getWidthAndHeight(this);

            CropImage.activity(data.getData())
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(dims.width, dims.height)
                    .start(this);
        }
        else if(requestCode == CHOOSE_TEXT_COLOUR_REQUEST_CODE && resultCode == RESULT_OK){
            int result = data.getIntExtra(ColourPickerActivity.COLOUR_PICKED_EXTRA, 0);
            if(result != 0) {
                UserPreferencesTableInterface.writeTextColourAndRefreshWallpaper(result, this);
            }
        }
        else if(requestCode == CHOOSE_REFRESH_INTERVAL_REQUEST_CODE && resultCode == RESULT_OK){
            long result = data.getLongExtra(RefreshIntervalPickerActivity.TIME_PICKED_EXTRA, -1);
            if(result != -1) {
                UserPreferencesTableInterface.writeRefreshIntervalAndRefreshWallpaper(result, this);
            }
        }
        else if(requestCode == CHOOSE_FONT_REQUEST_CODE && resultCode == RESULT_OK){
            FontPickerResult result = (FontPickerResult) data.getSerializableExtra(FontPickerActivity.FONT_PICKED_EXTRA);
            if(result != null) {
                UserPreferencesTableInterface.writeTextFont(result.getFont());
                UserPreferencesTableInterface.writeTextSize(result.getSize());
                UserPreferencesTableInterface.writeTextStyle(result.getStyle());
                SchedulingManager.settingChanged(this);
            }
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                UserPreferencesTableInterface.writeBackgroundUriAndRefreshWallpaper(resultUri.toString(), SettingsActivity.this);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        else if(requestCode == CHOOSE_CATEGORY_REQUEST_CODE && resultCode == RESULT_OK){
            int result = data.getIntExtra(CategoryPickerActivity.ACCOUNT_SELECTED_KEY, -1);
            if(result != -1) {
                SchedulingManager.stopJobs(SettingsActivity.this);
                UserPreferencesTableInterface.writeQuoteCategory(result);
                QuotesToUseTableInterface.clearTable();
                PullTweetsCallback callback = new PullTweetsCallback() {
                    @Override
                    public void start() {
                    }

                    @Override
                    public void done() {
                        MotivateMeWallpaperManager.updateWallPaperWithNewQuoteAndAddToUsedIfBackgroundIsSet(SettingsActivity.this);
                        SchedulingManager.cancelJobsAndStart(SettingsActivity.this);
                    }
                };
                PullTweetsAndPutInDbTask.attachProgressDialogToNextPull(new ProgressDialog(this));
                PullTweetsAndPutInDbTask.pullTweetsNotSafe(new PullTweetsParams(Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(result), Constants.TWEETS_TO_PULL_NORMAL_AMOUNT), callback);
            }
        }
    }

    @OnClick(R.id.settings_option_pick_colour)
    public void setupTextColourClick() {
        startColourPickerActivity();
    }

    private void startColourPickerActivity() {
        Intent intent = new Intent(this, ColourPickerActivity.class);
        startActivityForResult(intent, CHOOSE_TEXT_COLOUR_REQUEST_CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MotivateMeDbHelper.closeHelper();
    }
}
