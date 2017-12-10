package wardt7.dailydiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Toby on 18/11/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.DiaryEntryViewHolder>{
    public static final String FILE_NAME = "diaryentries.txt";
    private File file;
    private FileOutputStream outputStream;
    private static final int EDIT_RESULT = 1;


    public class DiaryEntryViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView rating;
        TextView date;
        TextView keyword;


        DiaryEntryViewHolder(final View itemView) {
            super(itemView);
            // Get widgets for cards and their contents
            cv = itemView.findViewById(R.id.card_view);
            rating = itemView.findViewById(R.id.rating);
            date = itemView.findViewById(R.id.date);
            keyword = itemView.findViewById(R.id.keyword);
            // Only create click/longclick if there are viewable and deletable entries
            if (!(entries.get(0).rating.equals("No Entries!"))) {
                itemView.setClickable(true);
                itemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Go to view the diary entry that was selected when clicked
                        int position = getPosition();
                        Context context = v.getContext();
                        String sendDate = entries.get(position).date;
                        String sendRating = entries.get(position).rating;
                        String sendKeyword = entries.get(position).keyword;
                        String sendContents = entries.get(position).contents;
                        Intent intentView = new Intent(context, ViewEntryActivity.class);
                        intentView.putExtra("date", sendDate);
                        intentView.putExtra("rating", sendRating);
                        intentView.putExtra("keyword", sendKeyword);
                        intentView.putExtra("contents", sendContents);
                        context.startActivity(intentView);
                    }
                });
                itemView.setLongClickable(true);
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        final Context context = v.getContext();
                        // Create a Dialog when a long click occurs to confirm user's decision
                        // to delete
                        DialogInterface.OnClickListener queryClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int option) {
                                switch (option) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        int position = getPosition();
                                        entries.remove(position);
                                        if (entries.size() == 0){
                                            // When there are no entries after removal, we add a
                                            // "No Entries!" entry that isn't clickable
                                            entries.add(new DiaryEntry("","","No Entries!",""));
                                            itemView.setClickable(false);
                                            itemView.setLongClickable(false);
                                        }
                                        // Update the file and tell the adapter that the
                                        // data set has changed
                                        save(context);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position,entries.size());
                                        notifyDataSetChanged();
                                        Toast.makeText(context,"Entry Deleted", Toast.LENGTH_SHORT).show();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };
                        // Create an alert on Long Click to ask the user if they want to delete
                        // the entry
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setMessage("Are you sure you want to delete this entry?").setPositiveButton("Yes", queryClickListener).setNegativeButton("No", queryClickListener).show();
                        return true;
                    }
                });

            } else{
                // Prevent people from viewing the "No Entries" entry as it contains nothing.
                itemView.setLongClickable(false);
                itemView.setClickable(false);
            }



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
        // Create a viewHolder for each card so that each card can be accessed in the adapter
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        DiaryEntryViewHolder devh = new DiaryEntryViewHolder(v);
        return devh;
    }


    @Override
    public void onBindViewHolder(DiaryEntryViewHolder diaryEntryViewHolder, int i) {
        // When given a card, set the text of the card to the correct entry
        diaryEntryViewHolder.rating.setText(entries.get(i).rating);
        diaryEntryViewHolder.date.setText(entries.get(i).date);
        diaryEntryViewHolder.keyword.setText(entries.get(i).keyword);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public boolean save(Context context){
        // Instantiate a file and a string builder
        file = new File(context.getFilesDir(), FILE_NAME);
        String data = "";
        StringBuilder builder = new StringBuilder();
        // Go through each entry in entries and add it to the StringBuilder. Note that
        // this function is normally called after an entry has been removed
        for(Iterator<DiaryEntry> i = entries.iterator(); i.hasNext();){
            DiaryEntry entry = i.next();
            if (entry.rating.equals("No Entries!")){
                return true;
            }
            builder.append(entry.date + "|" + entry.keyword + "|" + entry.rating + "|" + entry.contents + "|");
        }
        data = builder.toString();
        try{
            // Update diaryentries.txt with the new file
            outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
