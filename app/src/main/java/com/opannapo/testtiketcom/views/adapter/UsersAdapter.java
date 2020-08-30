package com.opannapo.testtiketcom.views.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.opannapo.core.layer.application.domain.User;
import com.opannapo.testtiketcom.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by napouser on 31,August,2020
 */
public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<User> data;
    private OnItemClicklListener listener;
    private Context context;
    private String queryMatch;
    private boolean isNoMoreData;

    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOAD_MORE = 2;

    public UsersAdapter(Context context, List<User> data, OnItemClicklListener listener) {
        this.listener = listener;
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_ITEM) {
            return new ViewHolderItem(inflater.inflate(R.layout.view_item_user, parent, false));
        } else {
            return new ViewHolderLoadMore(inflater.inflate(R.layout.view_item_load_more, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderItem) {
            ((ViewHolderItem) holder).bind(data.get(position), listener);
        } else {
            ((ViewHolderLoadMore) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == (getItemCount() - 1) ? VIEW_TYPE_LOAD_MORE : VIEW_TYPE_ITEM;
    }


    class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgProfile;

        ViewHolderItem(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgProfile = itemView.findViewById(R.id.imgProfile);
        }

        void bind(final User u, final OnItemClicklListener listener) {
            String name = u.getLogin();
            String nameOut = name.replace(queryMatch, "<b>" + queryMatch + "</b>");
            txtName.setText(Html.fromHtml(nameOut));
            Glide.with(context)
                    .load(u.getAvatarUrl())
                    .centerCrop()
                    .error(R.drawable.baseline_aspect_ratio_black_18dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .circleCrop()
                    .into(imgProfile);
            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onItemClick(getAdapterPosition(), u);
                }
            });
        }
    }

    class ViewHolderLoadMore extends RecyclerView.ViewHolder {
        @BindView(R.id.txtLoadingIndicator)
        TextView txtLoadingIndicator;
        @BindView(R.id.prbLoading)
        ProgressBar prbLoading;

        ViewHolderLoadMore(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind() {
            if (isNoMoreData) {
                prbLoading.setVisibility(View.GONE);
                txtLoadingIndicator.setVisibility(View.VISIBLE);
                txtLoadingIndicator.setText(data.size() + " Data Loaded");
            } else {
                prbLoading.setVisibility(View.VISIBLE);
                txtLoadingIndicator.setVisibility(View.GONE);
            }
        }
    }


    public void notifyAddMoreData(List<User> data, String queryMatch) {
        this.queryMatch = queryMatch;
        this.data.addAll(data);
        this.notifyItemInserted(getItemCount() - 1);
    }

    public void notifyAddMoreData(User data, String queryMatch) {
        this.queryMatch = queryMatch;
        this.data.add(data);
        this.notifyItemInserted(getItemCount() - 1);
    }

    public void notifyNoMoreData() {
        isNoMoreData = true;
        notifyItemChanged(getItemCount() - 1);
    }

    public interface OnItemClicklListener {
        void onItemClick(int i, User user);
    }
}
