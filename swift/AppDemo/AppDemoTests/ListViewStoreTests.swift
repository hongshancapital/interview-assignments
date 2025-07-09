//
// Created by Jeffrey Wei on 2022/6/30.
//

import XCTest
@testable import AppDemo
import Combine

class ListViewStoreTests: XCTestCase {
    private let store = ListViewStore()
    private var cancellable: Combine.AnyCancellable?
    private var errorCancellable: Combine.AnyCancellable?
    // 测试收藏的下标,暂定用第一条数据测试
    private var testCollectIndex = 0

    // 异步刷新准备首页数据,让store至少有一页备用
    override func setUp(completion: @escaping (Error?) -> Void) {
        cancellable = store.$dataSource
                .dropFirst()
                // 间隔1秒保证数据已存入store中
                .delay(for: 1, scheduler: DispatchQueue.global())
                .sink { _ in
                    completion(nil)
                }
        errorCancellable = store.$currentError.dropFirst().sink(receiveValue: completion)
        store.refresh()
    }

    func testRefresh() {
        var expectation: XCTestExpectation? = expectation(description: "异步需要的expectation")
        cancellable = store.$dataSource
                // dropFirst移除默认初始20条数据的订阅
                .sink { dataSource in
                    XCTAssertEqual(dataSource.count, 20, "刷新后的数据应该为20条")
                    expectation?.fulfill()
                    expectation = nil
                }
        errorCancellable = store.$currentError.dropFirst().sink { err in
            XCTAssertNil(err, "刷新抛错了!!")
        }
        store.refresh()
        waitForExpectations(timeout: 10)
    }

    func testLoadMore() {
        var expectation: XCTestExpectation? = expectation(description: "异步需要的expectation")
        // 拿到监听dataSource的publish,监听是每隔1秒一次,避免请求发送太快,且能保证store赋值完成
        let publish = store.$dataSource
                // dropFirst移除默认初始20条数据的订阅
                .dropFirst()
                .delay(for: 1, scheduler: DispatchQueue.global())
        // 监听变化,loadMore后内部会一直翻页请求,直到没有更多数据或者20秒过完用例失败
        cancellable = publish.sink { [weak self] dataSource in
            guard  let weakSelf = self else { return }
            switch weakSelf.store.hasMore {
            case true:
                weakSelf.store.loadMore()
            case false:
                XCTAssertEqual(dataSource.count, 68, "目前后台数据总共有68条,测出的数据对不上!!")
                expectation?.fulfill()
                expectation = nil
            }
        }
        errorCancellable = store.$currentError.dropFirst().sink { err in
            XCTAssertNil(err, "加载更多抛错了!!")
        }
        store.loadMore()
        waitForExpectations(timeout: 30)
    }

    func testDoCollected() {
        var expectation: XCTestExpectation? = expectation(description: "异步需要的expectation")
        let beforeCollectedValue = store.dataSource[testCollectIndex].isCollected
        // 收藏也会触发dataSource的变化,订阅即可
        cancellable = store.$dataSource
                // dropFirst移除默认初始20条数据的订阅
                .dropFirst()
                .sink { [weak self]dataSource in
                    guard  let weakSelf = self else { return }
                    let afterCollectedValue = dataSource[weakSelf.testCollectIndex].isCollected
                    XCTAssertNotEqual(beforeCollectedValue, afterCollectedValue, "点赞后的isCollected相同,没任何变化,应该相反才对!!!")
                    expectation?.fulfill()
                    expectation = nil
                }
        errorCancellable = store.$currentError.dropFirst().sink { err in
            XCTAssertNil(err, "收藏抛错了!!")
        }
        // 异步的setUp函数已经准备好一页数据,直接测即可
        store.doCollected(index: testCollectIndex)
        waitForExpectations(timeout: 10)
    }
}
