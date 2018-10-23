package com.example.solom.finalproject020618.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.solom.finalproject020618.R;
import com.example.solom.finalproject020618.db.DBHandler;
import com.example.solom.finalproject020618.db.Place;
import com.example.solom.finalproject020618.network.OnLocationClick;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RV_Adapter extends RecyclerView.Adapter<RV_Adapter.ViewHolder> {

    private List<Place> mPlaceList;
    private Context mContext;
    private OnLocationClick callback;
    private Activity mActivity;

    public RV_Adapter(List<Place> placeList, Context context) {

        mPlaceList = placeList;
        mContext = context;
        this.mActivity = mActivity;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.layout_list_item, parent, false);

        ViewHolder holder = new ViewHolder(contactView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final Place place = mPlaceList.get(position);
        final TextView locationName = holder.locationName;
        locationName.setText(place.getLocationName());
        final ImageView icon = holder.icon;
        Picasso.get().load(place.getIcon()).into(icon);
        CircleImageView image = holder.image;
        Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + place.getPhotoRef() + "&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY").into(image);
        final TextView distance = holder.distance;
        distance.setText(place.getAddress());
        TextView realDistance = holder.realDistance;
        realDistance.setText(String.format("%.2f", place.getDistance()) + " " + place.getConverter());


        DBHandler handler = new DBHandler(mContext);

        if (handler.getLocationNameDB(place.getLocationName())) {
            holder.heartOutline.setVisibility(View.INVISIBLE);
            holder.heartFill.setVisibility(View.VISIBLE);
        }




        holder.locationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address = mPlaceList.get(position).getAddress();

                double lat = mPlaceList.get(position).getLat();
                double lng = mPlaceList.get(position).getLng();
                String icon = mPlaceList.get(position).getIcon();
                String locationName = mPlaceList.get(position).getLocationName();
                double rating = mPlaceList.get(position).getRating();
                String photoRef = mPlaceList.get(position).getPhotoRef();
                double distance = mPlaceList.get(position).getDistance();
                String converter = mPlaceList.get(position).getConverter();


                callback = (OnLocationClick) mContext;
                callback.onLocationClick(address, lat, lng, icon , locationName, rating, photoRef, distance, converter);


            }
        });

        holder.distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = mPlaceList.get(position).getAddress();

                double lat = mPlaceList.get(position).getLat();
                double lng = mPlaceList.get(position).getLng();
                String icon = mPlaceList.get(position).getIcon();
                String locationName = mPlaceList.get(position).getLocationName();
                double rating = mPlaceList.get(position).getRating();
                String photoRef = mPlaceList.get(position).getPhotoRef();
                double distance = mPlaceList.get(position).getDistance();
                String converter = mPlaceList.get(position).getConverter();


                callback = (OnLocationClick) mContext;
                callback.onLocationClick(address, lat, lng, icon , locationName, rating, photoRef, distance, converter);
            }
        });

        //final DBHandler handler1 = new DBHandler(mContext);

//adding location to DB - Favorites

     /*   holder.heartOutline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler handler = new DBHandler(mContext);

                if (!handler.getLocationNameDB(place.getLocationName())) {
                    handler.addPlace(new Place(place.getAddress(), place.getLat(), place.getLng(), place.getIcon(), place.getLocationName(),
                            place.getRating(), place.getPhotoRef(), place.getDistance(), place.getConverter()));
                }


                holder.heartOutline.setVisibility(View.INVISIBLE);
                holder.heartFill.setVisibility(View.VISIBLE);
            }
        });*/

        final DBHandler handler2 = new DBHandler(mContext);


        holder.heartOutline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.heartOutline.setVisibility(View.INVISIBLE);
                holder.heartFill.setVisibility(View.VISIBLE);



                if (!handler2.getLocationNameDB(place.getLocationName())) {
                    handler2.addPlace(new Place(place.getAddress(), place.getLat(), place.getLng(), place.getIcon(), place.getLocationName(),
                            place.getRating(), place.getPhotoRef(), place.getDistance(), place.getConverter()));

                 //   handler.addPlace(new Place(place.getAddress(), place.getLat(), place.getLng(), place.getIcon(),


                }


            }
        });

        holder.heartFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//delete location from DB = Favorites
                holder.heartFill.setVisibility(View.INVISIBLE);
                holder.heartOutline.setVisibility(View.VISIBLE);
                String name = mPlaceList.get(position).getLocationName();
                handler2.deletePlaceByName((name) );
//                notifyItemRemoved(position);
//                notifyDataSetChanged();


            }
        });


    }

    @Override
    public int getItemCount() {
        return mPlaceList.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image;
        public ImageView icon;
        public TextView locationName;
        public TextView distance;
        public TextView realDistance;
        public ConstraintLayout mConstraintLayout;
        private ImageView heartOutline;
        private ImageView heartFill;


        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.CircleImage);
            icon = itemView.findViewById(R.id.imageIcon);
            locationName = itemView.findViewById(R.id.locationName);
            distance = itemView.findViewById(R.id.TextViewDistance);
            realDistance = itemView.findViewById(R.id.realDistance);
            heartOutline = itemView.findViewById(R.id.heartOutline);
            heartFill = itemView.findViewById(R.id.heartFill);
            mConstraintLayout = itemView.findViewById(R.id.list_item_layout);


        }
    }


}


