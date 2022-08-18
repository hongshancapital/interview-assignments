//
//  AppListViewModelTests.swift
//  AppListDemoTests
//
//  联系方式：微信(willn1987)
//
//  Created by HQ on 2022/8/18.
//

import XCTest

class AppListViewModelTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
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
    
    func testLoadDataSucces() async throws {
        let viewModel = AppListViewModel()
        await viewModel.requestAppListData(.load)
        
        XCTAssertTrue(viewModel.listData.count > 0, "接口返回空列表")
    }
    
    func testLoadDataCount() async throws {
        let viewModel = AppListViewModel()
        await viewModel.requestAppListData(.load)
        print(viewModel.listData.count)
        XCTAssertTrue(viewModel.listData.count == 10, "加载列表返回条数不正确")
    }
    
    func testLoadMoreDataSucces() async throws {
        let viewModel = AppListViewModel()
        await viewModel.requestAppListData(.load)
        let list = viewModel.listData
        await viewModel.requestAppListData(.loadMore)
        
        XCTAssertTrue(viewModel.listData.count > list.count, "加载更多失败")
    }

    func testLikedStateChange() async throws {
        let viewModel = AppListViewModel()
        await viewModel.requestAppListData(.load)
        let likedState = viewModel.listData[0].liked
        await viewModel.sendUpdateLikedState(index: 0, appId: viewModel.listData[0].appId)
        
        XCTAssertTrue(likedState != viewModel.listData[0].liked, "喜欢状态未改变")
    }
}
