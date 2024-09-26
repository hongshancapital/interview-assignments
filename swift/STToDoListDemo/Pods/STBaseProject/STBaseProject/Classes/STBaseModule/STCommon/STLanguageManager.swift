//
//  STLanguageManager.swift
//  STBaseProject
//
//  Created by stack on 2018/01/10.
//  Copyright © 2018 ST. All rights reserved.
//

import Foundation

struct STLanguageConstantKey {
    static var kBundleKey = "kBundleKey"
    let appLanguageSwitchKey = "App_Language_Switch_Key"
}

public class STLanguageManager: Bundle {
    
    deinit {
        objc_removeAssociatedObjects(self)
    }
    
    public override func localizedString(forKey key: String, value: String?, table tableName: String?) -> String {
        if let bundle = objc_getAssociatedObject(self, &STLanguageConstantKey.kBundleKey) as? Bundle {
            return bundle.localizedString(forKey: key, value: value, table: tableName)
        }
        return super.localizedString(forKey: key, value: value, table: tableName)
    }
}

public extension Bundle {
    static func st_localizedString(key: String) -> String {
        var string: String = ""
        let language = self.st_getCustomLanguage()
        if let lPath = Bundle.main.path(forResource: language, ofType: "lproj") {
            if let bundle = Bundle.init(path: lPath) {
                string = bundle.localizedString(forKey: key, value: nil, table: "Localizable")
            }
        } else {
            string = Bundle.main.localizedString(forKey: key, value: nil, table: "Localizable")
        }
        return string
    }
    
    /// 设置语言
    ///
    /// - Parameter language: 语言包的前缀，比如`Base.lproj`，传入`Base`
    ///
    /// `zh-Hans.lproj` 传入 `zh-Hans`，前提是工程中已经提前加入了语言包
    ///
    static func st_setCusLanguage(language: String) -> Void {
        let df = UserDefaults.standard
        if language.count > 0 {
            var newLanguage = language
            if newLanguage.contains(".lproj") {
                let components = newLanguage.components(separatedBy: ".")
                if components.count > 0 {
                    if let first = components.first {
                        newLanguage = first
                    }
                }
            }
            if let path = Bundle.main.path(forResource: newLanguage, ofType: "lproj") {
                if let bundle = Bundle.init(path: path) {
                    objc_setAssociatedObject(Bundle.main, &STLanguageConstantKey.kBundleKey, bundle, objc_AssociationPolicy.OBJC_ASSOCIATION_RETAIN_NONATOMIC)
                }
            }
            df.set(newLanguage, forKey: STLanguageConstantKey().appLanguageSwitchKey)
        } else {
            df.removeObject(forKey: STLanguageConstantKey().appLanguageSwitchKey)
        }
        df.synchronize()
    }
    
    /// 获取当前的自定义语言，如果使用的是跟随系统语言出参为 `nil`
    static func st_getCustomLanguage() -> String {
        let df = UserDefaults.standard
        var language = ""
        if let lan = df.value(forKey: STLanguageConstantKey().appLanguageSwitchKey) as? String {
            language = lan
        }
        
        if language.count < 1 {
            language = self.st_appSupportLanguage()
        }
        
        if language.contains("zh-Hans") {
            language = "zh-Hans"
        } else if language.contains("zh-Hant") {
            language = "zh-Hant"
        } else if language.contains("en") {
            language = "en"
        } else if language.contains("en-AU") {
            language = "en-AU"
        }
        return language
    }

    /// 恢复跟随系统语言
    static func st_restoreSysLanguage() -> Void {
        self.st_setCusLanguage(language: "")
    }
    
    private static func st_sysLanguage() -> String {
        let languages = NSLocale.preferredLanguages
        if languages.count > 0 {
            if let language = languages.first {
                return language
            }
        }
        return "en-CN"
    }
    
     static func st_appSupportLanguage() -> String {
        if let languages = UserDefaults.standard.value(forKey: "AppleLanguages") as? Array<String>, languages.count > 0 {
            if let language = languages.first {
                return language
            }
        }
        return "en-CN"
    }
    
    static func st_configLanguage() -> Void {
        object_setClass(Bundle.main, STLanguageManager.self)
        let cusLan = STLanguageManager.st_getCustomLanguage()
        if cusLan.count < 1 {
            STLanguageManager.st_setCusLanguage(language: "en")
        } else {
            STLanguageManager.st_setCusLanguage(language: cusLan)
        }
    }
}
