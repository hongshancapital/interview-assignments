//
//  AppListViewModel.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/5.
//

import SwiftUI


@available(iOS 15.0, *)
final class AppsViewModel: ObservableObject {
    @Published private(set) var apps: [AppInfoModel] = []
    
    @Published private var dataService: AppsDataServiceProtocol
    
    @Published var headerRefreshing: Bool = false
    @Published var footerRefreshing: Bool = false
    @Published var emptyState: EmptyDataState = EmptyDataState.loading
    
    /// 是否有更多数据
    var hasMoreData: Bool = false
    
    private let pageSize = 50
    private var currentPage = 1
    
    init(dataService: AppsDataServiceProtocol = AppsDataService()) {
        self.dataService = dataService
    }
}

// MARK: -

extension AppsViewModel {
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
        defer {
            currentPage = 1
        }
        
        do {
            guard let apps = try await dataService.fetchApps(page: 1) else {
                apps = []
                return
            }
            
            self.apps = apps
            
            hasMoreData = self.apps.count < pageSize * 2
            
            if apps.isEmpty {
                emptyState = .empty
            } else {
                emptyState = .items
            }
        } catch {
            emptyState = .error
            throw error
        }
    }
    
    /// 加载更多
    func fetchNextPageApps() async throws {
        guard let apps = try await dataService.fetchApps(page: currentPage + 1) else {
            return
        }
        
        self.apps += apps
        currentPage += 1
        
        hasMoreData = self.apps.count < pageSize * 2
    }
    
    /// 收藏
    func collect(index: Int) throws {
        guard index < apps.count else {
            throw NetworkError.customer(errorMessage: "Index out of range.")
        }
        
        apps[index].isCollected.toggle()
    }
}
