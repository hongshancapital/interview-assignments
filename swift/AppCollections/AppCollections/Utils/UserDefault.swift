//
//  UserDefault.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/12.
//

import Foundation

/// UserDefaults 的属性包装器。
///
/// 如果 defaultValue 有值，示例：
/// ```
/// @UserDefault("lastAlertDateKey", defaultValue: Date())
/// static var lastAlertDate: Date
/// ```
///
/// 如果 defaultValue 是 nil，则把属性声明为Optional的，示例：
/// ```
/// @UserDefault("lastAlertDateKey", defaultValue: nil)
/// static var lastAlertDate: Date?
/// ```
@propertyWrapper
public struct UserDefault<T> {
    
    public let key: String
    public let defaultValue: T

    public init(_ key: String, defaultValue: T) {
        self.key = key
        self.defaultValue = defaultValue
    }

    public var wrappedValue: T {
        get {
            UserDefaults.standard.object(forKey: key) as? T ?? defaultValue
        }
        set {
            UserDefaults.standard.set(newValue, forKey: key)
        }
    }
}
