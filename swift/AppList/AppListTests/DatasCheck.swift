//
//  DatasCheck.swift
//  AppListTests
//
//  Created by mengyun on 2022/3/20.
//

import XCTest
import Combine
@testable import AppList

class ApiServiceTests: XCTestCase {
    
    private var cancellableSet: Set<AnyCancellable> = []
    var loadMoreSubject = CurrentValueSubject<Void, Never>(())
    var pageSize: Int = 20
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func test_datas() throws {
        guard let url = Bundle.main.url(forResource: "mock", withExtension: "txt") else { return }
        guard let data = try? Data(contentsOf: url) else { return }
        guard let lisModel = try? JSONDecoder().decode(ListModel.self, from: data)  else { return }
        
        Just(lisModel.results)
            .eraseToAnyPublisher()
            .receive(on: RunLoop.main)
            .sink { completion in
                switch completion {
                case .finished:
                    break
                case .failure(let error):
                    print("Error--: \(error)")
                    break
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
