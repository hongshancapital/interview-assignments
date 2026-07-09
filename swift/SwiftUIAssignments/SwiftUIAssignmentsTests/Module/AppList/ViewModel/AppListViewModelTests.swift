//
//  AppListViewModelTests.swift
//  SwiftUIAssignmentsTests
//
//  Created by zcj on 2023/6/7.
//

import XCTest
@testable import SwiftUIAssignments

@MainActor
final class AppListViewModelTests: XCTestCase {

    private var viewModel: AppListViewModel!

    // MARK: - system
    override func setUpWithError() throws {
        viewModel = AppListViewModel()
    }

    override func tearDownWithError() throws {
        viewModel = nil
    }

    func testInitializerWithDefaultArguments() {
        // Given, When
        let viewModel = AppListViewModel()

        // Then
        XCTAssertEqual(viewModel.appModels.isEmpty, true)
        XCTAssertTrue(viewModel.isInitial)
        XCTAssertFalse(viewModel.isLoading)
        XCTAssertFalse(viewModel.noMoreData)
        XCTAssertFalse(viewModel.isShowAlert)
        XCTAssertEqual(viewModel.alertMessage, "")
    }

    func testInitAppList() async {
        // Given
        viewModel.setAppModelsForTesting([])
        viewModel.setIsInitialForTesting(true)

        // When
        await viewModel.initAppList()

        // Then
        XCTAssertFalse(viewModel.isInitial)
        XCTAssertFalse(viewModel.isLoading)
        XCTAssertFalse(viewModel.noMoreData)
        XCTAssertFalse(viewModel.appModels.isEmpty)
    }

    func testRefresh() async {
        // Given
        viewModel.setAppModelsForTesting([])
        viewModel.setNoMoreDataForTesting(true)

        // When
        await viewModel.refresh()

        // Then
        XCTAssertFalse(viewModel.isLoading)
        XCTAssertFalse(viewModel.noMoreData)
        XCTAssertEqual(viewModel.appModels.count, 20)

    }

    func testLoadMore() async {
        // Given
        viewModel.setAppModelsForTesting([])
        viewModel.setNoMoreDataForTesting(false)

        // When
        await viewModel.loadMore()

        // Then
        XCTAssertFalse(viewModel.noMoreData)
        XCTAssertFalse(viewModel.appModels.isEmpty)
    }

    func testLike() {
        // Given
        let mock = AppModel.mock
        viewModel.setAppModelsForTesting([mock])
        viewModel.likedAppIds = []

        // When
        viewModel.like(mock)

        // Then
        XCTAssertTrue(viewModel.appModels[0].isLike)
        XCTAssertTrue(viewModel.likedAppIds.contains(mock.id))
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
