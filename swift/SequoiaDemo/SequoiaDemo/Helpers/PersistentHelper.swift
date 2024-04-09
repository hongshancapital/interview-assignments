//
//  PersistentHelper.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/5/4.
//

import Foundation

/*
 用于本地存储
 */

struct PersistenHelper {
    static let instance  = PersistenHelper()
    
    func save(val: Any?, key: String) {
        UserDefaults.standard.set(val, forKey: key)
        UserDefaults.standard.synchronize()
    }
    
    func loadBool(with key: String) -> Bool? {
        return UserDefaults.standard.bool(forKey: key)
    }
    
    func loadString(with key: String) -> String? {
        return UserDefaults.standard.string(forKey: key)
    }
}
