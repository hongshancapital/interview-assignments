//
//  AppAction.swift
//  App
//
//  Created by lizhao on 2022/9/24.
//

import Foundation

enum AppAction {
    case refresh
    case loadApplist
    case loadApplistDone(result: Result<[AppModelWrapper], AppError>)
    
    case toggleFavorite(trackId: Int, isFavorite: Bool)
    case toggleFavoriteDone(result: Result<AppFavoriteResult, AppError>)
}
