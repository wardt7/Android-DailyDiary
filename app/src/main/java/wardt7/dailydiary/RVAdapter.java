package wardt7.dailydiary;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Toby on 18/11/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DiaryEntryViewHolder> {
    public static class DiaryEntryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView rating;
        TextView date;
        TextView keyword;

        DiaryEntryViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            rating = (TextView) itemView.findViewById(R.id.rating);
            date = (TextView) itemView.findViewById(R.id.date);
            keyword = (TextView) itemView.findViewById(R.id.keyword);
        }
    }
    List<DiaryEntry> entries;

    RVAdapter(List<DiaryEntry> entries){
        this.entries = entries;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DiaryEntryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        DiaryEntryViewHolder devh = new DiaryEntryViewHolder(v);
        return devh;
    }

    @Override
    public void onBindViewHolder(DiaryEntryViewHolder diaryEntryViewHolder, int i) {
        diaryEntryViewHolder.rating.setText(entries.get(i).rating);
        diaryEntryViewHolder.date.setText(entries.get(i).date);
        diaryEntryViewHolder.keyword.setText(entries.get(i).keyword);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

}
