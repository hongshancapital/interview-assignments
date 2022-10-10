//
//  MockNetworkServiceTests.swift
//  ListDemoTests
//
//  Created by Chr1s on 2022/2/25.
//

import XCTest
import Combine
@testable import ListDemo

class ApiServiceTests: XCTestCase {

    let service = ApiService()
    var cancellables = Set<AnyCancellable>()
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func test_ApiService_fetchListData_shouldReturnPublisherCorrect() {
        service.fetchListData()
            .sink { completion in
                switch completion {
                case .failure(let error):
                    XCTFail("\(error)")
                default:
                    break
                }
            } receiveValue: { value in
                XCTAssertNotNil(value)
                XCTAssertEqual(value.count, 50)
                for i in 0..<value.count {
                    // 数据是否有效
                    XCTAssertNotEqual(value[i].artworkUrl60, "")
                    XCTAssertNotEqual(value[i].trackId, 0)
                    XCTAssertNotEqual(value[i].trackName, "")
                    XCTAssertNotEqual(value[i].description, "")
                    // isFavorite是否初始化
                    XCTAssertNotNil(self.service.isFavoriteArray)
                }
            }
            .store(in: &cancellables)
    }
    
    func test_ApiService_fetchFavoriteData_shouldNotEmptyOrNil() {
        let value = service.fetchFavoriteData()
        XCTAssertNotNil(value)
        XCTAssertEqual(value.count, 50)
    }
    
    func test_ApiService_updateListData_shouldReturnPublisherCorrect() {
        for i in 0..<50 {
            let fav: Bool = Bool.random()
            service.updateListData(id: i, isFavorite: fav)
                .sink { value in
                    XCTAssertNotNil(value)
                    XCTAssertEqual(value.count, 50)
                    XCTAssertEqual(value[i], fav)
                }
                .store(in: &cancellables)
        }
    }
    
    func test_ApiService_updateListData_shouldReturnEmptyArray() {
        service.updateListData(id: -1, isFavorite: false)
            .sink { value in
                XCTAssertNotNil(value)
                XCTAssertEqual(value.count, 0)
            }
            .store(in: &cancellables)
        
        service.updateListData(id: 51, isFavorite: false)
            .sink { value in
                XCTAssertNotNil(value)
                XCTAssertEqual(value.count, 0)
            }
            .store(in: &cancellables)
    }
}
