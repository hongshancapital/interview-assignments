//
//  ConfigManagerTest.swift
//  ListDemoTests
//
//  Created by renhe on 2022/6/24.
//

import XCTest
@testable import ListDemo

class ConfigManagerTest: XCTestCase {
    var manager: ConfigManager!

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
       try super.setUpWithError()
        manager = ConfigManager.init()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        try super.tearDownWithError()
        manager = nil
    }
    ///测试加载全部数据
    func testLoadAllData() throws{
        try manager.loadAllData()
        XCTAssertFalse(manager.getDataSource().isEmpty)
        XCTAssertTrue(manager.getDataSource().count > 0)
    }
    ///测试刷新
    func testRefrehData() async throws{
        manager.pageIndex = 0
        try await testLoadMore()
    }
    ///测试加载更多
    func testLoadMore() async throws{
        switch await manager.loadMore() {
        case .success(let fetchResult):
            XCTAssertTrue(fetchResult.result.count>0)
            XCTAssertTrue(fetchResult.hasMore == true)
        case .failure(let error):
            XCTAssertNil(error)
        }
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

}
