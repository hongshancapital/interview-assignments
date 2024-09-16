//
//  OTViewModelTests.swift
//  OTAppStoreTests
//
//  Created by liuxj on 2022/3/19.
//

import XCTest

@testable import OTAppStore

@MainActor
class OTViewModelTests: XCTestCase {

    let viewModel = OTAppViewModel()

    @MainActor override func setUpWithError() throws {
        viewModel.appModelList = []
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testRefresh() async throws {
        XCTAssert(viewModel.appModelList.count == 0)

        //刷新
        await viewModel.refreshData()
        XCTAssert(viewModel.appModelList.count > 0)
        XCTAssert(!viewModel.hasError)
        XCTAssertNil(viewModel.errorMessage)
    }

    func testLoadMore() async throws {
        XCTAssert(viewModel.appModelList.count == 0)

        //刷新
        await viewModel.refreshData()
        let refreshCount = viewModel.appModelList.count
        XCTAssert(refreshCount > 0)

        //加载更多
        await viewModel.loadMoreData()
        XCTAssertGreaterThan(viewModel.appModelList.count, refreshCount)
    }

    func testFavoriteApp() async throws {
        XCTAssert(viewModel.appModelList.count == 0)
        await viewModel.refreshData()

        //刷新
        XCTAssert(viewModel.appModelList.count > 0)
        let id = viewModel.appModelList.first!.id

        //选中
        viewModel.favoriteApp(id: id)
        XCTAssert(viewModel.appModelList.first!.isFavorite)

        //反选
        viewModel.favoriteApp(id: id)
        XCTAssert(!viewModel.appModelList.first!.isFavorite)
    }


    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
