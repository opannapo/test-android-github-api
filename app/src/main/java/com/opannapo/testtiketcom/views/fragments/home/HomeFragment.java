package com.opannapo.testtiketcom.views.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.view.BaseFragment;
import com.opannapo.core.layer.enterprise.utils.Log;
import com.opannapo.testtiketcom.R;
import com.opannapo.testtiketcom.views.adapter.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by napouser on 30,August,2020
 */
public class HomeFragment extends BaseFragment<HomeVM> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    UsersAdapter adapter;
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @Override
    protected Class<HomeVM> initVM() {
        return HomeVM.class;
    }

    @Override
    protected int initLayout() {
        return R.layout.home_fragment;
    }

    @Override
    protected void onCreated(Bundle savedInstanceState, View view) {
        ButterKnife.bind(this, view);
        vm.liveUsers.observe(this, liveUsers);
        vm.liveLoadingState.observe(this, liveLoadingState);

        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (v.getText().toString().isEmpty()) return true;
                vm.findUsers(requireContext(), v.getText().toString());
                return true;
            }
            return false;
        });

        adapter = new UsersAdapter(requireContext(), new ArrayList<>(), (i, user) -> {
            Toast.makeText(requireContext(), user.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAction(String tag, Object... args) {

    }

    final Observer<List<User>> liveUsers = data -> {
        new Thread(() -> {
            for (User user : data) {
                try {
                    Log.d("live liveUsers user " + user);
                    requireActivity().runOnUiThread(() -> adapter.notifyAddMoreData(user));
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DUMMY_PROCESSING_THREAD").start();
    };

    final Observer<Integer> liveLoadingState = data -> {
        Log.d("live liveLoadingState " + data);
    };
}
