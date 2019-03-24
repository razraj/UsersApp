package com.kodeWorkTest.project.features.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.kodeWorkTest.project.R;
import com.kodeWorkTest.project.data.model.response.UserData;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<UserData> usersList = new ArrayList<>();
    private Subject<UserData> pokemonClickSubject;

    @Inject
    UsersAdapter() {
        pokemonClickSubject = PublishSubject.create();
    }

    public void setUsers(List<UserData> users) {
        if (usersList.size() > 0) {
            int position = usersList.size() - 1;
            usersList.remove(position);
            notifyItemRemoved(position);
        }
        this.usersList.addAll(users);
        usersList.add(null);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_users, parent, false);
            return new UsersViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UsersViewHolder) {
            ((UsersViewHolder) holder).onBind(this.usersList.get(position));
        } else {

        }

    }

    @Override
    public int getItemViewType(int position) {
        return usersList.size() > 0 ? usersList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    Observable<UserData> getPokemonClick() {
        return pokemonClickSubject;
    }

    public void removeAll() {
        int total = usersList.size();
        usersList.clear();
        notifyItemRangeRemoved(0, total);
    }

    public void hideLoader() {
        int position = usersList.size() - 1;
        usersList.remove(position);
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.first_name)
        TextView tvFirstName;
        @BindView(R.id.last_name)
        TextView tvLastName;
        @BindView(R.id.iv_avatar)
        ImageView iv_avatar;

        private UserData userData;

        UsersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> pokemonClickSubject.onNext(userData));
        }

        void onBind(UserData userData) {
            this.userData = userData;

            Glide.with(itemView.getContext()).load(userData.getAvatar()).into(iv_avatar);

            tvFirstName.setText(
                    String.format(
                            "%s%s", userData.getFirst_name().substring(0, 1).toUpperCase(), userData.getFirst_name().substring(1)));

            tvLastName.setText(
                    String.format(
                            "%s%s", userData.getLast_name().substring(0, 1).toUpperCase(), userData.getFirst_name().substring(1)));
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
