//
//  AppsViewModel.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI
import Combine

class AppsViewModel: ObservableObject {
    @Published var apps: [AppModel] = []
    @Published var isRefreshing: Bool = false
    @Published var isLoadMore: Bool = false
    @Published var favorites: [Int] = []
    
    let pageSize = 20
    var pageIndex = 0
    
    func loadNewApps() {
        pageIndex = 0
        self.isRefreshing = true
        Task {
            let models = await DataManager.shared.fetchApps(pageSize, self.pageIndex)
            await MainActor.run {
                self.apps = models
                self.isRefreshing = false
            }
        }
    }
    
    func loadMoreApps() {
        pageIndex += 1
        self.isLoadMore = true
        Task {
            let models = await DataManager.shared.fetchApps(pageSize, self.pageIndex)
            await MainActor.run {
                self.apps.append(contentsOf: models)
                self.isLoadMore = false
            }
        }
    }
    
    func itemOnAppear(_ app: AppModel) {
        if isLastItem(app) {
            self.loadMoreApps()
        }
    }
    
    func isLastItem(_ app: AppModel) -> Bool {
        guard self.apps.count > 0 else {
            return false
        }
        return self.apps.last?.id == app.id
    }
    
    func favoriteApp(_ app: AppModel, _ isFavorite: Bool) {
        DataManager.shared.favoriteApp(app, isFavorite)
        if let index = favorites.firstIndex(of: app.id) {
            favorites.remove(at: index)
        } else {
            favorites.append(app.id)
        }
    }
    
    func isFavorite(_ app: AppModel) -> Bool {
        let ret = favorites.firstIndex(of: app.id) != nil
        return ret
    }
}
