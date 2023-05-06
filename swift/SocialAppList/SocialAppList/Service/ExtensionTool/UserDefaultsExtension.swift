//
//  UserDefaults.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/5/4.
//

import Foundation

extension UserDefaults {
    class func set(_ value: Any?, forKey defaultName: String) {
        UserDefaults.standard.set(value, forKey: defaultName)
        UserDefaults.standard.synchronize()
    }
    
    class func value(key: String) -> Any? {
        return UserDefaults.standard.value(forKey: key)
    }
}
