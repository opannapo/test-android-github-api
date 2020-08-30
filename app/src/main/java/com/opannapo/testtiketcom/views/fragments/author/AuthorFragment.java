package com.opannapo.testtiketcom.views.fragments.author;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.view.BaseFragment;
import com.opannapo.testtiketcom.R;
import com.opannapo.testtiketcom.views.activities.main.MainVM;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by napouser on 30,August,2020
 */
public class AuthorFragment extends BaseFragment<AuthorVM> {
    @BindView(R.id.btnCreateProfile)
    Button btnCreateProfile;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtFirstName)
    EditText edtFirstName;
    @BindView(R.id.edtLastName)
    EditText edtLastName;
    @BindView(R.id.btnSaveProfile)
    Button btnSaveProfile;
    @BindView(R.id.layContent)
    LinearLayout layContent;

    MainVM sharedVm;

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
        vm.liveAuthor.observe(this, liveOwner);
        vm.getAuthorProfile(requireContext());
    }

    @Override
    public void onAction(String tag, Object... args) {

    }

    @OnClick({R.id.btnCreateProfile, R.id.btnSaveProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCreateProfile:
                btnCreateProfile.setVisibility(View.GONE);
                layContent.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSaveProfile:
                if (vm.liveAuthor.getValue() != null) {
                    User author = vm.liveAuthor.getValue();
                    /*author.setEmail(edtEmail.getText().toString());
                    author.setFirstName(edtFirstName.getText().toString());
                    author.setLastName(edtLastName.getText().toString());
                    vm.updateMyProfile(requireContext(), author);
                    */

                } else {
                    User author = new User();
                    /*owner.setEmail(edtEmail.getText().toString());
                    owner.setFirstName(edtFirstName.getText().toString());
                    owner.setLastName(edtLastName.getText().toString());
                    vm.createMyProfile(requireContext(), owner);*/
                }
                break;
        }
    }


    final Observer<User> liveOwner = data -> {
        if (data == null) {
            btnCreateProfile.setVisibility(View.VISIBLE);
            layContent.setVisibility(View.GONE);
        } else {
            btnCreateProfile.setVisibility(View.GONE);
            layContent.setVisibility(View.VISIBLE);
            btnSaveProfile.setText("Update Profile");
            edtEmail.setText(data.getEmail());
            /*edtFirstName.setText(data.getFirstName());
            edtLastName.setText(data.getLastName());*/

            sharedVm.liveAuthor.postValue(data);
        }
    };
}
