//
//  AppListViewModelTests.swift
//  AppGalleryTests
//
//  Created by X Tommy on 2022/8/11.
//

import XCTest
@testable import AppGallery

class AppListViewModelTests: XCTestCase {
    
    private let trackService = MockAppListService()
    
    private lazy var viewModel = AppListViewModel(service: trackService, pageSize: 2)
    
    override func tearDownWithError() throws {
        viewModel.loadState = .idle
    }
    
    func testFetchingState() async throws {
        let delayService = MockDelayAppListService()
        let delayViewModel = AppListViewModel(service: delayService, pageSize: 2)
        XCTAssertFalse(delayViewModel.isFetching)
        
        Task {
            try await withThrowingTaskGroup(of: Void.self) { group in
                group.addTask {
                    await delayViewModel.refresh()
                }
                
                group.addTask {
                    Thread.sleep(forTimeInterval: 0.1)
                    XCTAssertTrue(delayViewModel.isFetching)
                }
                
                try await group.waitForAll()
                XCTAssertFalse(delayViewModel.isFetching)
            }
        }

        delayService.error = MockError.`default`
        await delayViewModel.refresh()
        XCTAssertFalse(delayViewModel.isFetching)
    }
    
    func testLoadState() async throws {
        
        // case idle
        let delayService = MockDelayAppListService()
        let delayViewModel = AppListViewModel(service: delayService, pageSize: 2)
        guard case .idle = delayViewModel.loadState else {
            throw MockError.default
        }
        
        // case error
        delayService.error = MockError.default
        await delayViewModel.load()
        guard case .error(_ ) = delayViewModel.loadState else {
            throw MockError.default
        }
        
        delayService.apps = AppListSuccessfulMock.listOfTwo
        Task {
            try await withThrowingTaskGroup(of: Void.self) { group in
                group.addTask {
                    await delayViewModel.load()
                }
                group.addTask {
                    Thread.sleep(forTimeInterval: 0.1)
                    // case loading
                    guard case .loading = delayViewModel.loadState else {
                        throw MockError.default
                    }
                }
                
                try await group.waitForAll()
                // case loaded
                guard case .loaded(_, _) = delayViewModel.loadState else {
                    throw MockError.default
                }
            }
        }

    }
    
    func testSuccessfulRefresh() async throws {
        trackService.apps = AppListSuccessfulMock.listOfTwo
        await viewModel.refresh()
        
        if case .loaded(let content, let hasMore) = viewModel.loadState {
            XCTAssertEqual(content, AppListSuccessfulMock.listOfTwo)
            XCTAssertTrue(hasMore)
        } else {
            XCTFail()
        }
    }
    
    func testHasMoreDataFalse() async {
        trackService.apps = AppListSuccessfulMock.listOfOne
        await viewModel.refresh()
        
        if case .loaded(_, let hasMore) = viewModel.loadState {
            XCTAssertFalse(hasMore)
        } else {
            XCTFail()
        }
    }
    
    func testHasMoreDataTrue() async {
        trackService.apps = AppListSuccessfulMock.listOfTwo
        await viewModel.refresh()
        
        if case .loaded(_, let hasMore) = viewModel.loadState {
            XCTAssertTrue(hasMore)
        } else {
            XCTFail()
        }
    }
    
    func testSuccessfulLoadMore() async throws {
        trackService.apps = AppListSuccessfulMock.listOfTwo
        await viewModel.refresh()
        
        trackService.apps = AppListSuccessfulMock.listOfFour
        await viewModel.loadMore()
        
        if case .loaded(let content, _) = viewModel.loadState {
            XCTAssertEqual(content, AppListSuccessfulMock.listOfFour)
        } else {
            XCTFail()
        }
    }
    
    func testFailedRefresh() async throws {
        trackService.error = MockError.default
        await viewModel.refresh()
        
        guard case .error(_ ) = viewModel.loadState else {
            throw MockError.default
        }
    }
    
    func testFailedLoadMore() async throws {
        
        trackService.apps = AppListSuccessfulMock.listOfTwo
        await viewModel.refresh()
        
        trackService.error = MockError.default
        await viewModel.loadMore()
        
        guard case .error(_ ) = viewModel.loadState else {
            throw MockError.default
        }
        
    }
    
}
