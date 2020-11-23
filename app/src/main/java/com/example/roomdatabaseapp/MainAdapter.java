package com.example.roomdatabaseapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    // inisiasi variabel
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    //membuat konstraktor
    public MainAdapter(Activity context, List<MainData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inisiasi view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent
                , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //inisiasi main data
        MainData data = dataList.get(position);
        //inisiasi database
        database = RoomDB.getInstance(context);
        //set text on text view
        holder.textView.setText(data.getText());

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inisiasi main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //ambil id
                int sID = d.getID();
                //ambil text
                String sText = d.getText();

                //membuat dialog
                Dialog dialog = new Dialog(context);
                //set content view
                dialog.setContentView(R.layout.dialog_update);
                //inisiasi lebar
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //inisiasi tinggi
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //set layout
                dialog.getWindow().setLayout(width,height);
                //show dialog
                dialog.show();

                //inisiasi dan memasukkan variabel
                EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);

                //set text on edit text
                editText.setText(sText);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dismiss dialog
                        dialog.dismiss();
                        //get update text from edit
                        String uText = editText.getText().toString().trim();
                        //Update text in database
                        database.mainDao().update(sID,uText);
                        //notify when data is update
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());;
                        notifyDataSetChanged();
                    }
                });
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inisiasi main data
                MainData d = dataList.get(holder.getAdapterPosition());
                //delete teks dari database
                database.mainDao().delete(d);
                //notifikasi saat data sudah dihapus
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //inisiasi variabel
        TextView textView;
        ImageView btEdit,btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //cari variabel
            textView = itemView.findViewById(R.id.text_view);
            btDelete = itemView.findViewById(R.id.b_delete);
            btEdit = itemView.findViewById(R.id.b_edit);
        }
    }
}
