//
//  FWDemoTests.swift
//  FWDemoTests
//
//  Created by wei feng on 2022/6/21.
//

import XCTest
@testable import FWDemo

class FWDemoTests: XCTestCase {

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

    func testApplistApi() throws {
        var data = [AppInfo]()
        _ = NetworkManager.shared.sendRequest(requestType: AppInfoApis.getAppInfolist(1, 20))
        .tryMap{ $0.data }
        .decode(type: AppApiResponse.self, decoder: JSONDecoder())
        .sink(receiveCompletion: {completion in
            switch completion {
                case .finished:
                    print("==== success")
                case .failure(let e):
                    XCTAssert(false, "AppList接口请求出错:\(e)")
            }
        }, receiveValue: {response in
            data = response.results
        })
        let exp = self.expectation(description: "等待接口请求")
        DispatchQueue.global().asyncAfter(deadline: .now() + 10) {
            exp.fulfill()
        }
        waitForExpectations(timeout: 10)
        XCTAssert(data.count > 0, "AppList接口请求出错")
    }
}
