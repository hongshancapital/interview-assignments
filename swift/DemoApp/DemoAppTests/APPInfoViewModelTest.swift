//
//  APPInfoViewModelTest.swift
//  DemoAppTests
//
//  Created by xiongmin on 2022/4/6.
//

import XCTest
@testable import DemoApp

class APPInfoViewModelTest: XCTestCase {
    // 数据在主线程进行更新，这里需要在主线程进行判断

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    @MainActor func testInvalidParams() async throws {
        let appInfoViewModel = AppInfoViewModel()
        await appInfoViewModel.refresh(pageSize: 50, limit: 10)
        XCTAssert(appInfoViewModel.shouldShowErrorView == true)
    }
    
    @MainActor func testHasMore() async {
        let appInfoViewModel = AppInfoViewModel()
        await appInfoViewModel.refresh(pageSize: 10, limit: 50)
        XCTAssert(appInfoViewModel.hasMore == true)
    }
    
    @MainActor func testNotHasMore() async throws {
        let appInfoViewModel = AppInfoViewModel()
        await appInfoViewModel.refresh(pageSize: 30, limit: 30)
        XCTAssert(appInfoViewModel.hasMore == false)
    }
    
    @MainActor func testLoadMoreHasMore() async throws {
        // load more之后还有数据
        let appInfoViewModel = AppInfoViewModel()
        await appInfoViewModel.refresh(pageSize: 10, limit: 50)
        await appInfoViewModel.loadMore()
        XCTAssert(appInfoViewModel.hasMore == true)
    }
    
    @MainActor func testLoadMoreNotHasMore() async throws {
        // load more之后没数据了
        let appInfoViewModel = AppInfoViewModel()
        await appInfoViewModel.refresh(pageSize: 40, limit: 50)
        await appInfoViewModel.loadMore()
        XCTAssert(appInfoViewModel.hasMore == false)
    }

}
