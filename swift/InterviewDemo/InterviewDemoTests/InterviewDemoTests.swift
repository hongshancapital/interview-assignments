//
//  InterviewDemoTests.swift
//  InterviewDemoTests
//
//  Created by Lays on 2023/3/15.
//

import XCTest
@testable import InterviewDemo

final class InterviewDemoTests: XCTestCase {

    var viewModel: ViewMoel!
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        try super.setUpWithError()
        viewModel = ViewMoel(service: AppService(pageSize: 10))
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        try super.tearDownWithError()
        viewModel = nil
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
    
    func testDataInit() async throws {
        // wait some time for ViewModel to fetch data after initialized
        try await Task.sleep(nanoseconds: 2_000_000_000)
        XCTAssertFalse(viewModel.appList.isEmpty)
        XCTAssertTrue(viewModel.hasMore)
        XCTAssertNil(viewModel.error)
    }
    
    func testLoadMore() async throws {
        try await testDataInit()
        let dataCount = viewModel.appList.count
        for index in viewModel.appList.indices {
            viewModel.fetchMoreData(currentItem: viewModel.appList[index])
            try await Task.sleep(nanoseconds: 2_000_000_000)
            // load more data until scrolls to the last row
            if index < dataCount - 1 {
                XCTAssertTrue(viewModel.appList.count == dataCount)
            }else {
                XCTAssertTrue(viewModel.appList.count > dataCount)
            }
        }
    }
    
    func testNoMoreData() async throws {
        try await testDataInit()
        for _ in 0..<4 {
            await viewModel.fetchData()
        }
        XCTAssertFalse(viewModel.hasMore)
    }
    
    func testRefresh() async throws {
        try await testDataInit()
        let initCount = viewModel.appList.count
        await viewModel.fetchData()
        XCTAssertTrue(viewModel.appList.count > initCount)
        await viewModel.refresh()
        XCTAssertTrue(viewModel.appList.count == initCount)
    }

    func testCollectedStatusSaved() async throws {
        try await testDataInit()
        let preCollectedStatus = viewModel.appList.first!.collected
        viewModel.updateCollectedStatus(forItem: viewModel.appList.first!)
        let postCollectedStatus = viewModel.appList.first!.collected
        XCTAssertTrue(preCollectedStatus != postCollectedStatus)
        // re-init ViewModel
        viewModel = ViewMoel(service: AppService(pageSize: 10))
        try await testDataInit()
        XCTAssertTrue(viewModel.appList.first!.collected == postCollectedStatus)
    }
    
}
