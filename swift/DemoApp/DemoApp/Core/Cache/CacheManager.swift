//
//  CacheManager.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/19.
//

import Foundation

protocol CacheManagerProtocol {
    /// 缓存数据
    /// - Parameters:
    ///   - string: 缓存的内容
    ///   - key: 对应的 key
    func setString(_ string: String, forKey key: String)
    
    /// 获取缓存数据
    func getString(forKey key: String) -> String?
}

class CacheManager: CacheManagerProtocol {
    let userDefaults: UserDefaults
    
    init(userDefaults: UserDefaults) {
        self.userDefaults = userDefaults
    }
    
    func setString(_ string: String, forKey key: String) {
        userDefaults.set(string, forKey: key)
        userDefaults.synchronize()
    }
    
    func getString(forKey key: String) -> String? {
        let cacheString = userDefaults.string(forKey: key)
        return cacheString!
    }
}
