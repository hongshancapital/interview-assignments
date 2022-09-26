//
//  AppState.swift
//  AppList
//
//  Created by haojiajia on 2022/7/10.
//

import Foundation

struct AppState {
    var apps: [AppItem] = []
    
    var isRefreshing: Bool = false
    var footerState: AppFooterViewState = .idle
    
    var appsLoadingError: AppError?
}

enum AppAction {
    // app list
    case loadApps
    case loadAppsDone(result: Result<[AppItem], AppError>)
    
    case loadMoreApps
    case loadMoreAppsDone(result: Result<[AppItem], AppError>, isEnd: Bool)
    
    // chang like status
    case toggleLike(id: Int)
}
