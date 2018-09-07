package com.example.raindi.pumpercontrol.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raindi.pumpercontrol.R;
import com.example.raindi.pumpercontrol.data.InfoEntityData;
import com.example.raindi.pumpercontrol.entities.PumperControlEntity;
import com.example.raindi.pumpercontrol.entities.PumperDisplayEntity;
import com.example.raindi.pumpercontrol.http.QueryProtocol;
import com.example.raindi.pumpercontrol.utils.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private String TAG = "yjx";
    private Button mFailLess, mFailMore,mFailOver,mFailSensor,mFailCurrent,mFailWaterLess,mSend;
    public static boolean mChecked = true;
    ExecutorService singleThreadExecutor;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    QueryProtocol queryProtocol;
    private List<PumperDisplayEntity.ResultsBean> resultsBeans = new ArrayList<>();
    private List<String> deviceIds = new ArrayList<>(),tempList = new ArrayList<>();
    private final int OPEN = 1;
    private final int CLOSE = 0;
    private final int WATER_PRESSURE_MODE = 0;
    private final int WATER_FLOW_MODE = 1;
    private final int LESS = 1;
    private final int MORE = 2;
    private final int OVER = 6;
    private final int SENSOR = 3;
    private final int CURRENT = 5;
    private EditText mTarget;
    private LinearLayout mDeviceIdL;
    private TextView mDeviceStatus,mWaterPressureMode,mWaterFlowMode,mTimerMode,mCurrentWaterPressure;
    private PumperControlEntity controlEntity;
    private PumperControlEntity.ParasBean parasBean;
    private int selectId =0,etWater = 0;
    private ImageView mAddWP,mReduceWP,mSwitch;
    private AlertDialog stateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();

        spinner = findViewById(R.id.sp_select);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,deviceIds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        queryProtocol = new QueryProtocol(getApplicationContext());
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        handler.postDelayed(runnable, 1000);

    }
    private void initView() {
        mWaterPressureMode = findViewById(R.id.tv_water_pressure);
        mWaterFlowMode = findViewById(R.id.tv_water_flow);
        mTimerMode = findViewById(R.id.tv_timer);
        mFailLess = findViewById(R.id.btn_water_pressure_less);
        mFailMore = findViewById(R.id.btn_water_pressure_more);
        mFailOver = findViewById(R.id.btn_overload);
        mFailSensor = findViewById(R.id.btn_water_pressure_sensor_exception);
        mFailCurrent = findViewById(R.id.btn_excessive_current);
        mFailWaterLess = findViewById(R.id.btn_water_less);
        mSend = findViewById(R.id.btn_send);
        mSwitch = findViewById(R.id.iv_switch);
        mCurrentWaterPressure = findViewById(R.id.tv_current_water_pressure);
        mTarget = findViewById(R.id.et_water_pressure_set);
        mDeviceIdL = findViewById(R.id.ll_deviceId);
        mDeviceStatus = findViewById(R.id.tv_device_status);
        mAddWP = findViewById(R.id.iv_add_water_pressure);
        mReduceWP = findViewById(R.id.iv_reduce_water_pressure);


        mSend.setSelected(true);
        mTarget.clearFocus();

        mSwitch.setOnClickListener(this);
        mWaterPressureMode.setOnClickListener(this);
        mWaterFlowMode.setOnClickListener(this);
        mTimerMode.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mAddWP.setOnClickListener(this);
        mReduceWP.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (controlEntity == null || parasBean == null){
            return;
        }
        int msgType = 0;
        switch (view.getId()){
            case R.id.iv_switch:
                msgType = 2;
                if (parasBean.getStatus() == OPEN){
                    parasBean.setStatus(CLOSE);
                }else if (parasBean.getStatus() == CLOSE){
                    parasBean.setStatus(OPEN);
                }
                break;
            case R.id.tv_water_pressure:
                if (parasBean.getMode() == WATER_PRESSURE_MODE){
                    return;
                }
                parasBean.setMode(WATER_PRESSURE_MODE);
                msgType = 3;
                break;
            case R.id.tv_water_flow:
                if (parasBean.getMode() == WATER_FLOW_MODE){
                    return;
                }
                parasBean.setMode(WATER_FLOW_MODE);
                msgType = 3;
                break;
            case R.id.tv_timer:
                break;
            case R.id.btn_send:
                try {
                    int sendTarget;
                    //int target = Integer.parseInt(mTarget.getText().toString());  //这里的人target是从activity中获取的，是小数点的字符串类型
                    double target = Double.valueOf(mTarget.getText().toString());
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+target);
                    if (((double)(parasBean.getWaterPressur()/100)) == target){
                        return;
                    }
                    sendTarget = (int) (target*100);
                    parasBean.setWaterPressur(sendTarget);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>send:"+sendTarget);
                    msgType = 1;
                }
                catch(Exception e) {
                    System.out.println("---------------------发送错误----------------------------");
                }

                break;
            case R.id.iv_add_water_pressure:
                //int waterPrsAdd = Integer.parseInt(mTarget.getText().toString())+5;
                double waterPrsAdd = Double.valueOf(mTarget.getText().toString()) + 0.01;
                DecimalFormat df = new DecimalFormat("0.00");
                //String waterPressure = waterPrsAdd+"";
                String waterPressure = df.format(waterPrsAdd);
                mTarget.setText(waterPressure);
                mTarget.setSelection(mTarget.getText().toString().length());
                break;
            case R.id.iv_reduce_water_pressure:
                double waterPrsReduce = Double.valueOf(mTarget.getText().toString()) - 0.01;
                DecimalFormat rd = new DecimalFormat("0.00");
                String waterPressure1 = rd.format(waterPrsReduce);
                mTarget.setText(waterPressure1);
                mTarget.setSelection(mTarget.getText().toString().length());
                break;
        }
        if (msgType == 0){
            return;
        }
        parasBean.setMsgType(msgType);
        controlEntity.setParas(parasBean);
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                String result = queryProtocol.sendControl(controlEntity);
                Logger.D("control result:"+result);
                if (result != null && result.equals("\"ok\"")){
                    hasSendCMD();
                }
            }
        });
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {
            selectId = position;
            setPumpDisplay(position);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void setPumpDisplay(int position) {
        if (resultsBeans.size()==0){
            return;
        }
        resetButton();
        PumperDisplayEntity.ResultsBean resultsBean= resultsBeans.get(position);
        int key = resultsBean.getKey();
        int mode = resultsBean.getMode();
        int target = resultsBean.getTarget(); //从数据库获取的数据，获取到的数据例如：240
        int currentWaterPressure = resultsBean.getPressure();
        int error = resultsBean.getError();
        String status = resultsBean.getStatus();
        Logger.D("position:"+position+",name:"+resultsBean.getDeviceName()+",key:"+key+",mode:"+mode+",error:"+error);
        String deviceId = resultsBean.getDeviceId();
        controlEntity = new PumperControlEntity();
        controlEntity.setDeviceId(deviceId);
        parasBean = new PumperControlEntity.ParasBean();
        parasBean.setMode(mode);
        parasBean.setWaterPressur(target);
        parasBean.setStatus(key);
        if (target < 0){
            target = 0;
        }else if (target > 800){
            target = 800;
        }
        //Logger.D("target:"+target+",et:"+etWater);

        double targetShow;
        if (etWater != target){
            etWater = target;
            // 对数据进行转换
            targetShow = target*0.001;
            mTarget.setText(targetShow+"");
            mTarget.setSelection(mTarget.getText().toString().length());
        }

        int lackWater = 0;
        String errorStr = error+"";
        if (errorStr.length() == 2){
            lackWater = 1;
            error = Integer.parseInt(errorStr.substring(1,2));
        }

        if (status != null){
            mDeviceStatus.setText(status);
            mDeviceStatus.setVisibility(View.VISIBLE);
        } else {
            mDeviceStatus.setVisibility(View.GONE);
        }

        float num= (float)currentWaterPressure/100;
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，.后跟几个零代表几位小数
        String waterPressure = df.format(num);
        mCurrentWaterPressure.setText(getString(R.string.set_water_pressure,waterPressure));

        switch (key){
            case OPEN:
                mSwitch.setSelected(true);
                break;
            case CLOSE:
                mSwitch.setActivated(true);
                break;
        }

        switch (mode){
            case WATER_PRESSURE_MODE:
                mWaterPressureMode.setSelected(true);
                break;
            case WATER_FLOW_MODE:
                mWaterFlowMode.setSelected(true);
                break;
        }

        if (lackWater == 1){
            mFailWaterLess.setActivated(true);
        }

        switch (error){
            case LESS:
                mFailLess.setActivated(true);
                break;
            case MORE:
                mFailMore.setActivated(true);
                break;
            case SENSOR:
                mFailSensor.setActivated(true);
                break;
            case CURRENT:
                mFailCurrent.setActivated(true);
                break;
            case OVER:
                mFailOver.setActivated(true);
                break;
        }
    }

    private void resetButton(){
        mSwitch.setSelected(false);
        mSwitch.setActivated(false);
        mWaterPressureMode.setSelected(false);
        mWaterFlowMode.setSelected(false);
        mFailWaterLess.setActivated(false);
        mFailLess.setActivated(false);
        mFailMore.setActivated(false);
        mFailSensor.setActivated(false);
        mFailCurrent.setActivated(false);
        mFailOver.setActivated(false);
    }

    private void execQuery() {
        resultsBeans.clear();
        tempList.clear();
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //get info
//                PumperDisplayEntity infoEntity = queryProtocol.query();
                getInfoEntityData().setInfoEntity(queryProtocol.query());
                PumperDisplayEntity infoEntity = getInfoEntityData().getInfoEntity();

                if (infoEntity != null){
                    Log.d(TAG,"count:"+infoEntity.getCount());
                    if (infoEntity.getResults()!=null && infoEntity.getResults().size()>0){
                        resultsBeans = infoEntity.getResults();
                        for (PumperDisplayEntity.ResultsBean resultsBean : resultsBeans){
                            String deviceName = resultsBean.getDeviceName();
                            String [] name = deviceName.split("_");
                            if (name.length == 2){
                                String dName = "水泵"+name[1];
                                tempList.add(dName);
                            }else {
                                tempList.add(deviceName);
                            }
                        }

                        changeStatus();

                    }

                } else {
                    toastNet(getString(R.string.net_error));
                }
            }
        });
    }

    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (mChecked){
                execQuery();
                handler.postDelayed(this, 5000);
            }
        }
    };

    private static final int MSG_INFO_DISPLAY = 0;
    private static final int MSG_NET_STATUS = 1;
    private static final int MSG_SEND_CMD = 2;
    private static final String KEY_INFO = "key_info";
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            switch (message.what){
                case MSG_INFO_DISPLAY:
                    mDeviceIdL.setVisibility(View.VISIBLE);
                    deviceIds.clear();
                    deviceIds.addAll(tempList);
                    adapter.notifyDataSetChanged();
                    spinner.setSelection(selectId);
                    setPumpDisplay(selectId);
                    break;
                case MSG_NET_STATUS:
                    Bundle bundle = message.getData();
                    String s = bundle.getString(KEY_INFO);
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                    break;
                case MSG_SEND_CMD:
                    showStateDialog();
                    break;
            }
            return false;
        }
    });

    private void changeStatus() {
        Message msg = new Message();
        msg.what = MSG_INFO_DISPLAY;
        mHandler.sendMessage(msg);
    }

    private void hasSendCMD() {
        Message msg = new Message();
        msg.what = MSG_SEND_CMD;
        mHandler.sendMessage(msg);
    }

    private void toastNet(String info) {
        Message msg = new Message();
        msg.what = MSG_NET_STATUS;
        Bundle bundle = new Bundle();
        bundle.putString(KEY_INFO, info);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    private void showStateDialog(){
        if (stateDialog == null){
            stateDialog = new AlertDialog.Builder(this,R.style.Dialog).create();
            stateDialog.setButton(ProgressDialog.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        }
        stateDialog.setMessage(getString(R.string.result_ok));
        stateDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    public InfoEntityData getInfoEntityData(){
        return (InfoEntityData)getApplicationContext();
    }

    // 数据类型判断
    public static String getType(Object o){
        return o.getClass().toString();
    }
    public static String getType(int o){
        return "int";
    }
    public static String getType(byte o){
        return "byte";
    }
    public static String getType(char o){
        return "char";
    }
    public static String getType(double o){
        return "double";
    }
    public static String getType(float o){
        return "float";
    }
    public static String getType(long o){
        return "long";
    }
    public static String getType(boolean o){
        return "boolean";
    }
    public static String getType(short o){
        return "short";
    }
    public static String getType(String o){
        return "String";
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //菜单显示部分
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
