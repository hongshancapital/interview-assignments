//
//  AppListViewModelTests.swift
//  AppCollectionsTests
//
//  Created by Guang Lei on 2022/3/12.
//

import XCTest
@testable import AppCollections

@MainActor class AppListViewModelTests: XCTestCase {
    
    let viewModel = AppListViewModel()
    let searchNetwork = MockSearchNetwork()

    @MainActor override func setUpWithError() throws {
        searchNetwork.result = nil
        viewModel.searchNetwork = searchNetwork
        viewModel.list = []
        viewModel.isFirstLoading = true
        viewModel.noMoreData = false
        viewModel.showError = false
        viewModel.favoriteAppIds = []
    }
    
    // MARK: - First load
    
    func testFirstLoad() async throws {
        // Given
        let rspData = AppListMockData.rspData1
        searchNetwork.result = .success(rspData)
        
        // When
        await viewModel.firstLoad()
        
        // Then
        XCTAssertFalse(viewModel.isFirstLoading)
    }
    
    // MARK: - Refresh
    
    func testRefreshSuccess() async throws {
        // Given
        let rspData = AppListMockData.rspData1
        searchNetwork.result = .success(rspData)

        // When
        await viewModel.refresh()
        
        // Then
        XCTAssert(rspData.results.count == viewModel.list.count)
        
        let rspApp1 = rspData.results.first!
        let displayedApp1 = viewModel.list.first!
        XCTAssert(rspApp1.trackId == displayedApp1.trackId)
        XCTAssert(rspApp1.trackName == displayedApp1.trackName)
        XCTAssert(rspApp1.description == displayedApp1.description)
        XCTAssert(rspApp1.artworkUrl60 == displayedApp1.artworkUrl60)
    }
    
    func testRefreshFailure() async throws {
        // Given
        let rspError = AppListMockData.rspError
        searchNetwork.result = .failure(rspError)
        
        // When
        await viewModel.refresh()
        
        // Then
        XCTAssertTrue(viewModel.list.isEmpty)
        XCTAssert(viewModel.alertMessage == rspError.localizedDescription)
    }
    
    func testRefreshNoAvailableData() async throws {
        // Given
        let rspEmptyData = AppListMockData.rspEmptyData
        searchNetwork.result = .success(rspEmptyData)
        
        // When
        await viewModel.refresh()
        
        // Then
        XCTAssertTrue(viewModel.list.isEmpty)
        XCTAssertTrue(viewModel.alertMessage == "No available data.")
    }
    
    // MARK: - Load more
    
    func testLoadMoreSuccess() async throws {
        // Given
        let rspData1 = AppListMockData.rspData1
        searchNetwork.result = .success(rspData1)
        await viewModel.refresh()
        
        let rspData2 = AppListMockData.rspData2
        searchNetwork.result = .success(rspData2)

        // When
        await viewModel.loadMore()
        
        // Then
        XCTAssert(viewModel.list.count == (rspData1.results.count + rspData2.results.count))
        
        let rspApp3 = rspData2.results.last!
        let displayedApp3 = viewModel.list.last!
        XCTAssert(rspApp3.trackId == displayedApp3.trackId)
        XCTAssert(rspApp3.trackName == displayedApp3.trackName)
        XCTAssert(rspApp3.description == displayedApp3.description)
        XCTAssert(rspApp3.artworkUrl60 == displayedApp3.artworkUrl60)
    }
    
    func testLoadMoreFailure() async throws {
        // Given
        let rspError = AppListMockData.rspError
        searchNetwork.result = .failure(rspError)

        // When
        await viewModel.loadMore()
        
        // Then
        XCTAssertTrue(viewModel.list.isEmpty)
        XCTAssert(viewModel.alertMessage == rspError.localizedDescription)
    }
    
    func testLoadMoreNoMoreData() async throws {
        // Given
        let rspData1 = AppListMockData.rspData1
        searchNetwork.result = .success(rspData1)
        await viewModel.refresh()
        
        let rspEmptyData = AppListMockData.rspEmptyData
        searchNetwork.result = .success(rspEmptyData)
        
        // When
        await viewModel.loadMore()
        
        // Then
        XCTAssert(viewModel.list.count == rspData1.results.count)
        XCTAssertTrue(viewModel.noMoreData)
    }
    
    // MARK: - Favor

    func testFavor() {
        // Given
        let unfavoriteApp = AppListMockData.unfavoriteApp
        
        // When
        viewModel.favor(unfavoriteApp)
        
        // Then
        XCTAssert(viewModel.favoriteAppIds.contains(unfavoriteApp.trackId))
    }
    
    func testUnfavor() {
        // Given
        let favoriteApp = AppListMockData.favoriteApp
        viewModel.favoriteAppIds.append(favoriteApp.trackId)
        
        // When
        viewModel.favor(favoriteApp)
        
        // Then
        XCTAssertFalse(viewModel.favoriteAppIds.contains(favoriteApp.trackId))
    }
}
