//
//  AppListViewModelTest.swift
//  MyAppsTests
//
//  Created by liangchao on 2022/4/23.
//

import XCTest

class AppListViewModelTest: XCTestCase {
    var listViewModel: AppListViewModel!
    
    /// test app data loading
    func test_loadApps() async throws  {
        
        XCTAssertTrue(listViewModel.state == .notRequested)
        
        listViewModel.loadMyApps();
        try await Task.sleep(nanoseconds: 5 * 100_000_000)
        
        XCTAssertNotNil(listViewModel.viewModels)
        XCTAssertTrue(listViewModel.state == .loaded)
        XCTAssertFalse(listViewModel.viewModels!.isEmpty)
    }
    
    /// test paging load
    func test_loadMore() async throws {
        
        try await test_loadApps()
        
        XCTAssertFalse(listViewModel.state == .notRequested)
        
        let countBefore = listViewModel.viewModels?.count ?? 0
        listViewModel.loadMoreApps();
        try await Task.sleep(nanoseconds: 5 * 100_000_000)
        let countAfter = listViewModel.viewModels?.count ?? 0
        XCTAssertGreaterThan(countAfter, countBefore);
    }
    
    /// test app collected state
    func test_updateCollectedState() {
        let viewModel = AppInfoViewModel(app: AppInfo.mockedData.first!)
        XCTAssertNotNil(viewModel)
        
        let beforeState = viewModel.isCollected
        viewModel.isCollected = !beforeState
        let afterState = viewModel.isCollected
        
        XCTAssertTrue(beforeState != afterState)
    }
    

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        listViewModel = AppListViewModel(service: MockedAppInfoService());
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

}
