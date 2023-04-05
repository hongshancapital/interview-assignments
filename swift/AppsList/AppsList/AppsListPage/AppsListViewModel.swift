//
//  AppsListViewModel.swift
//  AppsList
//
//  Created by 贺建华 on 2023/4/2.
//

import Foundation
import Combine

class AppsListViewModel: ObservableObject {
    
    @Published private(set) var items: [AppItemModel] = []
    private(set) var state: LoadingState = .success
    private(set) var hasMore: Bool = true
        
    private var pageIndex: Int = 0
        
    @MainActor
    func requestAppItems(more: Bool) async {
        if state == .loading { return }
        state = .loading
        
        var appItems: [AppItemModel] = []
        var hasMore = self.hasMore
        do {
            (appItems, hasMore) = try await requestRemoteItems(pageIndex: more ? pageIndex + 1 : 0)
        } catch let error {
            print("[Log] [ViewModel] request fail. error:\(error)")
            state = .failure
            return
        }
        
        if more {
            items.append(contentsOf: appItems)
            pageIndex += 1
        } else {
            items = appItems
            pageIndex = 0
        }
        self.hasMore = hasMore
        state = .success
    }
    
    private func requestRemoteItems(pageIndex: Int = 0) async throws -> ([AppItemModel], Bool) {
        var results: [AppItemModel] = try await Requester.fetchRemoteDatas(Const.appsListUrlString)
        try await Task.sleep(nanoseconds: 500_000_000) // 延迟0.5秒，方便看loading效果
        results.removeFirst(pageIndex * Const.pageSize)
        return (Array(results.prefix(Const.pageSize)), results.count > Const.pageSize)
    }
}

