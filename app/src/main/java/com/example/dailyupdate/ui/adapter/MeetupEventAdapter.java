package com.example.dailyupdate.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyupdate.R;
import com.example.dailyupdate.data.model.MeetupEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetupEventAdapter extends RecyclerView.Adapter<MeetupEventAdapter.MeetupEventAdapterViewHolder> {

    @BindView(R.id.textview_event_title)
    TextView eventNameTextView;
    @BindView(R.id.textview_event_status)
    TextView eventStatusTextView;
    @BindView(R.id.textview_event_date)
    TextView eventDateTextView;
    @BindView(R.id.textview_event_time)
    TextView eventTimeTextView;
    @BindView(R.id.textview_group_title)
    TextView groupNameTextView;
    @BindView(R.id.textview_event_attendees)
    TextView attendeesCountTextView;
    @BindView(R.id.bookmark_icon_meetup_main)
    ImageView bookmarkIcon;

    private Context context;
    private static ClickListener clickListener;
    private static BookmarkIconClickListener bookmarkIconListener;
    private final List<MeetupEvent> meetupEventList;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MeetupEventAdapter.clickListener = clickListener;
    }

    public interface BookmarkIconClickListener {
        void onBookmarkIconClick(MeetupEvent meetupEvent);
    }

    public void setOnBookmarkIconClickListener(BookmarkIconClickListener bookmarkIconListener) {
        MeetupEventAdapter.bookmarkIconListener = bookmarkIconListener;
    }

    public MeetupEventAdapter(Context context, List<MeetupEvent> meetupEventList) {
        this.context = context;
        this.meetupEventList = meetupEventList;
    }

    @Override
    public MeetupEventAdapterViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.meetup_main_event_item, parent,
                false);
        ButterKnife.bind(this, view);
        return new MeetupEventAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeetupEventAdapterViewHolder viewHolder, int i) {
        MeetupEvent meetupEvent = meetupEventList.get(i);
        // TODO: change date & time format
        String eventDate = meetupEvent.getEventDate();
        String eventTime = meetupEvent.getEventTime();
        String attendeesCountString =
                meetupEvent.getAttendeesCount() + context.getString(R.string.meetupevent_attendees_label);

        eventNameTextView.setText(meetupEvent.getEventName());
        eventStatusTextView.setText(meetupEvent.getEventStatus());
        eventDateTextView.setText(eventDate);
        eventTimeTextView.setText(eventTime);
        groupNameTextView.setText(meetupEvent.getGroupNameObject().getEventGroupName());
        attendeesCountTextView.setText(attendeesCountString);
    }

    @Override
    public int getItemCount() {
        if (null == meetupEventList) return 0;
        return meetupEventList.size();
    }

    public MeetupEvent getCurrentEvent(int position) {
        return meetupEventList.get(position);
    }

    public class MeetupEventAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, BookmarkIconClickListener {

        private MeetupEventAdapterViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            bookmarkIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (bookmarkIconListener != null && position != RecyclerView.NO_POSITION) {
                        bookmarkIconListener.onBookmarkIconClick(meetupEventList.get(position));
                    }
                }
            });
        }

        @Override
        public void onClick(final View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public void onBookmarkIconClick(MeetupEvent meetupEvent) {
            bookmarkIconListener.onBookmarkIconClick(getCurrentEvent(getAdapterPosition()));
        }
    }
}

