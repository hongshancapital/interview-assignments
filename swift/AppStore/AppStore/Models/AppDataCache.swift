//
//  AppDataCache.swift
//  AppStore
//
//  Created by Ma on 2022/3/13.
//

import Foundation

class AppDataCache {
    private static var likeCache = Dictionary<Int, Bool>()
    static func checkFavorated(_ id:Int) -> Bool {
        if let favorated = likeCache[id] {
            return favorated
        }
        let favorated = UserDefaults.standard.bool(forKey: String(id))
        likeCache[id] = favorated
        return favorated
    }
    static func setFavorated(id:Int, favorated:Bool) {
        likeCache[id] = favorated
        UserDefaults.standard.set(favorated, forKey: String(id))
        UserDefaults.standard.synchronize()
    }
}
