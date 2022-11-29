//
//  AppDemoTests.swift
//  AppDemoTests
//
//  Created by jaly on 2022/11/29.
//

import XCTest
@testable import AppDemo

final class AppDemoTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testAppRefresh() async {
        let store = MainStore<AppState, AppReduce>()
        await store.dispatch(action: .refresh)
        let total = store.state.apps?.count ?? 0
        XCTAssertTrue(total > 0)
    }
    
    func testAppLoaderMore() async {
        let store = MainStore<AppState, AppReduce>()
        await store.dispatch(action: .refresh)
        var total = store.state.apps?.count ?? 0
        XCTAssertTrue(total > 0)
        await store.dispatch(action: .loadMore)
        total = store.state.apps?.count ?? 0
        let limit = store.state.limit
        XCTAssertTrue(total > limit)
    }

}
