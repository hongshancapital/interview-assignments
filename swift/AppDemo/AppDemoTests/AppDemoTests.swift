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

    // 测试获取demo数据列表第一页接口,并查看sinkResponseData的线程问题,后续接口不再测试线程问题
    func testGetDemoListApi() {
        let expectation = expectation(description: "异步需要的expectation")
        cancellable = Api.getDemoList(pageSize: 20, pageNum: 1).sinkResponseData(
                dataCls: [DemoModel].self,
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
                    XCTAssertEqual($0!.count, 20, "传入页数为1,size为20,第一页数据应该是满页20条数据!")
                })
        waitForExpectations(timeout: 20)
    }

    // 测试点赞接口,id为469,目前数据库中总共68条数据,id从469到536
    func testDoCollected() {
        let expectation = expectation(description: "异步需要的expectation")
        cancellable = Api.doCollected(id: 469, isCollected: true).sinkResponseData(
                dataCls: Bool.self,
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
                    XCTAssertNotNil($0, "result返回错误,不应该为nil!")
                    XCTAssertTrue($0!, "isCollected传入的为true,返回结果应该是true!")
                })
        waitForExpectations(timeout: 20)
    }

    /// 使用-1为id测收藏接口,仅测试后台异常情况
    /// 保证测到sinkResponseData函数抛错的情况
    /// (Request/Model和 Request/Target用例覆盖率达97%->100%)
    func testDoCollectedError() {
        let expectation = expectation(description: "异步需要的expectation")
        cancellable = Api.doCollected(id: -1, isCollected: true).sinkResponseData(
                dataCls: Bool.self,
                receiveCompletion: {
                    switch $0 {
                    case .finished:
                        XCTFail("无-1的id,后台应该抛错,而不是进来finished")
                    case .failure(let err):
                        let businessError = err as? BusinessError
                        XCTAssertNotNil(businessError, "此处错误应该是一个BusinessError")
                        XCTAssertNotNil(businessError?.localizedDescription, "后台必须给出异常原因,此处没给!")
                    }
                    expectation.fulfill()
                },
                receiveValue: { _ in
                })
        waitForExpectations(timeout: 20)
    }
}
