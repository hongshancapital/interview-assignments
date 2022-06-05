//
//  IAHomeViewModelTests.swift
//  InterviewAssignmentsTests
//
//  Created by ZhuChaoJun on 2022/6/2.
//

import XCTest
@testable import InterviewAssignments

class IAHomeViewModelTests: XCTestCase {

    var homeViewModel: IAHomeViewModel!

    override func setUpWithError() throws {
        homeViewModel = IAHomeViewModel()
    }

    override func tearDownWithError() throws {

    }

    func testInitDataSources() async {
        // 第一次启动加载
        XCTAssertTrue(homeViewModel.isLoading)
        await homeViewModel.initDataSources()
        // 第一次启动加载完成
        XCTAssertFalse(homeViewModel.isLoading)

        XCTAssertEqual(homeViewModel.cellViewModels.count, 20, "初始化后，数据应为20条")
    }

    func testRefresh() async {
        await homeViewModel.refresh()

        // no more data is false
        XCTAssertFalse(homeViewModel.noMoreData)
        // 刷新后数据为20条
        XCTAssertEqual(homeViewModel.cellViewModels.count, 20, "刷新后，数据应为20条")
    }

    func testLoadMore() async {
        await homeViewModel.initDataSources()
        await homeViewModel.loadMore()

        // 第2页，应该40条
        XCTAssertEqual(homeViewModel.cellViewModels.count, 40, "第二页加载后，数据应为40条")

        // 第3页，应该为50条
        await homeViewModel.loadMore()
        XCTAssertEqual(homeViewModel.cellViewModels.count, 50, "第三页加载后，数据应为50条")
    }
}
