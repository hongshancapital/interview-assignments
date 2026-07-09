//
//  AppListViewModelTests.swift
//  ApplistTests
//
//  Created by santcool on 2023/1/30.
//

@testable import Applist
import Combine
import XCTest

final class AppListViewModelTests: XCTestCase {
    var viewModel = AppListViewModel(page: 0)

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    func testToggleLikedData() throws {
        // 数据源为空，喜欢不生效
        viewModel.resultData = AppListMockData.response1
        viewModel.toggleLikedData(appModel: AppListMockData.likedApp)
        XCTAssertTrue(viewModel.likedList.count == 0)
        viewModel.toggleLikedData(appModel: AppListMockData.likedApp)
        XCTAssertTrue(viewModel.likedList.count == 0)

        // 喜欢切换是否正常生效
        viewModel.resultData = AppListMockData.response2
        viewModel.toggleLikedData(appModel: AppListMockData.likedApp)
        XCTAssertTrue(viewModel.likedList.contains(where: { $0.trackId == AppListMockData.likedApp.trackId }))
        viewModel.toggleLikedData(appModel: AppListMockData.likedApp)
        XCTAssertFalse(viewModel.likedList.contains(where: { $0.trackId == AppListMockData.likedApp.trackId }))
    }

    func testRefresh() async throws {
//        let _: XCTestExpectation = expectation(description: "async")
        await viewModel.refresh()
        XCTAssertFalse(viewModel.isLoading)
        XCTAssertFalse(viewModel.firstLoad)
        XCTAssertTrue(viewModel.page == 0)
    }

    func testRefreshWithNetError() async throws {
        viewModel.searchUrl = "https://www.baidu.com"
        await viewModel.refresh()
        XCTAssertTrue(viewModel.loadError != nil)
        XCTAssertTrue(viewModel.page == 0)
    }

    func testLoadMore() async throws {
//        let _: XCTestExpectation = expectation(description: "async")
        viewModel.searchUrl = "https://itunes.apple.com/search"
        viewModel.firstLoad = false
        let page = 3
        viewModel.page = page
        await viewModel.loadMore()
        XCTAssertFalse(viewModel.isLoading)
        if viewModel.loadError == nil {
            XCTAssertTrue(viewModel.page == page + 1)
        } else {
            XCTAssertTrue(viewModel.page == page)
        }
    }

    func testLoadMoreHasLoadAll() async throws {
        viewModel.firstLoad = false
        let page = 20 // 此接口正常情况下总共能返回200条数据，page为20时需要返回400条，返回条数小于需要的条数，则loadAll应该为true
        viewModel.page = page
        await viewModel.loadMore()
        if viewModel.loadError == nil {
            XCTAssertTrue(viewModel.loadAll)
        } else {
            XCTAssertFalse(viewModel.loadAll)
        }
    }
}
