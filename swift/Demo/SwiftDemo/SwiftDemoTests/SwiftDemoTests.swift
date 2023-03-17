//
//  SwiftDemoTests.swift
//  SwiftDemoTests
//
//  Created by 彭军涛 on 2022/7/10.
//

import XCTest
@testable import SwiftDemo

class SwiftDemoTests: XCTestCase {
    var viewModel: DemoViewModel!

    @MainActor override func setUpWithError() throws {
        try super.setUpWithError()
        viewModel = DemoViewModel()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
        viewModel = nil
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    // 测试数据加载、 上拉加载更多
    @MainActor func testExample() async throws {
        viewModel.requestData()
        XCTAssertFalse(viewModel.model.results?.isEmpty ?? true)
        XCTAssertTrue(viewModel.listData.count > 0)
        await viewModel.loadingMore()
        XCTAssertTrue(viewModel.isLoadingMore == true)
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        measure {
        }
    }
    
}
