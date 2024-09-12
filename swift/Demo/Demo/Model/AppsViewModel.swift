//
//  AppsViewModel.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI
import Combine

@MainActor class AppsViewModel: ObservableObject {
    @Published private(set) var apps: [AppModel] = []
    @Published var isRefreshing: Bool = false
    @Published var isLoadMore: Bool = false
    @Published var error: NetworkError? = nil
    
    var pageSize = 20
    var pageIndex = 0
    
    var isProgressing: Bool {
        return isRefreshing && apps.isEmpty
    }
    
    func loadNewApps() async {
        if self.isRefreshing || self.isLoadMore { return }

        pageIndex = 0
        self.isRefreshing = true
        self.error = nil
        do {
            let models = try await NetWorkManager.shared.fetchApps(pageSize, self.pageIndex)
            self.apps = models
            self.isRefreshing = false
            self.error = nil
        } catch {
            self.isRefreshing = false
            self.error = NetworkError.networkError
        }
    }
    
    func loadMoreApps() async {
        if self.isRefreshing || self.isLoadMore { return }

        pageIndex += 1
        self.isLoadMore = true
        self.error = nil
        do {
            let models = try await NetWorkManager.shared.fetchApps(pageSize, self.pageIndex)
            self.apps.append(contentsOf: models)
            self.isLoadMore = false
            self.error = nil
        } catch {
            self.isLoadMore = false
            self.error = NetworkError.networkError
        }
    }
    
    func favoriteApp(_ app: AppModel) async {
        do {
            let respAppModel = try await NetWorkManager.shared.favoriteRequest(app.id, !app.isFavorite)
            let index = apps.firstIndex(where: {$0.id == app.id})!
            apps[index].isFavorite = respAppModel.isFavorite
        } catch {
            // do somthing
        }
    }
}

extension AppsViewModel {
    func itemOnAppear(_ app: AppModel) async {
        if isLastItem(app) {
            await self.loadMoreApps()
        }
    }
    
    func isLastItem(_ app: AppModel) -> Bool {
        guard self.apps.count > 0 else {
            return false
        }
        return self.apps.last?.id == app.id
    }
}
