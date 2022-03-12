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

    @Published var list: [AppListModel.Item] = []
    @Published var isFirstLoading = true
    @Published var noMoreData = false
    @Published var showError = false

    var alertMessage: String = "" {
        willSet { showError = true }
    }
    
    func firstLoad() async {
        await refresh()
        isFirstLoading = false
    }
    
    func refresh() async {
        do {
            let results = try await SearchNetwork().searchChatApp(
                limit: pageSize,
                decodableType: AppListModel.self
            ).results
            list = results
            if results.isEmpty {
                alertMessage = "No available data."
            }
        } catch {
            self.alertMessage = error.localizedDescription
        }
    }
    
    func loadMore() async {
        /**
            经查询 itunes search api 接口文档：https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api
            发现未提供分页加载的参数，因此采用 limit 参数来加载更多数据，然后过滤掉已展示的，以实现录屏demo中的效果。
         */
        do {
            let currentCount = list.count
            let results = try await SearchNetwork().searchChatApp(
                limit: currentCount + pageSize,
                decodableType: AppListModel.self
            ).results
            if results.count > 0 {
                list.append(contentsOf: results.suffix(pageSize))
            } else {
                noMoreData = true
            }
        } catch {
            self.alertMessage = error.localizedDescription
        }
    }
}
