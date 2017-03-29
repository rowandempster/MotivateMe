package enghack.motivateme.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import enghack.motivateme.Adapters.CategoryPagerAdapter;
import enghack.motivateme.Adapters.FontPagerAdapter;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/28/17.
 */

public class CategoryPickerActivity extends FragmentActivity {
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
    }
}
