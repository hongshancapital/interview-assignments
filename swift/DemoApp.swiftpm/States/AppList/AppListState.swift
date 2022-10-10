//
//  AppListAction.swift
//  
//
//  Created by 黄磊 on 2022/4/10.
//

import MJDataFlow
import Foundation
import MJModule

let s_defaultFavoriteApps = "DefaultFavoriteApps"

enum AppListAction : Action {
    case toggleFavoriteOf(AppInfo)
}

struct AppListState: StateReducerLoadable, ActionBindable {
    
    typealias UpState  = AppState
    typealias BindAction = AppListAction
    
    var favoriteApps: Set<String>
    
    init() {
        if let arrFavoriteApps = UserDefaults.standard.stringArray(forKey: s_defaultFavoriteApps) {
            favoriteApps = Set<String>(arrFavoriteApps)
        } else {
            favoriteApps = []
        }
    }
    
    mutating func toggleFavoriteOf(_ bundleId: String) {
        if favoriteApps.contains(bundleId) {
            LogInfo("Remove app from favorite: \(bundleId)")
            favoriteApps.remove(bundleId)
        } else {
            LogInfo("Add app to favorite: \(bundleId)")
            favoriteApps.insert(bundleId)
        }
        UserDefaults.standard.setValue(favoriteApps.map { $0 }, forKey: s_defaultFavoriteApps)
        UserDefaults.standard.synchronize()
    }
    
    func isThisAppFavorite(_ bundleId: String) -> Bool {
        favoriteApps.contains(bundleId)
    }
    
    static func loadReducers(on store: Store<Self>) {
        store.register { (state, action: AppListAction) in
            var state = state
            switch action {
            case .toggleFavoriteOf(let appInfo):
                state.toggleFavoriteOf(appInfo.bundleId)
            }
            return state
        }
    }
}
