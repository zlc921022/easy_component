package com.xiaochen.module.apm;


import android.content.Context;

import com.argusapm.android.api.Client;
import com.argusapm.android.core.Config;
import com.argusapm.android.network.cloudrule.RuleSyncRequest;
import com.argusapm.android.network.upload.CollectDataSyncUpload;
import com.xiaochen.common.base.BaseApplication;

/**
 * <p></p >
 *
 * @author zhenglecheng
 * @date 2020/5/12
 */
public class ApmApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Config.ConfigBuilder builder = new Config.ConfigBuilder()
                .setAppContext(this)
                .setAppName("app_apm")
                .setRuleRequest(new RuleSyncRequest())
                .setUpload(new CollectDataSyncUpload())
                .setAppVersion("0.0.1")
                .setApmid("app_apm");
        Client.attach(builder.build());
        Client.startWork();
    }
}
