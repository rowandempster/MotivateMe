package enghack.motivateme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enghack.motivateme.Adapters.CategoryPagerAdapter;
import enghack.motivateme.Adapters.FontPagerAdapter;
import enghack.motivateme.R;
import enghack.motivateme.Util.Constants;

/**
 * Created by rowandempster on 3/28/17.
 */


public class CategoryPickerActivity extends FragmentActivity {
    public static final String ACCOUNT_SELECTED_KEY = "account_selected";
    @BindView(R.id.category_picker_option_tabs)
    SmartTabLayout _optionTabs;
    @BindView(R.id.category_picker_view_pager)
    ViewPager _viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catgory_picker_layout);
        ButterKnife.bind(this);
        _viewPager.setAdapter(new CategoryPagerAdapter(getSupportFragmentManager(), this));
        _optionTabs.setViewPager(_viewPager);
        _viewPager.setOffscreenPageLimit(5);
    }

    @OnClick(R.id.back_button)
    public void goBack(View v){
        finish();
    }

    @OnClick(R.id.confirm_button)
    public void comfirm(View v){
        Intent data = new Intent();
        data.putExtra(ACCOUNT_SELECTED_KEY, _viewPager.getCurrentItem());
        setResult(RESULT_OK, data);
        finish();
    }

}
