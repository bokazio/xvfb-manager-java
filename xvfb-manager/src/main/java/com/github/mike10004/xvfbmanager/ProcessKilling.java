package com.github.mike10004.xvfbmanager;

import com.github.mike10004.nativehelper.subprocess.ProcessDestructor;

import java.util.concurrent.TimeUnit;

class ProcessKilling {

    private ProcessKilling() {}

    public static void termOrKill(ProcessDestructor destructor, long timeout, TimeUnit duration) {
        destructor.sendTermSignal()
                .timeout(timeout, duration)
                .kill();
    }
}
