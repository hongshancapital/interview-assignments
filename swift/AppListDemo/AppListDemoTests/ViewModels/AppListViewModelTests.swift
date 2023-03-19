//
//  AppListViewModelTests.swift
//  AppListDemoTests
//
//  Created by Kimi on 2023/3/7.
//

import XCTest
@testable import AppListDemo

@MainActor
final class AppListViewModelTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testAppListViewModelInitialize() {
        let pageCount = 20
        let viewModel = AppListViewModel(pageCount: pageCount)
        
        XCTAssert(viewModel.appModels.isEmpty)
        XCTAssertEqual(viewModel.pageCount, pageCount)
    }
    
    func testAppListViewModelFirstLoad() async {
        let pageCount = 10
        let viewModel = AppListViewModel(pageCount: pageCount)
        
        XCTAssert(viewModel.isFirstLoading)
        
        await viewModel.firstLoad()
        
        XCTAssert(!viewModel.isFirstLoading)
        XCTAssert(viewModel.hasMoreData)
        XCTAssertEqual(viewModel.appModels.count, pageCount)
    }
    
    func testAppListViewModelLoadSingleApp() async {
        let pageCount = 1
        let viewModel = AppListViewModel(pageCount: pageCount)
        await viewModel.firstLoad()
        
        XCTAssertEqual(viewModel.appModels.count, pageCount)
    }
    
    func testAppListViewModelFirstLoadError() async {
        let pageCount = 10
        let viewModel = AppListViewModel(pageCount: pageCount)
        
        // Mock error data
        viewModel.requestDataService = MockErrorDataService.shared
        await viewModel.firstLoad()
        
        XCTAssert(!viewModel.isFirstLoading)
        XCTAssert(viewModel.appModels.isEmpty)
    }
    
    func testAppListViewModelRefresh() async {
        let pageCount = 10
        let viewModel = AppListViewModel(pageCount: pageCount)
        await viewModel.firstLoad()
        await viewModel.refresh()
        
        XCTAssert(!viewModel.isFirstLoading)
        XCTAssert(viewModel.hasMoreData)
        XCTAssertEqual(viewModel.appModels.count, pageCount)
    }
    
    func testAppListViewModelRefreshError() async {
        let pageCount = 10
        let viewModel = AppListViewModel(pageCount: pageCount)
        await viewModel.firstLoad()
        await viewModel.loadMore()
        let originCount = viewModel.appModels.count

        // Mock error data
        viewModel.requestDataService = MockErrorDataService.shared
        await viewModel.refresh()
        
        XCTAssert(!viewModel.isFirstLoading)
        XCTAssert(viewModel.hasMoreData)
        XCTAssertEqual(viewModel.appModels.count, originCount)
    }
    
    func testAppListViewModelLoadMore() async {
        let testPageCounts = [20, 25]
        
        for pageCount in testPageCounts {
            let viewModel = AppListViewModel(pageCount: pageCount)
            await viewModel.firstLoad()
            
            for i in 0..<3 {
                await viewModel.loadMore()
                if viewModel.hasMoreData {
                    XCTAssertEqual(viewModel.appModels.count, pageCount * (i+2))
                }else {
                    XCTAssertLessThanOrEqual(viewModel.appModels.count, pageCount * (i+2))
                    break
                }
            }
            
            let originCount = viewModel.appModels.count
            await viewModel.loadMore()
            XCTAssertEqual(viewModel.appModels.count, originCount)
        }
    }
    
    func testAppListViewModelRefreshAfterLoadMore() async {
        let pageCount = 10
        let viewModel = AppListViewModel(pageCount: pageCount)
        await viewModel.firstLoad()
        await viewModel.loadMore()
        await viewModel.refresh()
        
        XCTAssert(!viewModel.isFirstLoading)
        XCTAssert(viewModel.hasMoreData)
        XCTAssertEqual(viewModel.appModels.count, pageCount)
    }
    
    func testAppListViewModelToggleFavourite() async {
        let pageCount = 10
        let viewModel = AppListViewModel(pageCount: pageCount)
        await viewModel.firstLoad()
        
        XCTAssertEqual(viewModel.appModels.count, pageCount)

        for _ in 0..<3 {
            let randomIndex = Int.random(in: 0..<pageCount)
            let testModel = viewModel.appModels[randomIndex]
            let originFavourite = testModel.isFavourite
            
            await viewModel.toggleFavouriteApp(testModel)
            
            XCTAssertEqual(viewModel.appModels[randomIndex].isFavourite, !originFavourite)
        }
    }
    
    func testAppListViewModelToggleFavouriteError() async {
        let pageCount = 10
        let viewModel = AppListViewModel(pageCount: pageCount)
        await viewModel.firstLoad()
        
        XCTAssertEqual(viewModel.appModels.count, pageCount)
                
        let randomIndex = Int.random(in: 0..<pageCount)
        let testModel = viewModel.appModels[randomIndex]
        let originFavourite = testModel.isFavourite
        
        // Mock error data
        viewModel.requestDataService = MockErrorDataService.shared
        await viewModel.toggleFavouriteApp(testModel)
        
        XCTAssertEqual(viewModel.appModels[randomIndex].isFavourite, originFavourite)
    }
    
    func testAppListViewModelToggledFavouriteRefresh() async {
        let pageCount = 10
        let viewModel = AppListViewModel(pageCount: pageCount)
        await viewModel.firstLoad()
        await viewModel.loadMore()
        
        XCTAssertEqual(viewModel.appModels.count, pageCount * 2)
        
        let randomIndex = Int.random(in: pageCount..<pageCount*2)
        let testModel = viewModel.appModels[randomIndex]
        let originFavourite = testModel.isFavourite
        
        await viewModel.toggleFavouriteApp(testModel)
        await viewModel.refresh()
        await viewModel.loadMore()
        
        XCTAssertEqual(viewModel.appModels[randomIndex].isFavourite, !originFavourite)
    }
}
