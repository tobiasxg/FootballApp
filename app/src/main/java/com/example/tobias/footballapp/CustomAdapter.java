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
public class CustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(ListActivity listActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=listActivity;
        imageId=prgmImages;
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
        TextView tvRating;
        TextView tv;
        TextView tvPos;
        TextView tvAge;
        ImageView img;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tvRating=(TextView) rowView.findViewById(R.id.textView0);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.tvPos=(TextView) rowView.findViewById(R.id.textView2);
        holder.tvAge=(TextView) rowView.findViewById(R.id.textView3);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tvRating.setText(result[position].split(" - ")[0]);
        holder.tv.setText(result[position].split(" - ")[1]);
        holder.tvPos.setText(result[position].split(" - ")[2]);
        holder.tvAge.setText(result[position].split(" - ")[3]);
        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();

//                String[] playerSplit = result[position].split(" - ");
//                Intent i = new Intent(context,CreatePlayerActivity.class);
                Intent i = new Intent(context,PlayerAccountActivity.class);
//                Intent i = new Intent(context,MainActivity.class);
                i.putExtra("playername", result[position]);
                i.putExtra("listName", "players");
                context.startActivity(i);


//                TextView titleView = (TextView) v;
//                String playername = titleView.getText().toString();
//                String[] playerSplit = result[position].split(" - ");
//                String swek = "";
//                Intent i = new Intent(context,PlayerAccountActivity.class);
//                Intent i = new Intent(convertView.getContext(),PlayerAccountActivity.class);
//                i.putExtra("playername", playerSplit[1]);
//                i.putExtra("listName", "players");
//                context.startActivity(i);
//                convertView.getContext().startActivity(i);
            }
        });
        return rowView;
    }

}