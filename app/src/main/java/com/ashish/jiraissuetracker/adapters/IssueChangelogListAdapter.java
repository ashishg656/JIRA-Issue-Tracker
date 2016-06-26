package com.ashish.jiraissuetracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.activities.BaseActivity;
import com.ashish.jiraissuetracker.objects.issueHistory.History;
import com.ashish.jiraissuetracker.objects.issueHistory.IssueHistoryObject;
import com.ashish.jiraissuetracker.objects.issueHistory.Item;
import com.ashish.jiraissuetracker.serverApi.ImageRequestManager;
import com.ashish.jiraissuetracker.utils.TimeUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ashish on 22/06/16.
 */
public class IssueChangelogListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<History> mData;
    MyClickListener clickListener;

    int commentColorBlue, commentColorRed;

    public IssueChangelogListAdapter(Context context, List<History> mData) {
        this.context = context;
        this.mData = mData;
        clickListener = new MyClickListener();
        commentColorBlue = context.getResources().getColor(R.color.colorAccent);
        commentColorRed = context.getResources().getColor(R.color.red_color_primary);
    }

    private class ChangeHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name, time, field, fieldData, from, fromData, to, toData;
        TextView field2, fieldData2, from2, fromData2, to2, toData2;
        LinearLayout secondLayout, toContainer, toContainer2, openUserProfileLayout;

        public ChangeHolder(View v) {
            super(v);
            image = (CircleImageView) v.findViewById(R.id.circularimage);
            name = (TextView) v.findViewById(R.id.uploadrname);
            time = (TextView) v.findViewById(R.id.time);
            field = (TextView) v.findViewById(R.id.field);
            fieldData = (TextView) v.findViewById(R.id.fielddata);
            from = (TextView) v.findViewById(R.id.from);
            fromData = (TextView) v.findViewById(R.id.fromdata);
            to = (TextView) v.findViewById(R.id.to);
            toData = (TextView) v.findViewById(R.id.todata);
            field2 = (TextView) v.findViewById(R.id.field2);
            fieldData2 = (TextView) v.findViewById(R.id.fielddata2);
            from2 = (TextView) v.findViewById(R.id.from2);
            fromData2 = (TextView) v.findViewById(R.id.fromdata2);
            to2 = (TextView) v.findViewById(R.id.to2);
            toData2 = (TextView) v.findViewById(R.id.todata2);
            toData2 = (TextView) v.findViewById(R.id.todata2);
            secondLayout = (LinearLayout) v.findViewById(R.id.second_issue_history_layout);
            toContainer = (LinearLayout) v.findViewById(R.id.tocontainer);
            toContainer2 = (LinearLayout) v.findViewById(R.id.tocontainer2);
            openUserProfileLayout = (LinearLayout) v.findViewById(R.id.openuserprofilechangelogitem);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.issue_detail_changelog_list_item_layout, parent, false);
        ChangeHolder holder = new ChangeHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        position = viewHolder.getAdapterPosition();

        ChangeHolder holder = (ChangeHolder) viewHolder;
        History history = mData.get(position);

        holder.name.setText(history.getAuthor().getDisplayName());
        holder.time.setText(TimeUtils.getPostTimeGMTActivityStream(history.getCreated()));

        ImageRequestManager.requestImage(holder.image, history.getAuthor().getAvatarUrls().get48x48());

        holder.openUserProfileLayout.setTag(history.getAuthor().getName());
        holder.openUserProfileLayout.setOnClickListener(clickListener);

        if (history.getItems().size() == 1) {
            holder.secondLayout.setVisibility(View.GONE);

            Item item = history.getItems().get(0);

            setDataForFields(0, item, holder);
        } else {
            holder.secondLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < 2; i++) {
                try {
                    Item item = history.getItems().get(i);
                    setDataForFields(i, item, holder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setDataForFields(int pos, Item item, ChangeHolder holder) {
        if (pos == 0) {
            holder.fieldData.setText(item.getField());

            if (item.getField().equalsIgnoreCase("Comment")) {
                holder.toContainer.setVisibility(View.GONE);
                holder.fromData.setTextColor(commentColorBlue);

                if (item.getFrom() != null) {
                    holder.fromData.setText(item.getFrom());
                } else if (item.getFromString() != null) {
                    holder.fromData.setText(item.getFromString());
                }

                holder.from.setVisibility(View.GONE);
            } else {
                holder.toContainer.setVisibility(View.VISIBLE);
                holder.from.setVisibility(View.VISIBLE);
                holder.fromData.setTextColor(commentColorRed);

                if (item.getToString() != null) {
                    holder.toData.setText(item.getToString());
                } else if (item.getTo() != null) {
                    holder.toData.setText(item.getTo());
                } else {
                    holder.toContainer.setVisibility(View.GONE);
                }

                if (item.getFromString() != null) {
                    holder.fromData.setText(item.getFromString());
                } else if (item.getFrom() != null) {
                    holder.fromData.setText(item.getFrom());
                }
            }
        } else if (pos == 1) {
            holder.fieldData2.setText(item.getField());

            if (item.getField().equalsIgnoreCase("Comment")) {
                holder.toContainer2.setVisibility(View.GONE);
                holder.fromData.setTextColor(commentColorBlue);

                if (item.getFrom() != null) {
                    holder.fromData2.setText(item.getFrom());
                } else if (item.getFromString() != null) {
                    holder.fromData2.setText(item.getFromString());
                }

                holder.from2.setVisibility(View.GONE);
            } else {
                holder.toContainer2.setVisibility(View.VISIBLE);
                holder.from2.setVisibility(View.VISIBLE);
                holder.fromData.setTextColor(commentColorRed);

                if (item.getToString() != null) {
                    holder.toData2.setText(item.getToString());
                } else if (item.getTo() != null) {
                    holder.toData2.setText(item.getTo());
                } else {
                    holder.toContainer2.setVisibility(View.GONE);
                }

                if (item.getFromString() != null) {
                    holder.fromData2.setText(item.getFromString());
                } else if (item.getFrom() != null) {
                    holder.fromData2.setText(item.getFrom());
                }
            }
        }
    }

    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.openuserprofilechangelogitem:
                    String userName = (String) view.getTag();
                    ((BaseActivity) context).openUserProfileActivity(userName);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
