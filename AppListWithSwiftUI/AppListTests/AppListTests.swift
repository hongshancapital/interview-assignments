//
//  AppListTests.swift
//  AppListTests
//
//  Created by 王宁 on 2022/4/2.
//

import XCTest
@testable import AppList
import Combine

@MainActor
class AppListTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
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
        measure {
            // Put the code you want to measure the time of here.
        }
    }
    
    func testFirstLoad() async throws {
        let viewModel = AppViewModel()
        await viewModel.firstLoad()
        XCTAssertLessThanOrEqual(viewModel.datas.count, viewModel.pageLength)
    }
    
    func testRefreshAfterLoadMore() async throws {
        let viewModel = AppViewModel()
        await viewModel.firstLoad()
        await viewModel.loadMore()
        await viewModel.refresh()
        XCTAssertLessThanOrEqual(viewModel.datas.count, viewModel.pageLength)
    }
    
    func testLoadAllData() async throws {
        let viewModel = AppViewModel()
        await viewModel.firstLoad()
        while(viewModel.hasMoreData){
            await viewModel.loadMore()
            XCTAssertLessThanOrEqual(viewModel.datas.count, viewModel.pageLength * (viewModel.page + 1))
            XCTAssertGreaterThanOrEqual(viewModel.datas.count, viewModel.pageLength)
        }
    }
}
