//
//  AppListViewModelTests.swift
//  AppStoreTests
//
//  Created by liuxing on 2022/4/12.
//

import XCTest

@testable import AppStore

class AppListViewModelTests: XCTestCase {
    
    let viewModel = AppListViewModel()

    override func setUpWithError() throws {
        //测试前清空数据
        viewModel.appModelList = []
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
    func testRefresh() async throws {
        XCTAssert(viewModel.appModelList.count == 0)
        XCTAssertTrue(viewModel.hasMoreData == false)
        
        //开始刷新
        try await viewModel.refresh()
        XCTAssert(viewModel.appModelList.count > 0)
        XCTAssertTrue(viewModel.hasMoreData == true)
    }

    func testLoadMore() async throws {
        XCTAssert(viewModel.appModelList.count == 0)
        XCTAssertTrue(viewModel.hasMoreData == false)
        
        //开始刷新
        try await viewModel.refresh()
        let count = viewModel.appModelList.count
        XCTAssert(count > 0)

        //加载更多
        while(viewModel.hasMoreData) {
            try await viewModel.loadMore()
            XCTAssertGreaterThan(viewModel.appModelList.count, count)
        }
        
        //加载完成
        XCTAssertTrue(viewModel.hasMoreData == false)
    }
    
    func testFavorite() async throws {
        XCTAssert(viewModel.appModelList.count == 0)
        try await viewModel.refresh()
        
        for index in 0..<min(viewModel.appModelList.count, 5) {
            let model = viewModel.appModelList[index]

            //收藏
            viewModel.favoriteApp(id: model.id)
            XCTAssert(viewModel.appModelList[index].isFavorite)

            //取消收藏
            viewModel.favoriteApp(id: model.id)
            XCTAssert(!viewModel.appModelList[index].isFavorite)
        }

    }
    
    func testFavoriteAfterRefresh() async throws {
        XCTAssert(viewModel.appModelList.count == 0)
        
        //第一次刷新
        try await viewModel.refresh()
        guard let model = viewModel.appModelList.first else {
            return
        }
        let id = model.id
        //收藏
        viewModel.favoriteApp(id: model.id)
        
        //再次刷新
        try await viewModel.refresh()
        guard let index = viewModel.appModelList.firstIndex(where: { $0.id == id }) else {
            return
        }
        
        //测试之前收藏App是否为收藏状态
        XCTAssert(viewModel.appModelList[index].isFavorite)
    }

}
