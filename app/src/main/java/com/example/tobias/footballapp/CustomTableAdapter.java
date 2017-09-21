package com.example.tobias.footballapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class CustomTableAdapter extends BaseAdapter{
    String [] result;
    String [] tablePositions;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomTableAdapter(TableActivity tableActivity, String[] prgmNameList, String[] tablePositions) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        this.tablePositions = tablePositions;
        context=tableActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tvPos;
        TextView tvTeam;
        TextView tvPlayed;
        TextView tvGFGA;
        TextView tvGD;
        TextView tvPoints;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.table_list, null);
        holder.tvPos=(TextView) rowView.findViewById(R.id.textViewPosition);
        holder.tvTeam=(TextView) rowView.findViewById(R.id.textViewTeamName);
        holder.tvPlayed=(TextView) rowView.findViewById(R.id.textViewPlayed);
        holder.tvGFGA=(TextView) rowView.findViewById(R.id.textViewGFGA);
        holder.tvGD=(TextView) rowView.findViewById(R.id.textViewGD);
        holder.tvPoints=(TextView) rowView.findViewById(R.id.textViewPoints);

//      Points    ?   Goal Difference ?   Scored Goals    ?   Conceded Goals  ?   NAME  ?   Played Matches  ?   Win ?   Draw    ?   Lose
        holder.tvPos.setText(tablePositions[position]);
//        holder.tvPos.setText(result[position].split("\\?")[0]);
        holder.tvTeam.setText(result[position].split("\\?")[4]);
        holder.tvPlayed.setText(result[position].split("\\?")[5].split("--")[0]);
        holder.tvGFGA.setText(result[position].split("\\?")[2].split("--")[0]+
                ":"+result[position].split("\\?")[3].split("--")[0]);
//        holder.tvGD.setText(Integer.toString(Integer.valueOf(result[position].split("\\?")[6].split("-")[0])-
//                Integer.valueOf(result[position].split("\\?")[7].split("-")[0])));
        holder.tvGD.setText(result[position].split("\\?")[1].split("--")[0]);
        holder.tvPoints.setText(result[position].split("\\?")[0].split("--")[0]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();

//                Intent i = new Intent(context,PlayerAccountActivity.class);
//                i.putExtra("playername", result[position]);
//                i.putExtra("listName", "players");
//                context.startActivity(i);
            }
        });
        return rowView;
    }

}