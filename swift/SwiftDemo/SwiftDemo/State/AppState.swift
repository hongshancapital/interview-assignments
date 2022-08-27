//
//  AppState.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import Foundation

struct AppState {
    var appStore: AppStore = AppStore()
}

extension AppState {
    struct AppStore {
        @CombineUserStorge(key: .likedApps, container: .standard)
        var likedApps: [Int] = []
    }
}
