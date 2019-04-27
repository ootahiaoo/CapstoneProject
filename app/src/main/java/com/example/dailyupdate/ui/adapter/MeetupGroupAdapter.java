package com.example.dailyupdate.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dailyupdate.R;
import com.example.dailyupdate.data.model.MeetupGroup;

import java.util.List;

public class MeetupGroupAdapter extends RecyclerView.Adapter<MeetupGroupAdapter.MeetupGroupAdapterViewHolder> {

    private Context context;
    private static ClickListener clickListener;
    private final List<MeetupGroup> meetupGroupList;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MeetupGroupAdapter.clickListener = clickListener;
    }

    public MeetupGroupAdapter(Context context, List<MeetupGroup> meetupGroupList) {
        this.context = context;
        this.meetupGroupList = meetupGroupList;
    }

    @Override
    public MeetupGroupAdapterViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_meetupgroup_item, parent, false);
        MeetupGroupAdapterViewHolder viewHolder = new MeetupGroupAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MeetupGroupAdapterViewHolder viewHolder, int i) {
        MeetupGroup meetupGroup = meetupGroupList.get(i);
        String groupTitle = meetupGroup.getGroupName();
        int groupMembers = meetupGroup.getGroupMembers();
        String groupMembersString =
                String.valueOf(groupMembers) + context.getString(R.string.meetupgroup_members_label);

        if (meetupGroup.getGroupPhoto() != null) {
            String groupImageUrl = meetupGroup.getGroupPhoto().getGroupPhotoUrl();
            Glide.with(viewHolder.groupImageView.getContext()).load(groupImageUrl).centerCrop().into(viewHolder.groupImageView);
        }
        // TODO: handle empty image case

        viewHolder.groupTitleTextView.setText(groupTitle);
        viewHolder.groupMembersTextView.setText(groupMembersString);
    }

    @Override
    public int getItemCount() {
        if (null == meetupGroupList) return 0;
        return meetupGroupList.size();
    }

    public class MeetupGroupAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView groupTitleTextView;
        private final TextView groupMembersTextView;
        private final ImageView groupImageView;

        private MeetupGroupAdapterViewHolder(View view) {
            super(view);
            groupTitleTextView = (TextView) view.findViewById(R.id.textview_group_title);
            groupMembersTextView = (TextView) view.findViewById(R.id.textview_group_members);
            groupImageView = (ImageView) view.findViewById(R.id.imageview_group_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
