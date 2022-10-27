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
        let appModel = DataManager.shared.favoriteApp(app, isFavorite)
        let index = self.apps.firstIndex(where: {$0.id == appModel.id})!
        self.apps[index] = appModel
    }
}
