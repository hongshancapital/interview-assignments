//
//  AppInfoBaseModel.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/5.
//

import Foundation
import SwiftUI

enum NetworkError: Error {
    case invalidURL
    case customer(errorMessage: String)
}

fileprivate let pageSize = 50

@available(iOS 15.0, *)
struct AppsModel {
    private(set) var apps: [AppInfoModel] = []

    var hasMoreData: Bool = false
    
    fileprivate var currentPage = 1
}

extension AppsModel {
    // MARK: - 加载数据

    mutating func fetchNewApps() async throws {
        defer {
            currentPage = 1
        }
        
        guard let apps = try await fetchApps(page: 1) else {
            apps = []
            return
        }
        
        self.apps = apps
        
        hasMoreData = self.apps.count < pageSize * 2
    }
    
    mutating func fetchNextPageApps() async throws {
        guard let apps = try await fetchApps(page: currentPage + 1) else {
            return
        }
        
        self.apps += apps
        currentPage += 1
        
        hasMoreData = self.apps.count < pageSize * 2
    }
    
    private func fetchApps(page: Int) async throws -> [AppInfoModel]? {
        guard page > 0 else {
            throw NetworkError.customer(errorMessage: "page must > 0")
        }
        
        do {
            let response: ResponseModel<[AppInfoModel]> = try await LoadBundleDataService().loadJsonFromBundle("apps.json")
            
            return response.results
        } catch {
            throw NetworkError.customer(errorMessage: "Load data error.")
        }
    }
    
    // MARK: - 收藏

    mutating func collect(index: Int) throws {
        guard index < apps.count else {
            throw NetworkError.customer(errorMessage: "Index out of range.")
        }
        apps[index].isCollected.toggle()
    }
}
