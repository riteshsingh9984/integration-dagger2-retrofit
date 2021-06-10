package com.example.shiv.myapplication;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shiv.myapplication.modals.User;
import com.example.shiv.myapplication.utils.UserUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class IndexActivity extends BaseActivity  {

    ListView listView;
    String[] arrayS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p"};
    String[] arrayS2 = new String[]{"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "i1", "j1", "k1", "l1", "m1", "n1", "o1", "p1"};
    int pageCount = 1;
    int p = 0;
    View footer;

    List<User> userList;
    UserAdapter adapter = null;
    int start = 0;
    int limit = 10;
    boolean loadingMore = false;
    View loadMoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
/*
        listView = (ListView) findViewById(R.id.load);

        final ArrayList<String> list = new ArrayList<String>();


        for (int i = 0; i < arrayS.length; i++) {
            list.add(arrayS[i]);
        }

        // Add footer view
        footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.progress, null, false);
        listView.addFooterView(footer);

        final ArrayAdapter<String> ad = new ArrayAdapter(IndexActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(ad);

        // Implementing scroll refresh
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItemCount, final int totalItems) {
                Log.e("Get position", "--firstItem:" + firstItem + "  visibleItemCount:" + visibleItemCount + "  totalItems:" + totalItems + "  pageCount:" + pageCount);
                int total = firstItem + visibleItemCount;


                // Total array list i have so it
                if (pageCount < 2) {

                    if (total == totalItems) {

                        // Execute some code after 15 seconds have passed
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                for (int i = 0; i < arrayS2.length; i++) {
                                    list.add(arrayS2[i]);
                                }
                                ad.notifyDataSetChanged();
                                listView.setAdapter(ad);
                                listView.setSelection(totalItems);
                                pageCount++;
                            }
                        }, 15000);
                    }
                } else {
                    Log.e("hide footer", "footer hide");
                    listView.removeFooterView(footer);
                }
            }
        }); */

        listView = (ListView) findViewById(R.id.load);
        footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.progress, null, false);
        //listView.addFooterView(footer);

        userList = new ArrayList<>();
        adapter = new UserAdapter(this, R.layout.user_list, userList);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                User user = (User) parent.getItemAtPosition(position);
                Log.i("item", "clicked: "+user.getUsername());
                Toast.makeText(getApplicationContext(),
                        user.getUsername(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                if((lastInScreen == totalItemCount) && !(loadingMore)){
                        List<User> userDataList = UserUtils.getUsers(p);
                        for(User u : userDataList){
                            userList.add(u);
                            adapter.add(u);
                        }
                        adapter.notifyDataSetChanged();
                        loadingMore = false;
                        p += 10;
                    //listView.removeFooterView(footer);
                }
            }
        });

    }


    private class UserAdapter extends ArrayAdapter<User> {

        private ArrayList<User> users;

        public UserAdapter(Context context, int textViewResourceId, List<User> users){
            super(context, textViewResourceId, users);
            this.users = new ArrayList<>();
            this.users.addAll(users);
        }

        private class ViewHolder{
            TextView username;
            TextView name;
            ImageView profileImg;
        }

        public void add(User user){
            this.users.add(user);
        }


        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolder holder = null;
            if(convertView == null){
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.user_list, null);

                holder = new ViewHolder();
                holder.username = (TextView) convertView.findViewById(R.id.username);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.profileImg = (ImageView) convertView.findViewById(R.id.roundedimage);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            User user = this.users.get(position);
            holder.username.setText(user.getUsername());
            holder.name.setText(user.getEmailId());

            Picasso.with(getApplicationContext())
                    .load("https://avatars.githubusercontent.com/u/12725702?v=4")
                    .into(holder.profileImg);

            return convertView;
        }
    }

}
