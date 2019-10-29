package com.zcm.camera.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.Toast;

import com.zcm.aidl.IDemoAidl;
import com.zcm.aidl.Person;
import com.zcm.camera.R;
import com.zcm.ui.basearch.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AidlClientActivity extends BaseActivity {
    @BindView(R.id.aidl_bt)
    Button bt_aidl;
    IDemoAidl aidl;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidl = IDemoAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        Intent intent=new Intent();
        intent.setComponent(new ComponentName("com.zcm.thunder","com.zcm.thunder.aidl.AidlService"));
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @OnClick(R.id.aidl_bt)
    public void onClick() {
        try {
            List<Person> personList = aidl.getPersonList();
            Toast.makeText(mThisActivity,personList.toString(),Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
