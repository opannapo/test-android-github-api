package com.opannapo.testtiketcom.views.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.view.BaseFragment;
import com.opannapo.core.layer.enterprise.utils.Log;
import com.opannapo.testtiketcom.R;
import com.opannapo.testtiketcom.etc.Constant.ErrorType;
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
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.imgNoResult)
    ImageView imgNoResult;

    UsersAdapter adapter;
    private String queryMatch;

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
        vm.liveErrorType.observe(this, liveErrorType);


        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if (v.getText().toString().isEmpty()) return true;
                queryMatch = v.getText().toString();
                vm.findUsers(requireContext(), queryMatch);
                return true;
            }
            return false;
        });

        adapter = new UsersAdapter(requireContext(), new ArrayList<>(), (i, user) -> {
            Toast.makeText(requireContext(), user.getLogin(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (adapter.getItemCount() == 1) return;
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    if (!adapter.isNoMoreData()) vm.loadMore(requireContext());
                }
            }
        });
    }

    @Override
    public void onAction(String tag, Object... args) {

    }

    final Observer<List<User>> liveUsers = data -> {
        imgNoResult.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if (data == null) {
            requireActivity().runOnUiThread(() -> adapter.notifyToReset());
            return;
        }
        requireActivity().runOnUiThread(() -> adapter.notifyAddMoreData(data, queryMatch));
    };

    final Observer<Integer> liveLoadingState = data -> {
        Log.d("live liveLoadingState " + data);
    };

    final Observer<Integer> liveErrorType = data -> {
        Log.d("live liveErrorType " + data);
        if (data == ErrorType.EMPTY_RESULT) {
            if (adapter.getItemCount() > 1) { //++footer loading more
                adapter.notifyNoMoreData();
            } else {
                imgNoResult.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(requireContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
        }
    };
}
