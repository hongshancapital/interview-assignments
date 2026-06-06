//
//  ApiTestCase.swift
//  AppListTests
//
//  Created by dc on 2022/3/23.
//

import XCTest
import Combine
@testable import AppList

class APITests: XCTestCase {
    
    private var cancellableSet: Set<AnyCancellable> = []
    var loadMoreSubject = CurrentValueSubject<Void, APIError>(())
    var pageSize: Int = 20
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    func test_api() throws {
        let paths = [
            "https://itunes.apple.com/search?entity=software&limit=50&term=game",  // 不同类目
            "https://itunes.apple.com/search?entity=software&limit=500&term=people", // 500条数据
            "https://itunssss" // 错误链接
        ]
        
        let apiManager = ListAPIManager(path: paths[0])
        apiManager.fetchListData()
            .flatMap { $0.publisher }
            .collect( pageSize )
            .zip( loadMoreSubject )
            .receive(on: RunLoop.main)
            .handleEvents(
                receiveOutput: { _ in
                })
            .sink { completion in
                switch completion {
                case .finished:
                    print("finished")
                case .failure(let error):
                    XCTFail("\(error.message)")
                }
            } receiveValue: { value in
            }
            .store(in: &self.cancellableSet)
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
}
