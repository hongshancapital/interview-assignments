//
//  ContentViewModelTest.swift
//  ListDemoTests
//
//  Created by renhe on 2022/6/24.
//


import XCTest
@testable import ListDemo

class ContentViewModelTest: XCTestCase {
    var viewModel: ContentViewModel!

    override func setUpWithError() throws {
        try super.setUpWithError()

        // Put setup code here. This method is called before the invocation of each test method in the class.
        viewModel = ContentViewModel.init()

    }

    override func tearDownWithError() throws {
        try super.tearDownWithError()
        viewModel = nil

        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    ///测试下拉刷新
    @MainActor func testRefresh() async throws{
        let initialAppCount = viewModel.appInfos.count
        await viewModel.refresh()
        XCTAssertTrue(viewModel.appInfos.count == Config.pageSize)
        XCTAssertFalse(viewModel.appInfos.count == initialAppCount)
    }
    /// 测试点击喜欢后，刷新后保持不变
    func testLikeId() async throws {
        await viewModel.refresh()
        let liked = viewModel.appInfos.first!.isLiked
        viewModel.updateLikeByTrackId(byTrackId: "\(viewModel.appInfos.first?.trackId ?? 0)")
        let updatedLiked = viewModel.appInfos.first!.isLiked
        XCTAssertTrue(liked != updatedLiked)
        await viewModel.refresh()
        XCTAssertTrue(viewModel.appInfos.first!.isLiked == updatedLiked)
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
