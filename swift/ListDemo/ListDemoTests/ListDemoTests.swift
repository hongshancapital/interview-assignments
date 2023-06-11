//
//  ListDemoTests.swift
//  ListDemoTests
//
//  Created by 王明友 on 2023/6/10.
//

import XCTest
@testable import ListDemo

class ListDemoTests: XCTestCase {
    
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
    
    func testRefreshData() throws {
        let appStore = AppStore()
        appStore.dispatch(.refresh)
        let expectation = self.expectation(description: "refresh data")
        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now() + 3) {
            XCTAssertNil(appStore.appState.appList.appsLoadingError, "Error refresh data")
            XCTAssertNotNil(appStore.appState.appList.apps, "Data should not be nil")
            expectation.fulfill()
        }
        waitForExpectations(timeout: 6)
    }
    
    func testLodeMore() throws {
        let appStore = AppStore()
        appStore.appState.appList.pageIndex = 2
        appStore.dispatch(.loadMore)
        let expectation = self.expectation(description: "delay waiting refresh over")
        DispatchQueue.global().asyncAfter(deadline: DispatchTime.now() + 3) {
            XCTAssertNil(appStore.appState.appList.appsLoadingError, "Error refresh data")
            XCTAssertNotNil(appStore.appState.appList.apps, "Data should not be nil")
            expectation.fulfill()
        }
        waitForExpectations(timeout: 6)
    }
    
    func testFavorite() {
        let mockId = 1234567
        let appStore = AppStore()
        appStore.dispatch(.toggleFavorite(id: mockId))
        let isFavorite = appStore.appState.isFavoriteApp(id: mockId)
        
        XCTAssert(isFavorite == true, "favorite fail")
    }
    
    func testUnFavorite() {
        let mockId = 1234567
        let appStore = AppStore()
        appStore.dispatch(.toggleFavorite(id: mockId))
        appStore.dispatch(.toggleFavorite(id: mockId))
        let isFavorite = appStore.appState.isFavoriteApp(id: mockId)
        
        XCTAssert(isFavorite == false, "unfavorite fail")
    }

}
