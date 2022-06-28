//
//  AppDemoTests.swift
//  AppDemoTests
//
//  Created by Jeffrey Wei on 2022/6/27.
//
//

import XCTest
@testable import AppDemo
import Combine

class AppDemoTests: XCTestCase {
    private var cancellable: AnyCancellable?

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testGetDemoListApi() {
        let expectation = expectation(description: "异步需要的expectation")
        cancellable = Api.getDemoList(pageSize: 20, pageNum: 10).sinkResultData(dataCls: [DemoModel].self,
                receiveCompletion: {
                    switch $0 {
                    case .finished:
                        break
                    case .failure(let err):
                        XCTFail("请求过程失败,error是:\(err)")
                    }
                    expectation.fulfill()
                },
                receiveValue: {
                    XCTAssertNotNil($0, "result返回错误,不应该为nil")
                })
        waitForExpectations(timeout: 20)
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
