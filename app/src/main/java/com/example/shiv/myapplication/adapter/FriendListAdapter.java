package com.example.shiv.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiv.myapplication.R;

import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
  private List<String> data;
  public FriendListAdapter(List<String> data){
    this.data = data;
  }

  @NonNull
  @Override
  public FriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_grid_item, parent, false);
    return new ViewHolder(rowItem);
  }

  @Override
  public void onBindViewHolder(FriendListAdapter.ViewHolder holder, int position) {
    holder.textView.setText(this.data.get(position));
  }

  @Override
  public int getItemCount() {

    return this.data.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView textView;

    public ViewHolder(View view) {
      super(view);
      view.setOnClickListener(this);
      this.textView = view.findViewById(R.id.tv_user_name);
    }

    @Override
    public void onClick(View view) {
      Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.textView.getText(), Toast.LENGTH_SHORT).show();
    }
  }
}