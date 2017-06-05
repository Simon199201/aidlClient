package com.jikexuyuan.ndk.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jikexuyuan.ndk.aidl.IParticipateCallback;

public class MainActivity extends AppCompatActivity {
//    private IRemoteService remoteService;
    private IBinder mBinder;
    private static final java.lang.String DESCRIPTOR = "com.jikexuyuan.ndk.aidl.IRemoteService";

    private IParticipateCallback iParticipateCallback = new IParticipateCallback.Stub() {
        @Override
        public void onParticipate(String name, boolean joinOrLeave) throws RemoteException {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("begin bindService");
                Intent intent = new Intent();
                intent.setAction("duanqing.test.aidl");
                intent.setPackage("com.jikexuyuan.ndk.aidl");
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });

    }

//    ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mBinder = service;
//            remoteService = IRemoteService.Stub.asInterface(service);
//            try {
//                int pid = 0;
//                try {
//                    pid = remoteService.getPid();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                int currentPid = Process.myPid();
//                System.out.println("currentPID: " + currentPid + "  remotePID: " + pid);
//                remoteService.basicTypes(12, 1223, true, 12.2f, 12.3, "我们的爱，我明白");
//
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//            System.out.println("bind success! " + remoteService.toString());
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//    };

//不用aidl文件来实现
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = service;
            try {
                int pid = 0;
                try {
                    pid = getPid();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                int currentPid = Process.myPid();
                System.out.println("currentPID: " + currentPid + "  remotePID: " + pid);
                basicTypes(12, 1223, true, 12.2f, 12.3, "我们的爱，我明白");

            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println("bind success! " + mBinder.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    static final int TRANSACTION_basicTypes = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getPid = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_registerParticipateCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_unregisterParticipateCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);

    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, java.lang.String aString) throws android.os.RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            _data.writeInt(anInt);
            _data.writeLong(aLong);
            _data.writeInt(((aBoolean) ? (1) : (0)));
            _data.writeFloat(aFloat);
            _data.writeDouble(aDouble);
            _data.writeString(aString);
            mBinder.transact(TRANSACTION_basicTypes, _data, _reply, 0);
            _reply.readException();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }

    public int getPid() throws android.os.RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            mBinder.transact(TRANSACTION_getPid, _data, _reply, 0);
            _reply.readException();
            _result = _reply.readInt();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return _result;
    }

    public void registerParticipateCallback(com.jikexuyuan.ndk.aidl.IParticipateCallback cb) throws android.os.RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            _data.writeStrongBinder((((cb != null)) ? (cb.asBinder()) : (null)));
            mBinder.transact(TRANSACTION_registerParticipateCallback, _data, _reply, 0);
            _reply.readException();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }

    public void unregisterParticipateCallback(com.jikexuyuan.ndk.aidl.IParticipateCallback cb) throws android.os.RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            _data.writeStrongBinder((((cb != null)) ? (cb.asBinder()) : (null)));
            mBinder.transact(TRANSACTION_unregisterParticipateCallback, _data, _reply, 0);
            _reply.readException();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }
}
