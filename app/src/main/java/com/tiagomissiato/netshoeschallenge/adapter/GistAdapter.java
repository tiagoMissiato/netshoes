package com.tiagomissiato.netshoeschallenge.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiagomissiato.netshoeschallenge.R;
import com.tiagomissiato.netshoeschallenge.models.GistItem;
import com.tiagomissiato.netshoeschallenge.utils.RoundedCornersTransformation;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tiagomissiato on 5/22/16.
 */
public class GistAdapter extends RecyclerView.Adapter<GistAdapter.ViewHolder> {

    Context mContext;
    private List<GistItem> mItems;

    OnGistClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        @Bind(R.id.gist_owner_img)
        ImageView mGistImage;
        @Bind(R.id.gist_owner)
        TextView mGistOwner;
        @Bind(R.id.gist_type)
        TextView mGistType;
        @Bind(R.id.gist_language)
        TextView mGistLanguage;

        OnGistClickListener mListener;

        View mView;

        public ViewHolder(View v, OnGistClickListener listener) {
            super(v);

            ButterKnife.bind(this, v);

            mView = v;
            mListener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null) {
                mListener.onClicked(getAdapterPosition());
            }
        }
    }

    public GistAdapter(Context mContext, List<GistItem> items, OnGistClickListener listener) {
        this.mItems = items;
        this.mContext = mContext;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gist, parent, false);

        return new ViewHolder(layout, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GistItem item = mItems.get(position);

        if(getItemViewType(position) == 0) {
            holder.mView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.odd));
        } else {
            holder.mView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }

        String ownerName = mContext.getString(R.string.anonymous);
        if(item.owner != null) {
            ownerName = item.owner.login;

            if(!TextUtils.isEmpty(item.owner.avatar)) {
                Glide.with(mContext)
                        .load(item.owner.avatar)
                        .centerCrop()
                        .bitmapTransform(new RoundedCornersTransformation(mContext, 30, 0, RoundedCornersTransformation.CornerType.ALL))
                        .into(holder.mGistImage);
            }
        }
        holder.mGistOwner.setText(mContext.getString(R.string.gist_owner_file, ownerName, item.gist.filename));
        holder.mGistLanguage.setText(item.gist.language);
        holder.mGistType.setText(item.gist.type);

    }

    @Override
    public int getItemViewType(int position) {
        return (position % 2 == 0) ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setListener(OnGistClickListener listener) {
        this.mListener = listener;
    }

    public interface OnGistClickListener{
        void onClicked(int position);
    }

}
