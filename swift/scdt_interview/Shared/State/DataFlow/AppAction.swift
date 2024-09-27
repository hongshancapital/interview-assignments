//
//  AppAction.swift
//  scdt_interview
//
//  Created by Ray Tao on 2022/7/13.
//

import Foundation

enum AppAction {

    // App List
    case loadAppList
    case loadAppListDone(result: Result<[AppListViewModel], AppError>)
    case loadAppListHeader
    case loadAppListHeaderDone(result: Result<[AppListViewModel], AppError>)
    case loadAppListFooter(index: Int)
    case loadAppListFooterDone(result: Result<[AppListViewModel], AppError>)
    case toggleFavorite(id: String)

}
