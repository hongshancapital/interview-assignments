//
//  AppListViewModel.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/12.
//

import Foundation

@MainActor class AppListViewModel: ObservableObject {
    
    private var searchNetwork = SearchNetwork()
    private let pageSize = 10

    @Published var list: [AppModel] = []
    @Published var isFirstLoading = true
    @Published var noMoreData = false
    @Published var showError = false

    private(set) var alertMessage: String = "" {
        willSet { showError = true }
    }
    
    @UserDefault("favoriteAppIds", defaultValue: [])
    private var favoriteAppIds: Array<Int>
    
    // MARK: - Load & Pull refresh
    
    func firstLoad() async {
        await refresh()
        isFirstLoading = false
    }
    
    func refresh() async {
        do {
            let results = try await SearchNetwork().searchChatApp(
                limit: pageSize,
                decodableType: AppListResponseModel.self
            ).results
            let favoriteResults = mapToFavorite(results)
            list = favoriteResults
            if favoriteResults.isEmpty {
                alertMessage = "No available data."
            }
        } catch {
            self.alertMessage = error.localizedDescription
        }
    }
    
    func loadMore() async {
        /**
            经查询 itunes search api 接口文档：https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api
            发现未提供分页加载的参数，因此采用 limit 参数来加载更多数据，并过滤掉已展示的数据，以实现录屏demo中的效果。
         */
        do {
            let currentCount = list.count
            let results = try await SearchNetwork().searchChatApp(
                limit: currentCount + pageSize,
                decodableType: AppListResponseModel.self
            ).results
            let filteredResults = results.filter { result in
                !list.contains(where: { $0.trackId == result.trackId })
            }
            let favoriteResults = mapToFavorite(filteredResults)
            if favoriteResults.count > 0 {
                list.append(contentsOf: favoriteResults)
            } else {
                noMoreData = true
            }
        } catch {
            self.alertMessage = error.localizedDescription
        }
    }
    
    // MARK: - Favorite
    
    func favor(_ app: AppModel) {
        let id = app.trackId
        if let index = favoriteAppIds.firstIndex(where: { $0 == id }) {
            favoriteAppIds.remove(at: index)
        } else {
            favoriteAppIds.append(id)
        }
        
        if let index = list.firstIndex(where: { $0.trackId == id }) {
            list[index].isFavorite.toggle()
        }
    }
}

// MARK: - Helper

private extension AppListViewModel {
    
    func mapToFavorite(_ results: [AppListResponseModel.App]) -> [AppModel] {
        let favoriteIds = favoriteAppIds
        return results.map { app in
            AppModel(
                trackId: app.trackId,
                trackName: app.trackName,
                description: app.description,
                artworkUrl60: app.artworkUrl60,
                isFavorite: favoriteIds.contains(app.trackId)
            )
        }
    }
}
