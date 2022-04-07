//
//  ViewModelTests.swift
//  InterviewDemoTests
//
//  Created by Chenjun Ren on 2022/4/7.
//

import XCTest
@testable import InterviewDemo

class ViewModelTests: XCTestCase {
    
    var viewModel: ContentViewModel!

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        try super.setUpWithError()
        viewModel = .init(appInfoRepository: MockRepository(numberPerFetch: 10))
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        try super.tearDownWithError()
        viewModel = nil
    }
    
    func testProperInitialization() async throws {
        // ViewModel初始化完成后会fetch一部分数据, 需要等待一下
        try await Task.sleep(nanoseconds: 5_000_000_000)
        XCTAssertFalse(viewModel.appInfos.isEmpty)
        XCTAssertTrue(viewModel.hasMore)
        XCTAssertNil(viewModel.error)
    }
    
    func testIfLikeContentPersisted() async throws {
        try await testProperInitialization()
        let previousLikeStatus = viewModel.appInfos.first!.isLiked
        viewModel.updateLikeStatus(for: viewModel.appInfos.first!)
        let updatedLikeStatus = viewModel.appInfos.first!.isLiked
        XCTAssertTrue(previousLikeStatus != updatedLikeStatus)
        // 确保重新初始化viewModel后like的情况不变
        viewModel = .init(appInfoRepository: MockRepository(numberPerFetch: 10))
        try await testProperInitialization()
        XCTAssertTrue(viewModel.appInfos.first!.isLiked == updatedLikeStatus)
    }
    
    func testPullToRefresh() async throws {
        try await testProperInitialization()
        let initialAppCount = viewModel.appInfos.count
        await viewModel.fetchAppInfos()
        XCTAssertTrue(viewModel.appInfos.count > initialAppCount)
        await viewModel.refresh()
        XCTAssertTrue(viewModel.appInfos.count == initialAppCount)
    }
    
    func testAutoFetch() async throws {
        try await testProperInitialization()
        let appCount = viewModel.appInfos.count
        // 模拟下滑列表
        for index in viewModel.appInfos.indices[0..<appCount-1] {
            viewModel.fetchMoreIfNeeded(current: viewModel.appInfos[index])
            XCTAssertTrue(viewModel.appInfos.count == appCount)
        }
        // 确保只有最后一个item出现的时候才会开始fetch
        viewModel.fetchMoreIfNeeded(current: viewModel.appInfos.last!)
        try await Task.sleep(nanoseconds: 5_000_000_000)
        XCTAssertTrue(viewModel.appInfos.count > appCount)
    }
}
