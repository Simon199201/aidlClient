// IParticipateCallback.aidl
package com.jikexuyuan.ndk.aidl;

// Declare any non-default types here with import statements

interface IParticipateCallback {
    // 用户加入或者离开的回调
    void onParticipate(String name, boolean joinOrLeave);
}
