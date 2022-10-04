//
//  MockData.swift
//  AppTests
//
//  Created by lizhao on 2022/9/29.
//

import Foundation
@testable import App

enum MockData { }

extension MockData {

    struct List {
        static let mock10AppModel: [AppModelWrapper] = {
            (0..<10).map { i in
                AppModelWrapper(
                    AppModel(artworkUrl60: URL(string: "https://www.baidu.com")!,
                             description: "mock-\(i)",
                             trackId: i,
                             trackName: "name-\(i)")
                )
            }
        }()
        
        static let mockLoadedFirstPageAppState: AppState = {
            var state = AppState()
            state.appList.apps = mock10AppModel
            state.appList.canLoadMore = true
            state.appList.loadingList = false
            state.appList.loadingListError = nil
            state.appList.page = 2
            return state
        }()
    }
    
    static let mockToggleFavoriteDone = Result<AppFavoriteResult, AppError>.success(AppFavoriteResult(isFavorited: true, id: 1))
    
    
    struct FavoriteDone {
        static let success: Result<AppFavoriteResult, AppError> = .success(AppFavoriteResult(isFavorited: true, id: 1))
        
        static let failed: Result<AppFavoriteResult, AppError> = .failure(MockError.networkingFailed)
    }
    
    struct MockError {
        static let networkingFailed = AppError.networkingFailed(NSError(domain: "mock test", code: -1))
    }
}
 
 

extension AppState {
    func favoriteState(id: Int) -> Bool? {
        let model = self.appList.apps.first {
            $0.app.trackId == id
        }
        
        return model?.isFavorite
    }
}
