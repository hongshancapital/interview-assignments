//
//  AppListDemoTests.swift
//  AppListDemoTests
//
//  Created by arthur on 2022/10/22.
//

import XCTest
@testable import AppListDemo

class AppListDemoTests: XCTestCase {
    
    var viewModel: AppListViewModel?
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        
        viewModel = AppListViewModel()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        
        viewModel = nil
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }
    
    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
    func testMockData() throws {
        let list = try MockData.list()
        XCTAssertNoThrow(list)
        print("mock data success!  list count: \(list.count)")
    }
    
    func testViewModel() async throws {
        guard let viewModel = viewModel else {
            return
        }
        
        try await testViewModelRefreshing()
        print("==================================================== refreshing! list count: \(viewModel.dataSource.count)")
        
        
        try await testViewModelLoadMore(page: 2)
        print("==================================================== load more! list count: \(viewModel.dataSource.count)")
        try await testViewModelLoadMore(page: 3)
        print("==================================================== load more! list count: \(viewModel.dataSource.count)")
        try await testViewModelLoadMore(page: 4)
        print("==================================================== load more! list count: \(viewModel.dataSource.count)")
        try await testViewModelLoadMore(page: 5)
        print("==================================================== load more! list count: \(viewModel.dataSource.count)")
        try await testViewModelLoadMore(page: 6)
        print("==================================================== load more! list count: \(viewModel.dataSource.count)")
        
        
        try await testViewModelRefreshing()
        print("==================================================== refreshing! list count: \(viewModel.dataSource.count)")
    }
    
    func testViewModelRefreshing() async throws {
        guard let viewModel = viewModel else {
            return
        }
        
        await viewModel.refreshing()
        let count = viewModel.dataSource.count
        XCTAssert(count == 10)
        XCTAssertFalse(viewModel.isFirstLoading)
        XCTAssertFalse(viewModel.isNoMoreData)
        XCTAssertNil(viewModel.errorString)
    }
    
    func testViewModelLoadMore(page: Int) async throws {
        guard let viewModel = viewModel else {
            return
        }
        
        await viewModel.loadMore()
        let count = viewModel.dataSource.count
        XCTAssert(count == min(page * 10, 50))
        XCTAssertFalse(viewModel.isFirstLoading)
        XCTAssertNil(viewModel.errorString)
        if page >= 5 {
            XCTAssertTrue(viewModel.isNoMoreData)
        } else {
            XCTAssertFalse(viewModel.isNoMoreData)
        }
    }
}
