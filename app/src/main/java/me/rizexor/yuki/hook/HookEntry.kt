package me.rizexor.yuki.hook

import android.app.AlertDialog
import android.location.Location
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.classOf
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.factory.toClass
import com.highcapable.yukihookapi.hook.log.loggerD
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.java.AnyArrayClass
import com.highcapable.yukihookapi.hook.type.java.ArrayClass
import com.highcapable.yukihookapi.hook.type.java.ArrayListClass
import com.highcapable.yukihookapi.hook.type.java.BooleanType
import com.highcapable.yukihookapi.hook.type.java.UnitType
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import de.robv.android.xposed.XposedBridge
import me.rizexor.yuki.BuildConfig

@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {
    val LocationType get() = Location::class.javaPrimitiveType ?: "location".toClass()
    val LocationArrayClass get() = ArrayClass(LocationType)

    override fun onInit() = configs {
        isDebug = BuildConfig.DEBUG
        loggerD(msg = "UTS Mod Initialized")
    }

    override fun onHook() = encase {
        loadApp(name = "com.cris.utsmobile") {
            findClass(name = "com.cris.ima.utsonmobile.journeybooking.BookJrnyTicketActivity").hook {
                injectMember {
                    method {
                        name = "isRooted"
                        emptyParam()
                        returnType = BooleanType
                    }
                    replaceAny {
                        loggerD(msg = "isRooted called")
                        true
                    }
                }
            }
            findClass(name = "com.cris.uts.location.HelpToGetRealLocation").hook {
                injectMember {
                    method {
                        name = "isAnyLastThreeCoordinatesSame"
                        param(LocationArrayClass)
                        returnType = BooleanType
                    }
                    replaceAny {
                        loggerD(msg = "isAnyLastThreeCoordinatesSame")
                        false
                    }
                }
            }
        }
    }
}