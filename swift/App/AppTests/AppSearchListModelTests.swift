//
//  AppSearchListModelTests.swift
//  AppTests
//
//  Created by august on 2022/3/24.
//

import XCTest
@testable import App

class MockNetworkAPI: NetworkAPI<HomeListResponse> {
    
    var limit = 50
    
    override func fetchResponse(with method: String = "GET", paramaters: [String : Any]? = nil) async throws -> HomeListResponse {
        return HomeListResponse(resultCount: limit, results: (0..<limit).map({ index in
            AppInformation(trackId: index, trackName: "trackName\(index)", description: "description\(index)", artworkUrl60: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/86/a6/bf/86a6bf39-bf4b-25d1-451b-28eef9c15717/source/100x100bb.jpg")!)
        }))
    }
}

class AppSearchListModelTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    @MainActor func testLimitGreaterThanPageSize() async throws {
        let listModel = AppSearchListModel()
        let mockRequest = MockNetworkAPI(path: "")
        mockRequest.limit = 50
        listModel.chatAppRequest = mockRequest
        try? await listModel.refresh(pageSize: 10)
        //当limit大于pageSize是，结果最多为pageSize
        XCTAssertTrue(listModel.apps.count == 10)
        //可以翻页
        XCTAssertTrue(listModel.hasMoreData == true)
        //加载更多
        try? await listModel.loadMore(pageSize: 10)
        XCTAssertTrue(listModel.apps.count == 20)
        //翻页获取app数量最多为limit
        try? await listModel.loadMore(pageSize: 40)
        XCTAssertTrue(listModel.apps.count == 50)
        XCTAssertTrue(listModel.hasMoreData == false)
    }
    
    @MainActor func testLimitLessThanPageSize() async throws {
        let listModel = AppSearchListModel()
        let mockRequest = MockNetworkAPI(path: "")
        mockRequest.limit = 10
        listModel.chatAppRequest = mockRequest
        try? await listModel.refresh(pageSize: 20)
        //当limit小于pageSize是，结果最多为limit
        XCTAssertTrue(listModel.apps.count <= 10)
        //不能翻页
        XCTAssertTrue(listModel.hasMoreData == false)
    }
}
