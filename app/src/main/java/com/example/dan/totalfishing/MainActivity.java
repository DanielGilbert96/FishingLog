package com.example.dan.totalfishing;

/*

    References:
    https://www.youtube.com/user/akshayejh/
    https://stackoverflow.com/questions/35624035/opening-google-map-through-intent-and-adding-marker-in-certain-latitude-longitud
    https://stackoverflow.com/questions/1124548/how-to-pass-the-values-from-one-activity-to-previous-activity
    https://github.com/it-objects/android-string-extractor-plugin

 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mFishingLog;

    private DatabaseReference mDatabase;

    private Button loc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loc = (Button) findViewById(R.id.location);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Fishing");

        mFishingLog = (RecyclerView) findViewById(R.id.Fishing_log);
        mFishingLog.hasFixedSize();
        mFishingLog.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Fishing, FishingLogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Fishing, FishingLogViewHolder>(

                Fishing.class,
                R.layout.fishing_row,
                FishingLogViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FishingLogViewHolder viewHolder, Fishing model, int position) {
                viewHolder.setSpecie(model.getSpecie());
                viewHolder.setDate(model.getDatetime());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setMethod(model.getMethod());
                viewHolder.setWeight(model.getWeight());
                viewHolder.setImage(getApplicationContext(),model.getImage());
            }
        };

        mFishingLog.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FishingLogViewHolder extends RecyclerView.ViewHolder{
        View mView;
        private String Location = "";

        public FishingLogViewHolder(View itemView){
            super(itemView);

            mView = itemView;
        }

        public void setSpecie(String specie){
            TextView post_specie = (TextView) mView.findViewById(R.id.post_specie);
            post_specie.setText(specie);
        }
        public void setMethod(String method){
            TextView post_method = (TextView) mView.findViewById(R.id.post_method);
            post_method.setText(method);
        }
        public void setWeight(String weight){
            TextView post_weight = (TextView) mView.findViewById(R.id.post_weight);
            post_weight.setText(weight);
        }
        public void setDate(String date){
            TextView post_date = (TextView) mView.findViewById(R.id.post_date);
            post_date.setText(date);
        }
        public void setLocation(String location){
            Location = location.toString().trim();

        }
        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return  super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(MainActivity.this, PostActivity.class));
        }
        return  super.onOptionsItemSelected(item);
    }
}
