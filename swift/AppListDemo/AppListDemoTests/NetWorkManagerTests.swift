//
//  NetWorkManagerTests.swift
//  AppListDemoTests
//
//  联系方式：微信(willn1987)
//
//  Created by HQ on 2022/8/18.
//

import XCTest

class NetWorkManagerTests: XCTestCase {

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
    
    func testRequestSuccess() async throws {
        let url = "https://itunes.apple.com/search?entity=software&country=cn&term=效率&limit=10"
        let result = await NetWorkManager.shared.request(url: url, type: ResponseModel<AppInfoModel>.self)
        
        switch result {
        case .success(let data, msg: _):
            XCTAssertTrue(data.results.count > 0, "接口返回空列表")
        case .failure(msg: _):
            XCTAssertTrue(true, "接口请求失败")
        }
    }
    
    func testInvalidUrl() async throws {
        let url = "https://itunes.apple.com"
        let result: ResultModel = await NetWorkManager.shared.request(url: url, type: ResponseModel<AppInfoModel>.self)
        
        switch result {
        case .failure(let msg):
            XCTAssertTrue(msg == "接口请求失败, 无效接口" || msg == "接口请求失败, 数据解析失败", "无效接口失败提示信息错误")
        case .success(_, _):
            XCTAssertTrue(false, "无效接口请求返回成功")
        }
    }

}
