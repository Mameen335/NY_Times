package com.mameen.mytask.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mameen.mytask.R;
import com.mameen.mytask.data.models.Media;
import com.mameen.mytask.data.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends ParentRecyclerAdapter<News> {

    static final String TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter(Context context, List<News> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public ParentRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        holder.setOnItemClickListener(itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ParentRecyclerViewHolder holder, int position) {
        ViewHolder viewholder = (ViewHolder) holder;

        final News news = data.get(position);

        viewholder.tvTitle.setText(news.getTitle());
        viewholder.tvByLine.setText(news.getByline());
        viewholder.tvAuthor.setText(news.getSource());
        viewholder.tvDate.setText(news.getPublished_date());

        Media media = news.getMedia().get(0);
        if (media.getType().equals("image")) {
            try {
                Picasso.get()
                        .load(media.getMediaMetadata().get(0).getUrl())
                        .placeholder(R.color.grey)
                        .error(R.color.grey)
                        .into(viewholder.img);
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
        }

    }

    private class ViewHolder extends ParentRecyclerViewHolder {

        CircleImageView img;

        TextView tvTitle;
        TextView tvByLine;
        TextView tvAuthor;
        TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvByLine = itemView.findViewById(R.id.tvByLine);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvDate = itemView.findViewById(R.id.tvDate);

            setClickableRootView(itemView);
        }
    }
}
