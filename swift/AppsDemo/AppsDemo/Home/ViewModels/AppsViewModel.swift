//
//  AppListViewModel.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/5.
//

import SwiftUI

@available(iOS 15.0, *)
class AppsViewModel: ObservableObject {
    @Published private var model = AppsModel()
    @Published var headerRefreshing: Bool = false
    @Published var footerRefreshing: Bool = false
    @Published var emptyState: EmptyDataState = EmptyDataState.loading
}

// MARK: - 读写model

extension AppsViewModel {
    /// App 数据
    var apps: [AppInfoModel] {
        model.apps
    }
    
    /// 是否有更多数据
    var hasMoreData: Bool {
        model.hasMoreData
    }
    
    /// 是否可以加载更多
    /// 当正在下拉刷新、上拉加载或没有数据可以加载时返回false
    var canLoadMore: Bool {
        if headerRefreshing {
            return false
        }
        
        if footerRefreshing {
            return false
        }
        
        if !hasMoreData {
            return false
        }
        
        return true
    }
    
    /// 是否可以下拉刷新
    /// 当正在上拉加载时返回false
    var canReload: Bool {
        if footerRefreshing {
            return false
        }
        
        return true
    }
    
    /// 获取最新的数据
    func fetchNewApps() async throws {
        try await model.fetchNewApps()
    }
    
    /// 加载更多
    func fetchNextPageApps() async throws {
        try await model.fetchNextPageApps()
    }
    
    /// 收藏
    func collect(index: Int) throws {
        try model.collect(index: index)
    }
}
