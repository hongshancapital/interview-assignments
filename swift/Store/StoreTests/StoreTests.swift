//
//  StoreTests.swift
//  StoreTests
//
//  Created by 张欣宇 on 2022/11/3.
//

import XCTest
@testable import Store


final class StoreTests: XCTestCase {
    
    var viewModel = StoreListViewModel()
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        
    }
    
    func testRequest() throws {
        let url = URL(string: "https://itunes.apple.com/search?entity=software&limit=15&term=chat")
        let promise = expectation(description: "Status code: 200")
        URLSession.shared.dataTask(with: url!) { data, response, error in
            if let error = error {
                XCTFail("Error: \(error.localizedDescription)")
                return
            } else if let statusCode = (response as? HTTPURLResponse)?.statusCode {
                if statusCode == 200 {
                    promise.fulfill()
                } else {
                    XCTFail("Status code: \(statusCode)")
                }
            }
        }.resume()
        wait(for: [promise], timeout: 5)
    }
    
    func testModel() throws {
        viewModel.addApp([AppModel(trackId: 123, trackName: "kael", description: "....", artworkUrl60: "1", like: false)])
        XCTAssertEqual(viewModel.apps.count, 1, "add failed")
        viewModel.clearApps()
        XCTAssertEqual(viewModel.apps.count, 0, "clear failed")
    }
    
    func testLike() throws {
        viewModel.addApp([AppModel(trackId: 123, trackName: "kael", description: "....", artworkUrl60: "1", like: false)])
        XCTAssertEqual(viewModel.apps[0].like, false)
        viewModel.like(viewModel.apps[0].trackId)
        XCTAssertEqual(viewModel.apps[0].like, true)
        viewModel.like(viewModel.apps[0].trackId)
        XCTAssertEqual(viewModel.apps[0].like, false)
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
        measure {
            // Put the code you want to measure the time of here.
        }
    }
    
}
