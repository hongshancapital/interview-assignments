//
//  AppAction.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/11.
//

import Foundation

enum AppAction {
    case clearCache

    // app List
    case refresh
    case refreshDone(result: Result<[AppListViewModel], AppError>)
    case loadMore
    case loadMoreDone(result: Result<[AppListViewModel], AppError>)
    
    // user action
    case toggleFavorite(id: Int)
}
