package com.opannapo.testtiketcom.views.fragments.author;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.view.BaseFragment;
import com.opannapo.testtiketcom.R;
import com.opannapo.testtiketcom.views.activities.main.MainVM;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by napouser on 30,August,2020
 */
public class AuthorFragment extends BaseFragment<AuthorVM> {


    MainVM sharedVm;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtBio)
    TextView txtBio;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.layContent)
    LinearLayout layContent;

    @Override
    protected Class<AuthorVM> initVM() {
        return AuthorVM.class;
    }

    @Override
    protected int initLayout() {
        return R.layout.profile_fragment;
    }

    @Override
    protected void onCreated(Bundle savedInstanceState, View view) {
        ButterKnife.bind(this, view);

        sharedVm = new ViewModelProvider(requireActivity()).get(MainVM.class);
        sharedVm.liveAuthor.observe(this, liveOwner);
    }

    @Override
    public void onAction(String tag, Object... args) {

    }


    final Observer<User> liveOwner = data -> {
        if (data == null) return;
        layContent.setVisibility(View.VISIBLE);
        txtName.setText(data.getName());
        txtBio.setText(data.getBio());
        txtAddress.setText(data.getLocation());
        Glide.with(requireContext())
                .load(data.getAvatarUrl())
                .centerCrop()
                .error(R.drawable.baseline_aspect_ratio_black_18dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .circleCrop()
                .into(imgProfile);
    };
}
