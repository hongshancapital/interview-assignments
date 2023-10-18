//
//  AppState.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/11.
//

import Foundation
import Combine

struct AppState {
    var appList = AppList()
    var favoriteappIDs = Set<Int>()
}

extension AppState {
    struct AppList {
        
        @DataStorage(directory: .cachesDirectory, fileName: "data.json")
        var apps: [AppListViewModel]?
        
        let pageAppsNum = 20
        var pageIndex: Int = 0
        var isLoading = false
        var hasMore = true
        var appsLoadingError: AppError?
        
        func display() -> [AppListViewModel] {

            guard let apps = apps else {
                return []
            }
            /**
             handle data, maybe do
             1. filter
             2. sorted
             3. map
             */
            return apps
        }
    }
}

extension AppState {
    func isFavoriteApp(id: Int) -> Bool {
        favoriteappIDs.contains(id)
    }
}
