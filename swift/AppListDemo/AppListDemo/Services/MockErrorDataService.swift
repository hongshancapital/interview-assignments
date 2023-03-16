//
//  MockErrorDataService.swift
//  AppListDemo
//
//  Created by Kimi on 2023/3/7.
//

import Foundation

actor MockErrorDataService {
    static let shared = MockErrorDataService()
}

// MARK: - RequestDataService
extension MockErrorDataService: RequestDataService {
    func fetchAppList(atPage page: Int, pageCount: Int) async throws -> AppListResponseModel {
        // mocking network delay
        try await Task.sleep(for: Duration.seconds(0.5))
        
        return  AppListResponseModel (
            code: .fail,
            appModels: []
        )
    }
    
    func toggleFavouriteApp(_ appModel: AppModel) async throws -> FavouriteResponseModel {
        // mocking network delay
        try await Task.sleep(for: Duration.seconds(0.5))
        
        return FavouriteResponseModel(code: .fail)
    }
}
