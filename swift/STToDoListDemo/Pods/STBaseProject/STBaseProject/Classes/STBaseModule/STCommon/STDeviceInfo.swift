//
//  STDeviceInfo.swift
//  STBaseProject
//
//  Created by stack on 2019/02/10.
//  Copyright © 2019 ST. All rights reserved.
//

import UIKit

public struct STDeviceInfo {
    
    /// uuid
    public static func uuid() -> String {
        return UIDevice.current.identifierForVendor?.uuidString ?? ""
    }
    
    public static func currentSysVersion() -> String {
        return UIDevice.current.systemVersion
    }
    
    public static func currentAppVersion() -> String {
        let info = self.appInfo()
        if let appVersion = info["CFBundleShortVersionString"] as? String {
            return appVersion
        }
        return ""
    }
    
    public static func currentAppName() -> String {
        let info = self.appInfo()
        if let appName = info["CFBundleDisplayName"] as? String {
            return appName
        }
        if let appName = info["CFBundleName"] as? String {
            return appName
        }
        return ""
    }
    
    public static func appInfo() -> Dictionary<String, Any> {
        return Bundle.main.infoDictionary ?? Dictionary<String, Any>()
    }
    
    public static func isLandscape() -> Bool {
        let application = UIApplication .shared
        let orientation = application.statusBarOrientation
        if orientation.isLandscape {
            return true
        }
        return false
    }
    
    public static func isPortrait() -> Bool {
        let application = UIApplication .shared
        let orientation = application.statusBarOrientation
        if orientation.isPortrait {
            return true
        }
        return false
    }

    public static func statusBarOrientation() -> UIInterfaceOrientation {
        return UIApplication.shared.statusBarOrientation
    }
}
