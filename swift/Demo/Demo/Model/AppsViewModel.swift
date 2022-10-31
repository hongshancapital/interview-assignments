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
            do {
                let models = try await NetWorkManager.shared.fetchApps(pageSize, self.pageIndex)
                await MainActor.run {
                    self.apps = models
                    self.isRefreshing = false
                }
            } catch NetworkError.badPageIndex {
                // do somthing
                await MainActor.run {
                    self.isRefreshing = false
                }
            } catch {
                // do somthing
                await MainActor.run {
                    self.isRefreshing = false
                }
            }
        }
    }
    
    func loadMoreApps() {
        pageIndex += 1
        self.isLoadMore = true
        Task {
            do {
                let models = try await NetWorkManager.shared.fetchApps(pageSize, self.pageIndex)
                await MainActor.run {
                    self.apps.append(contentsOf: models)
                    self.isLoadMore = false
                }
            } catch {
                // do somthing
                await MainActor.run {
                    self.isLoadMore = false
                }
            }
        }
    }
    
    func favoriteApp(_ app: AppModel, _ isFavorite: Bool) async {
        do {
            let respAppModel = try await NetWorkManager.shared.favoriteRequest(app, isFavorite)
            await MainActor.run {
                if respAppModel.isFavorite {
                    addFavorite(app)
                } else {
                    removeFavorite(app)
                }
            }
        } catch {
            // do somthing
        }
    }
}

extension AppsViewModel {
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
    
    func addFavorite(_ app: AppModel) {
        if favorites.firstIndex(of: app.id) == nil {
            favorites.append(app.id)
        }
    }
    
    func removeFavorite(_ app: AppModel) {
        if let index = favorites.firstIndex(of: app.id) {
            favorites.remove(at: index)
        }
    }
        
    func isFavorite(_ app: AppModel) -> Bool {
        return favorites.firstIndex(of: app.id) != nil
    }
}
