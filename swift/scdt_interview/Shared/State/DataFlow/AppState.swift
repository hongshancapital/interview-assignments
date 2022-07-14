//
//  AppState.swift
//  scdt_interview
//
//  Created by Ray Tao on 2022/7/13.
//

import Foundation

import Combine
import Foundation
import SwiftUI

struct AppState {
    var appList = AppList()
    var user: User = .init()
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
        @State var headerRefresh = false
        @State var footerRefresh = false
        @State var listState = ListState()
        var hasMoreData: Bool { listState.noMore }
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
