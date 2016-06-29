package com.ashish.jiraissuetracker.adapters;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.glideImageRequest.GlideRequestManager;
import com.ashish.jiraissuetracker.objects.issueDetail.Priority;
import com.ashish.jiraissuetracker.objects.projectListing.ProjectListingObject;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 27/06/16.
 */
public class SelectPriorityFragmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Priority> mData;
    List<String> selectedAlready;

    SparseBooleanArray selectedItems;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;
    MyClickListener clickListener;

    public SelectPriorityFragmentListAdapter(Context context, List<Priority> mData, List<String> selectedAlready) {
        if (context == null) {
            return;
        }

        this.context = context;
        this.mData = mData;
        this.selectedAlready = selectedAlready;

        requestBuilder = GlideRequestManager.getRequestBuilder(context, R.drawable.symphony);
        clickListener = new MyClickListener();
        selectedItems = new SparseBooleanArray();

        if (selectedAlready != null) {
            for (int i = 0; i < mData.size(); i++) {
                if (selectedAlready.contains(mData.get(i).getName())) {
                    selectedItems.put(i, true);
                }
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.select_user_fragment_list_item_layout, parent, false);
        UserHolder holder = new UserHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderCom, int position) {
        position = holderCom.getAdapterPosition();

        UserHolder holder = (UserHolder) holderCom;
        Priority priority = mData.get(position);

        if (selectedItems.get(position, false)) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.name.setText(priority.getName());
        holder.email.setMaxLines(1);
        holder.email.setEllipsize(TextUtils.TruncateAt.END);
        holder.email.setText(priority.getDescription());

        holder.imageNonCircular.setVisibility(View.VISIBLE);
        holder.image.setVisibility(View.GONE);

        try {
            if (priority.getIconUrl() != null) {
                String url = priority.getIconUrl();
                Uri uri = Uri.parse(url);
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .load(uri)
                        .into(holder.imageNonCircular);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.container.setTag(position);
        holder.container.setOnClickListener(clickListener);
    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.openuserprofilechangelogitem:
                    int pos = (int) view.getTag();
                    toggleSelection(pos);
                    break;
            }
        }
    }

    private class UserHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        ImageView imageNonCircular;
        TextView name, email;
        CheckBox checkBox;
        LinearLayout container;

        public UserHolder(View v) {
            super(v);
            image = (CircleImageView) v.findViewById(R.id.circularimage);
            imageNonCircular = (ImageView) v.findViewById(R.id.circularimageImage);
            name = (TextView) v.findViewById(R.id.uploadrname);
            email = (TextView) v.findViewById(R.id.time);
            container = (LinearLayout) v.findViewById(R.id.openuserprofilechangelogitem);
            checkBox = (CheckBox) v.findViewById(R.id.checkbox_1);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public List<String> getSelectedItemsString() {
        List<String> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(mData.get(selectedItems.keyAt(i)).getName());
        }
        return items;
    }
}
