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
        do {
            try await appInfoViewModel.refresh(pageSize: 50, limit: 10)
        } catch {
            if (error as NSError).code == -999 {
                // pageSize > limit 或者limit|pageSize < 0为-999
                XCTAssert(true)
            }
        }
    }
    
    @MainActor func testHasMore() async throws {
        let appInfoViewModel = AppInfoViewModel()
        do {
            try await appInfoViewModel.refresh(pageSize: 10, limit: 50)
            XCTAssert(appInfoViewModel.hasMore == true)
        } catch {
            XCTAssert(false)
        }
    }
    
    @MainActor func testNotHasMore() async throws {
         let appInfoViewModel = AppInfoViewModel()
        do {
            try await appInfoViewModel.refresh(pageSize: 30, limit: 30)
            XCTAssert(appInfoViewModel.hasMore == false)
        } catch {
            XCTAssert(false)
        }
    }
    
    @MainActor func testLoadMoreHasMore() async throws {
        // load more之后还有数据
        let appInfoViewModel = AppInfoViewModel()
        do {
            try await appInfoViewModel.refresh(pageSize: 10, limit: 50)
            try await appInfoViewModel.loadMore()
            XCTAssert(appInfoViewModel.hasMore == true)
        } catch {
            XCTAssert(false)
        }
    }
    
    @MainActor func testLoadMoreNotHasMore() async throws {
        // load more之后没数据了
        let appInfoViewModel = AppInfoViewModel()
        do {
            try await appInfoViewModel.refresh(pageSize: 40, limit: 50)
            try await appInfoViewModel.loadMore()
            XCTAssert(appInfoViewModel.hasMore == false)
        } catch {
            XCTAssert(false)
        }
    }

}
