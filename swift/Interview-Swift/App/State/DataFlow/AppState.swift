//
//  AppState.swift
//  App
//
//  Created by lizhao on 2022/9/24.
//

import Foundation
import Combine

struct AppState {
    var appList = AppList(page: 1, apps: [])
}

extension AppState {
    struct AppList {
        var loadingList = false
        var loadingListError: AppError?
        var page: Int
        var apps: [AppModelWrapper]
        var canLoadMore = true
        var loadmoreState: LoadMoreViewState = .hidden

        // 更新点赞状态
        func updateFavoriteState(id: Int, isFavorite: Bool) {
            apps.forEach { model in
                if model.app.trackId == id {
                    model.isFavorite = isFavorite
                }
            }
        }
    }
}
