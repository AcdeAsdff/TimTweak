package com.linearity.timtweak;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class StartHook implements IXposedHookLoadPackage , IXposedHookInitPackageResources ,IXposedHookZygoteInit{
    public XC_InitPackageResources.InitPackageResourcesParam resparam;
    public String modulePath;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        HookTIMClass.DoHook(lpparam);
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        this.resparam = resparam;
        HookTIMClass.DoColor(this);
    }
    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) {
        this.modulePath = startupParam.modulePath;
    }
}
