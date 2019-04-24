package com.zcm.camera.net;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zcm.camera.R;
import com.zcm.ui.widget.H5Activity;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {
    Context context;
    List<FeedModel.FeedItem> feedList;

    public FeedAdapter(Context context) {
        this.context = context;
    }

    public void setFeedList(List<FeedModel.FeedItem> feedList) {
        this.feedList = feedList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feed_item, viewGroup,false);
        FeedHolder feedHolder = new FeedHolder(view);
        return feedHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder feedHolder, int position) {
        feedHolder.mFeedTitle.setText(feedList.get(position).title);
        feedHolder.mFeedSubtitle.setText(feedList.get(position).description);
        feedHolder.mFeedTime.setText(feedList.get(position).ctime);
        Glide.with(context).load(feedList.get(position).picUrl).into(feedHolder.mFeedImg);
        feedHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, H5Activity.class);
                intent.putExtra("H5Url", feedList.get(position).url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return feedList == null ? 0 : feedList.size();
    }

    static class FeedHolder extends RecyclerView.ViewHolder {

        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            mFeedImg = (ImageView) itemView.findViewById(R.id.feed_img);
            mFeedTitle = (TextView) itemView.findViewById(R.id.feed_title);
            mFeedSubtitle = (TextView) itemView.findViewById(R.id.feed_subtitle);
            mFeedTime = itemView.findViewById(R.id.feed_time);
            this.itemView=itemView;
        }

        ImageView mFeedImg;
        TextView mFeedTitle;
        TextView mFeedSubtitle;
        TextView mFeedTime;
        View itemView;

    }
}
