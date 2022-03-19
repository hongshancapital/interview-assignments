//
//  AppsDemoViewModelTests.swift
//  AppsDemoTests
//
//  Created by changcun on 2022/3/19.
//

import XCTest
@testable import AppsDemo

class AppsDemoViewModelTests: XCTestCase {

    var viewModel: AppsViewModel!
    var mockAppsDataService: MockAppsDataService!
    var apps: [AppInfoModel]!
    
    override func setUpWithError() throws {
        mockAppsDataService = MockAppsDataService()
        viewModel = .init(dataService: mockAppsDataService)
        
        let response: ResponseModel<[AppInfoModel]>! = try LoadBundleDataService().loadJsonFromBundle("apps.json")
        apps = response.results
    }

    /// 获取最新数据
    func testFetchNewApps() async throws {
        
        mockAppsDataService.apps = apps
        
        try await viewModel.fetchNewApps()
        
        XCTAssertEqual(viewModel.apps.count, apps.count)
        XCTAssertEqual(viewModel.emptyState, .items)
    }
    
    /// 获取最新数据，数据为空
    func testFetchNewAppsEmpty() async throws {
        
        mockAppsDataService.apps = []
        
        try await viewModel.fetchNewApps()
        
        XCTAssertEqual(viewModel.emptyState, .empty)
    }
    
    /// 获取最新数据错误
    func testFetchNewAppsError() async throws {
        
        mockAppsDataService.error = NetworkError.invalidURL
        
        do {
            try await viewModel.fetchNewApps()
        } catch {
            XCTAssertEqual(viewModel.emptyState, .error)
        }
    }
    
    /// 获取最新数据，有更多数据可加载
    func testHasMoreDataTrue() async throws {
        
        mockAppsDataService.apps = apps

        try await viewModel.fetchNewApps()
        
        XCTAssertTrue(viewModel.hasMoreData)
    }
    
    /// 获取最新数据，没有更多数据
    func testHasMoreDataFalse() async throws {
        
        mockAppsDataService.apps = apps + apps

        try await viewModel.fetchNewApps()
        
        XCTAssertFalse(viewModel.hasMoreData)
    }
    
    /// 获取下一页
    func testFetchNextPageApps() async throws {
        
        mockAppsDataService.apps = apps + apps
        
        try await viewModel.fetchNextPageApps()
        
        XCTAssertEqual(viewModel.apps.count, apps.count * 2)
    }
    
    /// 当下拉刷新时不可加载下一页
    func testCanLoadMoreFalseWhenHeaderRefreshing() async throws {
        
        viewModel.headerRefreshing = true
                
        XCTAssertFalse(viewModel.canLoadMore)
    }

    /// 当正在上拉加载时不可加载下一页
    func testCanLoadMoreFalseWhenFooterRefreshing() async throws {
        
        viewModel.footerRefreshing = true
                
        XCTAssertFalse(viewModel.canLoadMore)
    }
    
    /// 当没有更多数据时不可加载下一页
    func testCanLoadMoreFalseWhenNoMoreData() async throws {
        
        mockAppsDataService.apps = apps + apps

        XCTAssertFalse(viewModel.canLoadMore)
    }

    /// 收藏
    func testCollect() async throws {
        
        mockAppsDataService.apps = apps
        
        try await viewModel.fetchNewApps()
        try viewModel.collect(index: 0)
        
        XCTAssertTrue(viewModel.apps[0].isCollected)
    }

    /// 收藏 Index 越界
    func testCollectOutofRange() async throws {
        
        mockAppsDataService.apps = []
        try await viewModel.fetchNewApps()

        do {
            try viewModel.collect(index: 100)
        } catch {
            XCTAssertNotNil(error)
        }
    }
}
