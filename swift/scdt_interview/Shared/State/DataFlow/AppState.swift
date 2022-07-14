//
//  AppState.swift
//  scdt_interview
//
//  Created by Ray Tao on 2022/7/13.
//

import Foundation

import Combine
import Foundation

struct AppState {
    var appList = AppList()
    var user: User = User()
}

extension AppState {
    struct User: Codable {
        var favoriteAppIDs: Set<String> = []
        
        func isFavorite(id: String) -> Bool {
            favoriteAppIDs.contains(id)
        }
    }
}

extension AppState {
    struct AppList {
//        @FileStorage(directory: .cachesDirectory, fileName: "appList.json")
        var list: [AppListViewModel]?
        
        var loadingApps = false
        let pageSize = 10
        var pageIndex = 0
        var headerRefresh = false
        var hasMoreData = true
        var appsLoadingError: AppError?
        
        var favoriteError: AppError?
        
        func displayAppList(with user: User) -> [AppListViewModel] {
            func isFavorite(_ appListModel: AppListViewModel) -> Bool {
                return user.isFavorite(id: appListModel.id)
            }
            
            guard let list = list else {
                return []
            }
            return list
        }
    }
}
