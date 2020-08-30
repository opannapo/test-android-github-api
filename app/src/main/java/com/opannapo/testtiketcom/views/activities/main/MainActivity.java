package com.opannapo.testtiketcom.views.activities.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.view.BaseActivity;
import com.opannapo.core.layer.application.presenter.view.BaseFragment;
import com.opannapo.core.layer.enterprise.utils.Log;
import com.opannapo.testtiketcom.R;
import com.opannapo.testtiketcom.views.adapter.ViewPagerAdapter;
import com.opannapo.testtiketcom.views.fragments.author.AuthorFragment;
import com.opannapo.testtiketcom.views.fragments.home.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.opannapo.testtiketcom.R2.string.appbar_scrolling_view_behavior;

/**
 * Created by napouser on 30,August,2020
 */
public class MainActivity extends BaseActivity<MainVM> {
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.txtProfileName)
    TextView txtProfileName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.vpPages)
    ViewPager vpPages;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.layContentToolbar)
    RelativeLayout layContentToolbar;

    HomeFragment homeFragment;
    AuthorFragment authorFragment;
    ViewPagerAdapter vpAdapter;
    BaseFragment<?> currentFragment;

    @Override
    public Class<MainVM> initVM() {
        return MainVM.class;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initialFragmentMember();
        initialNavigation();

        vm.liveAuthor.observe(this, liveAuthor);
        vm.liveLoadingState.observe(this, liveLoadingState);

        vm.getMyProfile(this);
    }

    private void initialNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navPageHome:
                    if (currentFragment == homeFragment) return false;
                    vpPages.setCurrentItem(0, true);
                    return true;
                case R.id.navPageProfile:
                    if (currentFragment == authorFragment) return false;
                    vpPages.setCurrentItem(1, true);
                    return true;
                default:
                    return false;
            }
        });
    }

    private void initialFragmentMember() {
        vpAdapter = new ViewPagerAdapter(this.getSupportFragmentManager(), appbar_scrolling_view_behavior);
        homeFragment = new HomeFragment();
        authorFragment = new AuthorFragment();

        vpAdapter.addFragment(0, homeFragment);
        vpAdapter.addFragment(1, authorFragment);
        vpPages.setAdapter(vpAdapter);
        vpPages.setOffscreenPageLimit(2);
        vpPages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("ViewPager onPageScrolled, position: " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("ViewPager onPageSelected, position: " + position);
                switch (position) {
                    case 0:
                        currentFragment = homeFragment;
                        bottomNavigation.getMenu().getItem(0).setChecked(true);
                        break;
                    case 1:
                        currentFragment = authorFragment;
                        bottomNavigation.getMenu().getItem(1).setChecked(true);
                        break;
                }
                currentFragment.onHiddenChanged(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("ViewPager onPageScrollStateChanged, state: " + state);
            }
        });
    }

    final Observer<User> liveAuthor = data -> {
        if (data == null) {
            txtProfileName.setText("No Profile");
            progressBar.setVisibility(View.GONE);
        } else {
            txtProfileName.setText(
                    stringInject(R.string.label_toolbar_profile_name,
                            "Author", data.getName())
            );
            progressBar.setVisibility(View.GONE);
            layContentToolbar.postDelayed(() -> layContentToolbar.setBackgroundColor(Color.parseColor("#000000")), 1000);
        }
    };

    final Observer<Integer> liveLoadingState = data -> progressBar.setVisibility(data == 1 ? View.VISIBLE : View.GONE);
}
