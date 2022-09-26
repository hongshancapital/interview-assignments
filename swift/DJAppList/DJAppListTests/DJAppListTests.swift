//
//  DJAppListTests.swift
//  DJAppListTests
//
//  Created by haojiajia on 2022/7/7.
//

import XCTest
@testable import DJAppList

class DJAppListTests: XCTestCase {

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

    func testRefreshApps() {
        var expectation: XCTestExpectation? = expectation(description: "refresh expectation")
        let token = SubscriptionToken()
        let store = AppStore()
        
        store.dispatch(.loadApps)
        XCTAssertTrue(store.appState.isRefreshing, "header state 状态异常")

        store.$appState
            .dropFirst()
            .sink { state in
                XCTAssertEqual(state.apps.count, 10, "数据个数异常, count = \(state.apps.count)")
                XCTAssertFalse(state.isRefreshing, "header state 状态未更新")
                XCTAssertEqual(state.footerState, .idle, "footer state 状态异常, value = \(state.footerState)")
                expectation?.fulfill()
                expectation = nil
        }.seal(in: token)
        
        waitForExpectations(timeout: 30)
    }
        
    func testLoadMoreApps() {
        var expectation: XCTestExpectation? = expectation(description: "loadMore expectation")
        let token = SubscriptionToken()
        let store = AppStore()
        
        store.dispatch(.loadMoreApps)
        XCTAssertEqual(store.appState.footerState, .refreshing, "footer state 状态异常, value = \(store.appState.isRefreshing)")

        store.$appState
            .sink { state in
                if state.footerState == .noMoreData {
                    XCTAssertLessThanOrEqual(state.apps.count, 30, "数据异常, value count = \(state.apps.count)")
                    expectation?.fulfill()
                    expectation = nil
                } else if state.footerState == .idle {
                    DispatchQueue.global().asyncAfter(deadline: .now() + 10) {
                        store.dispatch(.loadMoreApps)
                    }
                }
        }.seal(in: token)
        waitForExpectations(timeout: 60)
    }
    
    func testLikeAction() {
        var expectation: XCTestExpectation? = expectation(description: "loadMore expectation")
        let token = SubscriptionToken()
        let store = AppStore()
        
        let id = 1163852619
        let item = AppItem(id: id,
                           image: "",
                           name: "Google Chat",
                           description: "Google Chat")
        store.appState.apps = [item]
        
        store.dispatch(.toggleLike(id: id))
        
        store.$appState
            .sink { state in
                if let item = state.apps.first {
                    XCTAssertEqual(item.isLike, true)
                    expectation?.fulfill()
                    expectation = nil
                }
        }.seal(in: token)
        
        waitForExpectations(timeout: 3)
    }

}
