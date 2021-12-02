package com.example.exampleclim;

import android.content.Context;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    public Context mContext;
    private List<ModelClim> albumList;
    private ArrayList<ModelClim> arraytitles;
    LayoutInflater inflater;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView hours, description;
        private ImageView mNetworkImageView;

        public MyViewHolder(View view) {
            super(view);
            mNetworkImageView = (ImageView) view.findViewById(R.id.iv_timeImg);
            description = (TextView) view.findViewById(R.id.txt_title);

        }
    }


    public AlbumsAdapter(Context mContext, List<ModelClim> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
        inflater = LayoutInflater.from(mContext);
        this.arraytitles = new ArrayList<ModelClim>();
        this.arraytitles.addAll(albumList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ModelClim album = albumList.get(position);
        holder.description.setText(album.getDescription()+"\n"+ album.getHours()+"\n"+album.getCity());

        Glide.with(mContext).load(album.getIcon())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .into(holder.mNetworkImageView);

        holder.mNetworkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopupMenu(holder.mNetworkImageView

                );
            }
        });



    }


    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ViewHolder {
        TextView nombre;
    }


    public int getCount() {
        return albumList.size();
    }


    public ModelClim getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.card, null);
            // Buscar los datos y presentarlos en el listview_item.xml
            holder.nombre = (TextView) view.findViewById(R.id.txt_title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Establecer resultados en el TextView
        holder.nombre.setText(albumList.get(position).getDescription());
        return view;
    }

    // Función filtrar
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        albumList.clear();
        if (charText.length() == 0) {
            albumList.addAll(arraytitles);
        } else {
            for (ModelClim wp : arraytitles) {
                if (wp.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                    albumList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
