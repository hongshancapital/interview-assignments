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
        cancellable = Api.getDemoList(pageSize: 20, pageNum: 1).sinkResponseData(dataCls: [DemoModel].self,
                receiveCompletion: {
                    XCTAssertTrue(Thread.isMainThread, "封装的sink函数回调不在主线程!")
                    switch $0 {
                    case .finished:
                        break
                    case .failure(let err):
                        XCTFail("请求过程失败,error是:\(err)")
                    }
                    expectation.fulfill()
                },
                receiveValue: {
                    XCTAssertTrue(Thread.isMainThread, "封装的sink函数回调不在主线程!")
                    XCTAssertNotNil($0, "result返回错误,不应该为nil")
                })
        waitForExpectations(timeout: 20)
    }

    func testDoCollected() {
        let expectation = expectation(description: "异步需要的expectation")
        cancellable = Api.doCollected(id: 469, isCollected: true).sinkResponseData(dataCls: Bool.self,
                receiveCompletion: {
                    XCTAssertTrue(Thread.isMainThread, "封装的sink函数回调不在主线程!")
                    switch $0 {
                    case .finished:
                        break
                    case .failure(let err):
                        XCTFail("请求过程失败,error是:\(err)")
                    }
                    expectation.fulfill()
                },
                receiveValue: {
                    XCTAssertTrue(Thread.isMainThread, "封装的sink函数回调不在主线程!")
                    XCTAssertNotNil($0, "result返回错误,不应该为nil")
                })
        waitForExpectations(timeout: 20)
    }
}
