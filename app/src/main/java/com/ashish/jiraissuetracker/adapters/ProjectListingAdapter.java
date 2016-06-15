package com.ashish.jiraissuetracker.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.glideImageRequest.GlideRequestManager;
import com.ashish.jiraissuetracker.objects.projectListing.ProjectListingObject;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 15/06/16.
 */
public class ProjectListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ProjectListingObject> mData;
    MyClickListener clickListener;

    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public ProjectListingAdapter(Context context, List<ProjectListingObject> mData) {
        this.context = context;
        this.mData = mData;
        clickListener = new MyClickListener();

        requestBuilder = GlideRequestManager.getRequestBuilder(context);
    }

    private class ProjectHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name, type, lead, key;
        FrameLayout container;

        public ProjectHolder(View v) {
            super(v);
            image = (CircleImageView) v.findViewById(R.id.project_image);
            name = (TextView) v.findViewById(R.id.project_name);
            type = (TextView) v.findViewById(R.id.project_type);
            lead = (TextView) v.findViewById(R.id.project_lead);
            key = (TextView) v.findViewById(R.id.project_key);
            container = (FrameLayout) v.findViewById(R.id.project_container);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.project_listing_list_item_layout, parent, false);
        ProjectHolder holder = new ProjectHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderCom, int position) {
        position = holderCom.getAdapterPosition();
        ProjectHolder holder = (ProjectHolder) holderCom;

        ProjectListingObject object = mData.get(position);

        Uri uri = Uri.parse(object.getAvatarUrls().get48x48());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(holder.image);

        holder.lead.setText(object.getLead().getDisplayName());
        holder.type.setText(object.getProjectTypeKey());
        holder.name.setText(object.getName());
        holder.key.setText(object.getKey());
        holder.lead.setPaintFlags(holder.lead.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        holder.lead.setTag(object.getLead().getName());
        holder.lead.setOnClickListener(clickListener);

        holder.container.setTag(position);
        holder.container.setOnClickListener(clickListener);
    }

    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.project_lead:
                    String userName = (String) view.getTag();
                    ((BaseActivity) context).openUserProfileActivity(userName);
                    break;
                case R.id.project_container:
                    int position = (int) view.getTag();
                    ((BaseActivity) context).openProjectDetailActivity(mData.get(position));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
