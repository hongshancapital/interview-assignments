//
//  HomeViewModelTests.swift
//  AppListTests
//
//  Created by 钟逊超 on 2022/8/2.
//

import XCTest
@testable import AppList

class HomeViewModelTests: XCTestCase {

    var viewModel: HomeViewModel = HomeViewModel()
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testOriginCase() throws {
        XCTAssertEqual(viewModel.appList.count, 0, "初始化应用列表数量不正确")
        XCTAssertTrue(viewModel.isLoading)
        XCTAssertFalse(viewModel.isHeaderRefreshing)
        XCTAssertFalse(viewModel.isFooterRefreshing)
    }

    func testHeaderRefreshCase() throws {
        viewModel.fetchPreviousAppList()
        XCTAssertFalse(viewModel.isLoading)
        XCTAssertEqual(viewModel.appList.count, 0, "下拉刷新应用列表数量不正确")
    }
    
    func testFootRefreshCase() throws {
        viewModel.fetchSubsequentAppList()
        XCTAssertEqual(viewModel.appList.count, 20, "上拉刷新应用列表数量不正确")
        viewModel.fetchSubsequentAppList()
        XCTAssertEqual(viewModel.appList.count, 40, "上拉刷新应用列表数量不正确")
        viewModel.fetchSubsequentAppList()
        XCTAssertEqual(viewModel.appList.count, 50, "上拉刷新应用列表数量不正确")
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
