//
//  SwiftUIAppTests.swift
//  SwiftUIAppTests
//
//  Created by quanhai on 2022/6/28.
//

import XCTest
@testable import SwiftUIApp

class SwiftUIAppTests: XCTestCase {
	
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
//MARK: - 测试app fectch
	func testAppRefreshFectch() async throws{
		let viewModel = AppViewModel()
		try await viewModel.getAppList()
		
		XCTAssert(viewModel.appList.count == viewModel.pageNumber * viewModel.pageSize, "刷新获取到的数据长度异常")
		XCTAssert(viewModel.hasMoreData, "理论上最多有\(viewModel.maxDataCount)，hasMoreData 应该为true")
		XCTAssert(!viewModel.isFecthing, "请求结束, isFecthing 应为false")
		XCTAssert(viewModel.pageNumber == 1, "刷新时 pagenumber = 1")
	}
	
	func testAppMutilRefreshFetch() async throws{
		let viewModel = AppViewModel()
		for _ in 0..<10{
			try await viewModel.getAppList()
		}
		
		XCTAssert(viewModel.appList.count == viewModel.pageNumber * viewModel.pageSize, "刷新获取到的数据长度异常")
		XCTAssert(viewModel.hasMoreData, "理论上最多有\(viewModel.maxDataCount)，hasMoreData 应该为true")
		XCTAssert(!viewModel.isFecthing, "请求结束, isFecthing 应为false")
		XCTAssert(viewModel.pageNumber == 1, "刷新时 pagenumber = 1")
	}
	
	func testAppLoadMoreFetch() async throws{
		let viewModel = AppViewModel()
		try await viewModel.getAppList()
		for i in 1..<5{
			try await viewModel.getMoreAppList()
			XCTAssert(viewModel.pageNumber <= viewModel.maxDataCount/viewModel.pageSize, "pageNumber 异常")
			if i >= 3{
				XCTAssert(viewModel.pageNumber == 3, "pagenumber 在没有更多数据时应不再增长")
			}
			
			XCTAssert(!viewModel.isFecthing, "每次请求结束, isFecthing 应为false")
			XCTAssert(viewModel.appList.count == viewModel.pageNumber*viewModel.pageSize, "第\(i)次loadmore 获取数据长度异常")
		}
		XCTAssert(viewModel.appList.count <= viewModel.maxDataCount, "多次loadmore 获取数据长度异常")
	}
	
//MARK: - 测试app喜爱的存取
	func testAppLike() async throws{
		let viewModel = AppViewModel()
		try await viewModel.getAppList()
		guard let app = viewModel.appList.first else{
			XCTAssert(viewModel.appList.count > 0, "未获取到数据")
			return
		}
		await viewModel.updateAppLike( app.id, isLike: true)
		let likeApp = viewModel.appList.first!
		let isLike = viewModel.appIsLike(app.id)
		XCTAssert(isLike, "app like 应该为true")
		XCTAssert(likeApp.like, "app list app like 应该为true")
		
		await viewModel.updateAppLike( app.id, isLike: false)
		let unLikeApp = viewModel.appList.first!
		let unLike = viewModel.appIsLike(app.id)
		XCTAssert(!unLike, "app like 应该为false")
		XCTAssert(unLikeApp.like, "app list app like 应该为false")
	}
}
