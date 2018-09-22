package com.example.raindi.pumpercontrol.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.example.raindi.pumpercontrol.R;
import com.example.raindi.pumpercontrol.data.InfoEntityData;
import com.example.raindi.pumpercontrol.entities.PumperControlEntity;
import com.example.raindi.pumpercontrol.entities.PumperDisplayEntity;
import com.example.raindi.pumpercontrol.http.QueryProtocol;
import com.example.raindi.pumpercontrol.utils.Logger;
import com.example.raindi.pumpercontrol.widget.DashboardView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    final String URL = "url";
    final String SENDURL = "sendUrl";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private DashboardView mDashboardView;

    private String TAG = "yjx";
    private Button mFailLess, mFailMore,mFailOver,mFailSensor,mFailCurrent,mFailWaterLess,mSend,mTimingSend;
    private Switch mSwitchTiming;
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
    private EditText mTarget,mSetTimer;
    private LinearLayout mDeviceIdL;
    private TextView mDeviceStatus,mWaterPressureMode,mWaterFlowMode,mTimerMode,mCurrentWaterPressure,mRunningStatus,mRealTimeTiming,mTargetPressure;
    private PumperControlEntity controlEntity;
    private PumperControlEntity.ParasBean parasBean;
    private int selectId =0,etWater = 0,etTime = 0;
    private ImageView mAddWP,mReduceWP,mSwitch,mReduceTime,mAddTime;
    private AlertDialog stateDialog;
    private PumperDisplayEntity.ResultsBean resultsBean;
    private long time=20*1000;//倒计时的总时间 ms
    private CountDownTimer downTimer;
    private  boolean timeRunning = true;
    private boolean execQueryFlag = true;


    @Override
    protected void onResume() {
        super.onResume();
        preferences = getSharedPreferences("NETWORK_SETTING", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        String beforeUrl = preferences.getString(URL,"https://eiiman.raindi.net/api/pumper.json");
        String beforeSnedUrl = preferences.getString(SENDURL,"https://eiiman.raindi.net/api/pumperctl");

        System.out.println(beforeUrl);
        System.out.println(beforeSnedUrl);

        InfoEntityData.setUrl(beforeUrl);
        InfoEntityData.setSendUrl(beforeSnedUrl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mDashboardView = (DashboardView) findViewById(R.id.tv_water_pressure_set_dashboard);

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
        mRunningStatus = findViewById(R.id.tv_running_status);
        mTimerMode = findViewById(R.id.tv_timer);
//        mSwitchTiming = findViewById(R.id.switch_timing);
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

        mReduceTime = findViewById(R.id.iv_reduce_time);
        mAddTime = findViewById(R.id.iv_add_time);
        mTimingSend = findViewById(R.id.btn_timing_send);
        mSetTimer = findViewById(R.id.et_set_timer);
        mRealTimeTiming  = findViewById(R.id.tv_real_time_timing);
        mTargetPressure = findViewById(R.id.tv_target_pressure);


        mSend.setSelected(true);
        mTarget.clearFocus();
        mTimerMode.setSelected(true);

        mSwitch.setOnClickListener(this);
        mWaterPressureMode.setOnClickListener(this);
        mWaterFlowMode.setOnClickListener(this);
        mTimerMode.setOnClickListener(this);

        mSend.setOnClickListener(this);
        mAddWP.setOnClickListener(this);
        mReduceWP.setOnClickListener(this);

        mReduceTime.setOnClickListener(this);
        mAddTime.setOnClickListener(this);
        mTimingSend.setOnClickListener(this);
//        mSwitchTiming.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            switch (buttonView.getId()){
//                case R.id.switch_timing:
//                    if(isChecked){
////                        execQueryFlag = false;
////                        timePickerPopWin();  // 设定时间
////                        mSetTimer.setEnabled(true);
////                        timeRunning = true;
//                    }else {
//                        try{
//                            downTimer.cancel();//暂停
//                        }catch (Exception e){
//                            System.out.println("-----------------------定时器未开启，不需要关闭");
//                        }finally {
//                            mSetTimer.setEnabled(false);
//                            timeRunning = false;
//                            parasBean.setSetTiming(0);
//
//                            InfoEntityData.setMsgType(4);
//
//                            if (InfoEntityData.getMsgType() == 0){
//                                System.out.println("错误");
//                                return;
//                            }
//                            parasBean.setMsgType(InfoEntityData.getMsgType());
//                            parasBean.setRefreshData(1);  // 数据刷新
//                            controlEntity.setParas(parasBean);
//                            singleThreadExecutor.execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    String result = (new QueryProtocol(getApplicationContext())).sendControl(controlEntity);
//                                    Logger.D("control result:"+result);
//                                    if (result != null && result.equals("\"ok\"")){
//                                        System.out.println("定时器已关闭");
//                                    }
//                                }
//                            });
//
//                        }
//
//                    }
//                    break;
//            }
    }

    @Override
    public void onClick(View view) {
        if (controlEntity == null || parasBean == null){
            return;
        }
        InfoEntityData.setMsgType(0);
        switch (view.getId()){
            case R.id.iv_switch:
//                msgType = 2;
                InfoEntityData.setMsgType(2);
                if (parasBean.getStatus() == OPEN){
                    parasBean.setStatus(CLOSE);
                }else if (parasBean.getStatus() == CLOSE){
                    parasBean.setStatus(OPEN);
                }
                break;

            case R.id.tv_timer:
                if(parasBean.getSetTiming() == 0){
                    mReduceTime.setEnabled(true);
                    mAddTime.setEnabled(true);
                    mTimingSend.setSelected(true);
                    mTimingSend.setEnabled(true);
                    mSetTimer.setEnabled(true);
                }else{
                    mReduceTime.setEnabled(false);
                    mAddTime.setEnabled(false);
                    mTimingSend.setSelected(false);
                    mTimingSend.setEnabled(false);
                    mTimerMode.setSelected(false);
                    parasBean.setSetTiming(0);
                    mSetTimer.setEnabled(false);
                    timeRunning = false;
                    mRealTimeTiming.setText(" ");
                    InfoEntityData.setMsgType(4);
                }

                break;

            case R.id.et_set_timer:

                break;
            case R.id.iv_reduce_time:
                int timeReduce = Integer.parseInt(mSetTimer.getText().toString())-1;
                if (timeReduce < 0){
                    timeReduce = 0;
                }else if (timeReduce > 999){
                    timeReduce = 999;
                }
                String timeReduce1 = timeReduce +"";
                mSetTimer.setText(timeReduce1);
                mSetTimer.setSelection(mSetTimer.getText().toString().length());

                break;
            case R.id.iv_add_time:
                int timeAdd = Integer.parseInt(mSetTimer.getText().toString())+1;
                if (timeAdd < 0){
                    timeAdd = 0;
                }else if (timeAdd > 999){
                    timeAdd = 999;
                }
                String timeAdd1 = timeAdd +"";
                mSetTimer.setText(timeAdd1);
                mSetTimer.setSelection(mSetTimer.getText().toString().length());

                break;

            case R.id.btn_timing_send:
                int time = Integer.parseInt(mSetTimer.getText().toString());
                System.out.println("发送");
                parasBean.setSetTiming(time);
                InfoEntityData.setMsgType(4);
                break;

            case R.id.btn_send:
                try {
                    int sendTarget;
                    //int target = Integer.parseInt(mTarget.getText().toString());  //这里的人target是从activity中获取的，是小数点的字符串类型
                    double target = Double.valueOf(mTarget.getText().toString());
                    if (((double)(parasBean.getWaterPressur())) == target){
                        return;
                    }
                    sendTarget = (int) (target*100);
                    parasBean.setWaterPressur(sendTarget);
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>send:"+sendTarget);
//                    msgType = 1;
                    InfoEntityData.setMsgType(1);
                }
                catch(Exception e) {
                    System.out.println("---------------------发送错误----------------------------");
                }

                break;
            case R.id.iv_add_water_pressure:
                int waterPrsAdd = Integer.parseInt(mTarget.getText().toString())+5;
//                double waterPrsAdd = Double.valueOf(mTarget.getText().toString()) + 0.01;
//                DecimalFormat df = new DecimalFormat("0.00");
                if (waterPrsAdd < 0){
                    waterPrsAdd = 0;
                }else if (waterPrsAdd > 800){
                    waterPrsAdd = 800;
                }
                String waterPressure = waterPrsAdd+"";
//                String waterPressure = df.format(waterPrsAdd);
                mTarget.setText(waterPressure);
                mTarget.setSelection(mTarget.getText().toString().length());
                break;
            case R.id.iv_reduce_water_pressure:
                // Bar单位显示
//                double waterPrsReduce = Double.valueOf(mTarget.getText().toString()) - 0.01;
//                DecimalFormat rd = new DecimalFormat("0.00");
//                String waterPressure1 = rd.format(waterPrsReduce);
//                mTarget.setText(waterPressure1);
                int waterPrsReduce = Integer.parseInt(mTarget.getText().toString())-5;
                if (waterPrsReduce < 0){
                    waterPrsReduce = 0;
                }else if (waterPrsReduce > 800){
                    waterPrsReduce = 800;
                }
                String waterPressure1 = waterPrsReduce+"";
                mTarget.setText(waterPressure1);
                mTarget.setSelection(mTarget.getText().toString().length());
                break;
            case R.id.tv_water_pressure:
                if (parasBean.getMode() == WATER_PRESSURE_MODE){
                    return;
                }
                parasBean.setMode(WATER_PRESSURE_MODE);
//                msgType = 3;
                InfoEntityData.setMsgType(3);
                break;
            case R.id.tv_water_flow:
                if (parasBean.getMode() == WATER_FLOW_MODE){
                    return;
                }
                parasBean.setMode(WATER_FLOW_MODE);
//                msgType = 3;
                InfoEntityData.setMsgType(3);
                break;
        }
        sendMsg(); // 发送消息
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {   // 当选择发生改变时触发

        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {
            selectId = position;
            timeRunning = true;
            setPumpDisplay(position);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void setPumpDisplay(int position) {

        preferences = getSharedPreferences("UNIT_SWITCH", Activity.MODE_PRIVATE);

        editor.putString(URL,"https://eiiman.raindi.net/api/pumper.json");
        editor.putString(SENDURL,"https://eiiman.raindi.net/api/pumperctl");

        if (resultsBeans.size()==0){
            return;
        }
        resetButton();
        try {
            resultsBean = resultsBeans.get(position);
        }catch (Exception e){
            System.out.println("不知道的崩溃...");
        }

        int key = resultsBean.getKey();
        int mode = resultsBean.getMode();
        int target = resultsBean.getTarget(); //从数据库获取的数据，获取到的数据例如：240
        int currentWaterPressure = resultsBean.getPressure();
        int error = resultsBean.getError();
        int realTimeTiming = resultsBean.getRealTimeTiming();
        int runningStatus = resultsBean.getRunningStatus();
        int targetTime = resultsBean.getTargetTime();
        int waterLessStatus = resultsBean.getWaterLessStatus();

        String status = resultsBean.getStatus();
        Logger.D("position:"+position+",name:"+resultsBean.getDeviceName()+",key:"+key+",mode:"+mode+",error:"+error);
        String deviceId = resultsBean.getDeviceId();
        controlEntity = new PumperControlEntity();
        controlEntity.setDeviceId(deviceId);
        parasBean = new PumperControlEntity.ParasBean();
        parasBean.setMode(mode);
        parasBean.setWaterPressur(target);
        parasBean.setStatus(key);
        parasBean.setSetTiming(realTimeTiming);

        if (target < 0){
            target = 0;
        }else if (target > 800){
            target = 800;
        }
        //Logger.D("target:"+target+",et:"+etWater);
        // 直接以kPa单位显示
        if (etWater != target){
            etWater = target;
            mTarget.setText(target+"");
            mTarget.setSelection(mTarget.getText().toString().length());
        }

        if (etTime != realTimeTiming){
            etTime = realTimeTiming;
            mSetTimer.setText(realTimeTiming + "");
            mSetTimer.setSelection(mSetTimer.getText().toString().length());
        }

        mTarget.setSelection(mTarget.getText().toString().length());
        mTargetPressure.setText(getString(R.string.set_target_pressure,target));


        realTimeTiming = 10;
        if(timeRunning & (realTimeTiming != 0)){  // 当时间定时器时间不为0时候，开启定时
            try{
                downTimer.cancel();//暂停
            }catch (Exception e){
                            System.out.println("-----------------------定时器未开启，不需要关闭");
            }finally {
                mReduceTime.setEnabled(true);
                mAddTime.setEnabled(true);
                mTimingSend.setSelected(true);
                mTimingSend.setEnabled(true);
                timeRunning = false;
                mTimerMode.setSelected(true);
                openTimer(realTimeTiming);         //打开计时器，没开启
                downTimer.start();//开始倒计时
            }
        }


//        if (parasBean.getSetTiming() == 0){
//            mSwitchTiming.setChecked(false);
//            mSetTimer.setEnabled(false);
//        }

        // Bar与kg/cm2单位显示
//        double targetShow;
//        if (etWater != target){
//            etWater = target;
//            // 对数据进行转换
//            targetShow = target*0.01;
//            if(preferences.getBoolean("unitSwitchFlag",true)){
//
//            }else {
//                targetShow = targetShow*1.02;
//            }
//            mTarget.setText(targetShow+"");
//            mTarget.setSelection(mTarget.getText().toString().length());
//        }

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

        if (currentWaterPressure < 0){
            currentWaterPressure = 0;
        }else if (currentWaterPressure > 800){
            currentWaterPressure = 800;
        }

        // 以kPa显示方式
        final int currentNum=  currentWaterPressure;
        String waterPressure = currentNum + "";

        // 动态仪表
        new Thread(){
            @Override
            public void run() {
                super.run();
                if( DashboardView.getmRealTimeValue() <= currentNum){
                    for(int i = DashboardView.getmRealTimeValue();i<=currentNum;i++){
                        try {
                            sleep(3);
                            mDashboardView.setRealTimeValue(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }else if(DashboardView.getmRealTimeValue() >= currentNum){
                    for(int i = DashboardView.getmRealTimeValue();i >= currentNum;i--){
                        try {
                            sleep(3);
                            mDashboardView.setRealTimeValue(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

        mCurrentWaterPressure.setText(getString(R.string.set_water_pressure_kpa,waterPressure));

        // 已Bar显示的方式
//        double num= (double) currentWaterPressure/100;
//        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，.后跟几个零代表几位小数
//
//        if(preferences.getBoolean("unitSwitchFlag",true)){
//            String waterPressure = df.format(num);
//            mCurrentWaterPressure.setText(getString(R.string.set_water_pressure,waterPressure));
//        }else {
//            num = num * 1.02;
//            String waterPressure = df.format(num);
//            mCurrentWaterPressure.setText(getString(R.string.set_water_pressure_kg,waterPressure));
//        }

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
                mWaterFlowMode.setSelected(false);
                break;
        }

        if(runningStatus == 1){
            mRunningStatus.setSelected(true);
        }else {
            mRunningStatus.setSelected(false);
        }

//        if (lackWater == 1){  //缺水
//            mFailWaterLess.setActivated(true);
//        }

        if(waterLessStatus == 1){ //缺水
            mFailWaterLess.setActivated(true);
        }

        switch (error){
            case LESS: // 欠压
                mFailLess.setActivated(true);
                break;
            case MORE: //过压
                mFailMore.setActivated(true);
                break;
            case SENSOR: //传感器
                mFailSensor.setActivated(true);
                break;
            case CURRENT: //过流
                mFailCurrent.setActivated(true);
                break;
            case OVER://过载
                mFailOver.setActivated(true);
                break;
        }
    }

    private void resetButton(){
        mSwitch.setSelected(false);
        mSwitch.setActivated(false);
        mWaterPressureMode.setSelected(false);
        mWaterFlowMode.setSelected(false);

//        mTimerMode.setSelected(false);

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
//                getInfoEntityData().setInfoEntity(queryProtocol.query());
                getInfoEntityData().setInfoEntity((new QueryProtocol(getApplicationContext())).query());
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
                if(execQueryFlag){
                    execQuery();
                }
                else{
                }
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
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

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

        if (id == R.id.nav_network_settings) {
            Intent intent = new Intent(MainActivity.this,NetworkSettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_question) {
            Uri uri = Uri.parse("http://bbs.raindi.net/forum.php");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(uri);
            startActivity(intent);

        } else if (id == R.id.nav_refresh) {
            InfoEntityData.setUrl("https://eiiman.raindi.net/api/pumper.json");
            InfoEntityData.setSendUrl("https://eiiman.raindi.net/api/pumperctl");

            preferences = getSharedPreferences("NETWORK_SETTING", Activity.MODE_PRIVATE);
            editor = preferences.edit();

            editor.putString(URL,"https://eiiman.raindi.net/api/pumper.json");
            editor.putString(SENDURL,"https://eiiman.raindi.net/api/pumperctl");

            if(editor.commit()){
                Toast.makeText(getApplicationContext(), "重置成功", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_copyright) {
            Intent intent = new Intent(MainActivity.this,CopyrightActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_unit_switch){
            if (parasBean.getMode() == WATER_PRESSURE_MODE){
                parasBean.setMode(WATER_FLOW_MODE);
                InfoEntityData.setMsgType(3);
//                Toast.makeText(getApplicationContext(), "水流模式", Toast.LENGTH_SHORT).show();
            }else if(parasBean.getMode() == WATER_FLOW_MODE){
                parasBean.setMode(WATER_PRESSURE_MODE);
                InfoEntityData.setMsgType(3);
//                Toast.makeText(getApplicationContext(), "水压模式", Toast.LENGTH_SHORT).show();
            }

            sendMsg(); // 发送消息

//            preferences = getSharedPreferences("UNIT_SWITCH", Activity.MODE_PRIVATE);
//            editor = preferences.edit();
//            if(preferences.getBoolean("unitSwitchFlag",true)){
//                editor.putBoolean("unitSwitchFlag",false);
//                if(editor.commit()){
//                    Toast.makeText(getApplicationContext(), "切换成功", Toast.LENGTH_SHORT).show();
//                }
//            }else {
//                editor.putBoolean("unitSwitchFlag",true);
//                if(editor.commit()){
//                    Toast.makeText(getApplicationContext(), "切换成功", Toast.LENGTH_SHORT).show();
//                }
//            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // 发送消息
    public void sendMsg(){
        if (InfoEntityData.getMsgType() == 0){
            return;
        }
//        parasBean.setMsgType(msgType);
        parasBean.setMsgType(InfoEntityData.getMsgType());
        parasBean.setRefreshData(1);  // 数据刷新
        controlEntity.setParas(parasBean);
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
//                String result = queryProtocol.sendControl(controlEntity);
                String result = (new QueryProtocol(getApplicationContext())).sendControl(controlEntity);
                Logger.D("control result:"+result);
                if (result != null && result.equals("\"ok\"")){
                    hasSendCMD();
                }
            }
        });
    }

    // 计时器
    private void initTimer() {
        //1000 表示 每过1000ms 执行一次 onTick()
        downTimer = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time -= 1000;

                System.out.println("---------------------minute:" + time / 1000);
                if(time >= 0){
                    mRealTimeTiming.setText(time / 1000 + "");
                }

            }


            @Override
            public void onFinish() {
                mReduceTime.setEnabled(false);
                mAddTime.setEnabled(false);
                mTimingSend.setSelected(false);
                mTimingSend.setEnabled(false);
                mTimerMode.setSelected(false);
                mSetTimer.setEnabled(false);
                timeRunning = false;

                mRealTimeTiming.setText(" ");

                InfoEntityData.setMsgType(2);
                if (parasBean.getStatus() == OPEN){
                    parasBean.setStatus(CLOSE);
                }

                if (InfoEntityData.getMsgType() == 0){
                    return;
                }
                parasBean.setMsgType(InfoEntityData.getMsgType());
                parasBean.setRefreshData(1);  // 数据刷新
                controlEntity.setParas(parasBean);
                singleThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result = (new QueryProtocol(getApplicationContext())).sendControl(controlEntity);
                        Logger.D("control result:"+result);
                        if (result != null && result.equals("\"ok\"")){
                            System.out.println("-------------------------------设备关闭");

                        }
                    }
                });
            }
        };
    }



    public void openTimer(int time_second){
        time = time_second * 1000;  // 设定时间
        initTimer();

    }

    public void showTimehhmmss(int hour,int minute,int second){
        String hourString = hour + "";
        String minuteString = minute + "";
        String secondString = second + "";

        if(hourString.length() == 1){
            hourString = "0" + hourString;
        }
        if(minuteString.length() == 1){
            minuteString = "0" + minuteString;
        }
        if(secondString.length() == 1){
            secondString = "0" + secondString;
        }

//        mSetTimer.setText(hourString + ":" + minuteString + ":" + secondString);
    }
}
