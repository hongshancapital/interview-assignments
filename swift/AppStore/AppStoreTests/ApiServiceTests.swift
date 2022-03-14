//
//  ApiServiceTests.swift
//  AppStoreTests
//
//  Created by Ma on 2022/3/13.
//

import XCTest
//@testable import AppStore

class ApiServiceTests: XCTestCase {
    var apiService : ApiService?
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        apiService = ApiService()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        apiService = nil
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
    func testRequest() throws {
        // https://itunes.apple.com/search?entity=software&limit=50&term=chat&offset=0
        let req = URLRequest(url: URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat&offset=0")!)
        apiService?.request(req: req, res: AppData.self)
            .sink(receiveCompletion: { completion in
                switch completion {
                case .failure(let error):
                    
                case .finished
                }
            }, receiveValue: { appData in
                
            })
    }

}
