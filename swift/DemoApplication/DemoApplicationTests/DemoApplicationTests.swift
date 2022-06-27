//
//  DemoApplicationTests.swift
//  DemoApplicationTests
//
//  Created by aut_bai on 2022/6/25.
//

import XCTest
@testable import DemoApplication

class DemoApplicationTests: XCTestCase {

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
    
    let viewModel = ApplicationViewModel()
    
    func testRefresh() throws {
        viewModel.refresh()
        
        let expectation = self.expectation(description: "下拉刷新")
        DispatchQueue.global().asyncAfter(deadline: .now() + 5) {
            expectation.fulfill()
        }
        waitForExpectations(timeout: 10)
        XCTAssert(viewModel.items.count == 20, "获取的items应为20")
    }
    
    //MARK: 测试前请先执行一次`testRefresh`方法
    func testLoadNextpage() throws {
        viewModel.loadNextPage()
        
        let expectation = self.expectation(description: "上滑或上拉加载更多")
        DispatchQueue.global().asyncAfter(deadline: .now() + 5) {
            expectation.fulfill()
        }
        waitForExpectations(timeout: 10)
        XCTAssert(viewModel.items.count == 40, "获取的items应为40")
    }
    
    func testMaxLimit() throws {
        let viewModel = ApplicationViewModel(10)
        
        viewModel.refresh()
        
        let expectation = self.expectation(description: "获取数据上限")
        DispatchQueue.global().asyncAfter(deadline: .now() + 5) {
            expectation.fulfill()
        }
        waitForExpectations(timeout: 10)
        XCTAssert(viewModel.items.count == 10, "获取的items应为10")
    }
    
    
    //MARK: 测试请求结果为空数据时，请禁用电脑或手机网络
    func testRequest() throws {
        let request = request()
        let expectation = XCTestExpectation(description: "从 \(String(describing: request.url)) 获取数据")
        
        let publisher = NetworkRequestService
            .shared
            .request(request)
            .map { items -> [ApplicationItem] in
                    return Array(items)
            }
            .sink(receiveCompletion: {
                print(".sink() 收到结束回调", String(describing: $0))
                switch $0 {
                case .finished: expectation.fulfill()
                case .failure: XCTFail()
                }
            }, receiveValue: { someValue in   //([ApplicationItem])
                XCTAssertNotNil(someValue)
                XCTAssertTrue(someValue.count > 0)
            })
        XCTAssertNotNil(publisher)
        wait(for: [expectation], timeout: 5.0)
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
    private func request() -> URLRequest {
        let urlString = """
        https://itunes.apple.com/search?\
        entity=software&limit=20&term=chat
        """
        
        let url = URL(string: urlString)!
        return URLRequest(url: url, timeoutInterval: 20)
    }

}
