//
//  DemoTests.swift
//  DemoTests
//
//  Created by 李永杰 on 2022/7/4.
//

import XCTest
@testable import Demo

class DemoTests: XCTestCase {

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
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

    func testRequestAppinfos() throws {
        let viewModel = AppViewModel.shared
        viewModel.requestAppinfos(true)
        let expectation = self.expectation(description: "延时等待获取数据")
        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now()+5) {
            expectation.fulfill()
        }
        waitForExpectations(timeout: 10)

        XCTAssert(viewModel.appInfos.count == 10, "获取的数据是10条数据才对")
    }
    
    func testRequestAppLikeinfos() throws {
        let viewModel = AppViewModel.shared
        viewModel.updateLikeStatus(with: 123, isLike: true)
//        let result = viewModel.queryLikeStatus(with: 122)
        let result = viewModel.queryLikeStatus(with: 123)
        XCTAssert(result == true, "已经收藏了123,没有收藏122")
    }
    
}
