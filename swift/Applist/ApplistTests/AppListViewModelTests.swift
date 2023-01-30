//
//  AppListViewModelTests.swift
//  ApplistTests
//
//  Created by wulei7 on 2023/1/30.
//

@testable import Applist
import XCTest

final class AppListViewModelTests: XCTestCase {
    let viewModel = AppListViewModel(page: 0)
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    func testToggleLikedData() throws {
        
        // 数据源为空，喜欢不生效
        viewModel.resultData = AppListMockData.response1
        viewModel.toggleLikedData(appModel: AppListMockData.likedApp)
        XCTAssertTrue(viewModel.likedList.count == 0)
        viewModel.toggleLikedData(appModel: AppListMockData.likedApp)
        XCTAssertTrue(viewModel.likedList.count == 0)
        
        // 喜欢切换是否正常生效
        viewModel.resultData = AppListMockData.response2
        viewModel.toggleLikedData(appModel: AppListMockData.likedApp)
        XCTAssertTrue(viewModel.likedList.contains(where: { $0.trackId == AppListMockData.likedApp.trackId }))
        viewModel.toggleLikedData(appModel: AppListMockData.likedApp)
        XCTAssertFalse(viewModel.likedList.contains(where: { $0.trackId == AppListMockData.likedApp.trackId }))
        
    }
}
