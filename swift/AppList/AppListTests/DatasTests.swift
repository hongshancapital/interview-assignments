//
//  DatasCheck.swift
//  AppListTests
//
//  Created by mengyun on 2022/3/20.
//

import XCTest
import Combine
@testable import AppList

class DatasTests: XCTestCase {
    
    private var cancellableSet: Set<AnyCancellable> = []
    var loadMoreSubject = CurrentValueSubject<Void, APIError>(())
    var pageSize: Int = 20
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func test_datas() throws {
        let apiManager = ListAPIManager(path: "https://itunes.apple.com/search?entity=software&limit=50&term=chat")
        apiManager.fetchListData()
            .eraseToAnyPublisher()
            .receive(on: RunLoop.main)
            .sink { completion in
                switch completion {
                case .finished:
                    print("finished")
                case .failure(let error):
                    XCTFail("\(error.message)")
                }
            } receiveValue: { value in
                XCTAssertNotNil(value.count)
                XCTAssertEqual(value.count, 50)
                for i in 0..<value.count {
                    let artworkUrl = value[i].artworkUrl60
                    XCTAssertNotEqual(artworkUrl, "", "artworkUrl is nil!!!")
                }
            }
            .store(in: &cancellableSet)
    }
    
    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
}
