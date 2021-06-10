package com.example.shiv.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.shiv.myapplication.config.SessionManager;

public class DashboardActivity extends BaseActivity {

    private SessionManager sessionManager;

    private String TAG="GridViewActivity";

    Activity activity;
    GridView gridView;
    TextView viewText;

    static final String[] numbers = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        sessionManager = SessionManager.INSTANCE;

        activity=this;

        gridView = _findViewById(R.id.gridView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, numbers);

        gridView.setAdapter(adapter);

        viewText = _findViewById(R.id.viewText);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int gridView, long id) {
                CharSequence tx = v==null ? "" : ((TextView) v).getText();
                Log.v(TAG,"onItemClick:("+gridView + ","+ id + "," + tx + ")");
//			  Toast.makeText(activity,tx, Toast.LENGTH_SHORT).show();
                if("".equals(tx))return;
                viewText.setText(viewText.getText() + ","+tx);
            }
        });

        gridView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event){


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_POINTER_2_DOWN:
                    case MotionEvent.ACTION_POINTER_3_DOWN:
                    case 0x305:
                    case 0x405:
                        Log.v(TAG,"onTouch:ACTION_DOWN");
                        if(Build.VERSION.SDK_INT>=5){
                            int pointCount = event.getPointerCount();
                            for (int p = 0; p < pointCount; p++) {
                                int px=(int)event.getX(p);
                                int py=(int)event.getY(p);
                                clickItemView(px, py);
                            }
                        }
                        else{
                            int px=(int)event.getX();
                            int py=(int)event.getY();
                            clickItemView(px, py);
                        }

                        break;
                }


//				int pv = gridView.pointToPosition((int)event.getX(), (int)event.getY());
//				long id = gridView.pointToRowId((int)event.getX(), (int)event.getY());
//				View lv = gridView.getChildAt(pv);
//				gridView.performItemClick(lv, pv, id);
                return true;
            }
        });
    }

    private void clickItemView(int px, int py) {
        int pv = gridView.pointToPosition(px,py);
        long id = gridView.pointToRowId(px,py);
        View lv = gridView.getChildAt(pv);
        Log.v(TAG,"HIT:"+isViewContains(lv,px,py));
        gridView.performItemClick(lv, pv, id);
    }

    private boolean isViewContains(View view, int rx, int ry) {
        if(view==null)return false;
//	    int[] l = new int[2];
//	    view.getLocationOnScreen(l);
        int x = view.getLeft();// l[0];
        int y = view.getTop();//l[1];
        int w = view.getWidth();
        int h = view.getHeight();

        Log.v(TAG,"(x,y,x+w,y+h,rx,ry)=("+x+","+y+","+(x+w)+","+(y+h)+","+rx+","+ry+")");

        if (rx < x || rx > x + w || ry < y || ry > y + h) {
            return false;
        }
        return true;
    }

    protected <T extends View> T _findViewById(final int id){
        return (T)findViewById(id);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(SessionManager.INSTANCE ==null){
            signOut();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showUserProfile(View view){
        // Closing ActivityMain and redirecting on DashboardActivity
        Intent intent = new Intent( DashboardActivity.this , UserActivity.class);
        startActivity(intent);
    }

    @Override
    public void base(View view) {
        userService.userTest();
        Log.i("B" , "Base Activity "+sessionManager.getUser());
    }

}
