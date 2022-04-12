//
//  AppListViewModel.swift
//  AppStore
//
//  Created by liuxing on 2022/4/11.
//

import Foundation
import SwiftUI

class AppListViewModel: ObservableObject {
    
    @Published var appModelList: [AppModel] = []
    @Published var hasMoreData: Bool = false
    
    private let host = "https://itunes.apple.com"
    private let searchUrlPath = "/search"
    
    /// 每页加载个数，方便模拟上拉加载更多效果
    private let pageCountLimit = 10
    /// 最多加载个数，方便模拟数据加载完成效果
    private let maxCountLimit = 50
    
    private var favoriteIds: [Int] = []
    
    
    func refresh() async throws {
        let params: [String : Any] = [
            "entity": "software",
            "limit": maxCountLimit,
            "term": "chat"
        ]
        let urlPath = host + searchUrlPath
        let data: AppListModel = try await Network.shared.requestData(urlPath: urlPath, params: params)
        await MainActor.run(body: {
            let count = data.results.count
            if count > 0 {
                appModelList.removeAll()
                hasMoreData = count > pageCountLimit
                let appModels = Array(data.results.prefix(upTo: min(count, pageCountLimit)))
                appModelList.append(contentsOf: updatefavorite(appModels: appModels))
            }
        })
    }
    
    func loadMore() async throws {
        let params: [String : Any] = [
            "entity": "software",
            "limit": maxCountLimit,
            "term": "chat"
        ]
        let urlPath = host + searchUrlPath
        let data: AppListModel = try await Network.shared.requestData(urlPath: urlPath, params: params)
        await MainActor.run(body: {
            let count = data.results.count
            hasMoreData = count > appModelList.count + pageCountLimit
            let appModels = Array(data.results[appModelList.count..<min(count, appModelList.count + pageCountLimit)])
            appModelList.append(contentsOf: appModels)
        })
    }
    
    func favoriteApp(id: Int) {
        if let index = appModelList.firstIndex(where: { $0.id == id }) {
            appModelList[index].isFavorite.toggle()
        }
        
        if let index = favoriteIds.firstIndex(where: { $0 == id }) {
            favoriteIds.remove(at: index)
        } else {
            favoriteIds.append(id)
        }
    }
    
    func updatefavorite(appModels: [AppModel]) -> [AppModel] {
        return appModels.map { appModel in
            AppModel(
                id: appModel.id,
                artworkUrl60: appModel.artworkUrl60,
                trackName: appModel.trackName,
                description: appModel.description,
                isFavorite: favoriteIds.contains(appModel.id))
        }
    }
}
