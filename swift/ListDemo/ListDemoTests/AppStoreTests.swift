//
//  AppStoreTests.swift
//  ListDemoTests
//
//  Created by 王明友 on 2023/6/14.
//

import XCTest

final class AppStoreTests: XCTestCase {

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
    
    let store: AppStore = AppStore()
    func testAppStore_dispatch() throws {
        store.dispatch(.refresh)
        XCTAssertEqual(store.appState.appList.isLoading, true)
        
        store.dispatch(.loadMore)
        XCTAssertEqual(store.appState.appList.isLoading, true)
        
        let mockAppLists = TestMockData.mockData()
        var appListCount = mockAppLists.count
        store.dispatch(.refreshDone(result: .success(mockAppLists)))
        XCTAssertEqual(store.appState.appList.isLoading, false)
        XCTAssertEqual(store.appState.appList.apps?.count ?? 0, appListCount)
        
        
        appListCount += mockAppLists.count
        store.dispatch(.loadMoreDone(result: .success(mockAppLists)))
        XCTAssertEqual(store.appState.appList.isLoading, false)
        XCTAssertEqual(store.appState.appList.apps?.count ?? 0, appListCount)
        
        let testError = NSError(domain: "testDispatchError", code: -1000)
        store.dispatch(.refreshDone(result: .failure(AppError.networkingFailed(testError))))
        XCTAssertEqual(store.appState.appList.isLoading, false)
        XCTAssertNotNil(store.appState.appList.appsLoadingError)
        
        store.dispatch(.loadMoreDone(result: .failure(AppError.networkingFailed(testError))))
        XCTAssertEqual(store.appState.appList.isLoading, false)
        XCTAssertNotNil(store.appState.appList.appsLoadingError)
    }
    
    func testAppStoreReduce_refreshAction() throws {
        let testError = NSError(domain: "testRefreshActionError", code: -1001)
        store.appState.appList.appsLoadingError = AppError.networkingFailed(testError);
        let result = AppStore.reduce(state: store.appState, action: .refresh)
        /// 检查是否符合预期
        XCTAssertNil(result.0.appList.appsLoadingError)
        XCTAssertNotNil(result.1)
        XCTAssertEqual(result.0.appList.isLoading, true)
    }
    
    func testAppStoreReduce_refreshDoneAction() throws {
        let testError = NSError(domain: "testRefreshDoneActionError", code: -1002)
        store.appState.appList.appsLoadingError = AppError.networkingFailed(testError)
        let mockAppLists = TestMockData.mockData()
        let appListCount = mockAppLists.count
        let successRes = AppStore.reduce(state: store.appState, action: .refreshDone(result: .success(mockAppLists)))
        /// 检查是否符合预期
        XCTAssertEqual(successRes.0.appList.isLoading, false)
        XCTAssertEqual(successRes.0.appList.apps?.count ?? 0, appListCount)
        
        let failureRes = AppStore.reduce(state: store.appState, action: .refreshDone(result: .failure(AppError.networkingFailed(testError))))
        XCTAssertEqual(failureRes.0.appList.isLoading, false)
        XCTAssertNotNil(failureRes.0.appList.appsLoadingError)
    }
    
    func testAppStoreReduce_loadMoreAction() throws {
        let testError = NSError(domain: "testLoadMoreActionError", code: -1003)
        store.appState.appList.appsLoadingError = AppError.networkingFailed(testError);
        let result = AppStore.reduce(state: store.appState, action: .loadMore)
        /// 检查是否符合预期
        XCTAssertNil(result.0.appList.appsLoadingError)
        XCTAssertNotNil(result.1)
        XCTAssertEqual(result.0.appList.isLoading, true)
        
    }
    
    func testAppStoreReduce_loadMoreDoneAction() throws {
        let testError = NSError(domain: "testLoadMoreDoneDoneActionError", code: -1004)
        store.appState.appList.appsLoadingError = AppError.networkingFailed(testError)
        let mockAppLists = TestMockData.mockData()
        let appListCount = (store.appState.appList.apps?.count ?? 0) + mockAppLists.count
        let result = AppStore.reduce(state: store.appState, action: .loadMoreDone(result: .success(mockAppLists)))
        XCTAssertEqual(result.0.appList.isLoading, false)
        XCTAssertEqual(result.0.appList.apps?.count ?? 0, appListCount)
        
        let failureRes = AppStore.reduce(state: store.appState, action: .loadMoreDone(result: .failure(AppError.networkingFailed(testError))))
        XCTAssertEqual(failureRes.0.appList.isLoading, false)
        XCTAssertNotNil(failureRes.0.appList.appsLoadingError)
    }
    
    func testAppStoreReduce_toggleFavorite() throws {
        let toggleFavoriteAppId = 100
        let isFavorite = store.appState.isFavoriteApp(id: toggleFavoriteAppId)
        XCTAssertEqual(isFavorite, false)
        
        let result = AppStore.reduce(state: store.appState, action: .toggleFavorite(id: toggleFavoriteAppId))
        XCTAssertEqual(result.0.isFavoriteApp(id: toggleFavoriteAppId), true)
    }
}
