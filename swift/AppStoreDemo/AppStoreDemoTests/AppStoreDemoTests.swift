//
//  AppStoreDemoTests.swift
//  AppStoreDemoTests
//
//  Created by wuxi on 2023/3/18.
//

import XCTest
import Combine
@testable import AppStoreDemo

final class AppStoreDemoTests: XCTestCase {
    
    private var publishers: [AnyCancellable] = []
    let store = Store()

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        
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
    
    func testRequestSuccess() throws {
        let url: URL = URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat")!
        let expectation = XCTestExpectation(description: "time_out")
        let publisher = AppRequest().publisher(url: url)
            .sink { completion in
                switch completion {
                case .finished:
                    expectation.fulfill()
                case .failure(let err):
                    XCTFail(err.localizedDescription)
                    expectation.fulfill()
                }
            } receiveValue: { data in
                XCTAssertNotNil(data)
                XCTAssertTrue(data.count > 0)
                XCTAssertTrue(!data[0].title.isEmpty)
            }
            .store(in: &publishers)
        XCTAssertNotNil(publisher)
        wait(for: [expectation], timeout: 30)

    }
    
    func testRequestFail() throws {
        let url: URL = URL(string: "https://itunes.wuxi.com")!
        let expectation = XCTestExpectation(description: "time_out")
        let publisher = AppRequest().publisher(url: url)
            .sink { completion in
                switch completion {
                case .finished:
                    expectation.fulfill()
                case .failure(let err):
                    XCTExpectFailure(err.localizedDescription)
                    expectation.fulfill()
                }
            } receiveValue: { data in
                XCTAssertNotNil(data)
                XCTAssertTrue(data.count > 0)
                XCTAssertTrue(!data[0].title.isEmpty)
            }
            .store(in: &publishers)
        XCTAssertNotNil(publisher)
        wait(for: [expectation], timeout: 30)

    }
    
    func testDispatchRefreshAction() throws {
        let expectation = XCTestExpectation(description: "time_out")
        XCTAssertFalse(store.state.isRefreshing)
        store.dispatch(.refresh())
        XCTAssertTrue(store.state.isRefreshing)
        DispatchQueue.main.asyncAfter(deadline: .now() + 5, execute: {
            XCTAssertFalse(self.store.state.isRefreshing)
            XCTAssertTrue(!self.store.state.data.isEmpty)
            expectation.fulfill()
        })
        wait(for: [expectation], timeout: 30)
    }
    
    func testDispatchLoadMoreAction() throws {
        let expectation = XCTestExpectation(description: "time_out")
        XCTAssertFalse(store.state.isRefreshing)
        store.dispatch(.refresh())
        XCTAssertTrue(store.state.isRefreshing)
        DispatchQueue.main.asyncAfter(deadline: .now() + 5, execute: {
            
            let oldCount = self.store.state.data.count
            XCTAssertFalse(self.store.state.isLoadingMore)
            self.store.dispatch(.loadMore)
            XCTAssertTrue(self.store.state.isLoadingMore)
            DispatchQueue.main.asyncAfter(deadline: .now() + 5, execute: {
                XCTAssertFalse(self.store.state.isLoadingMore)
                let newCount = self.store.state.data.count
                XCTAssertTrue(oldCount > 0 && newCount == oldCount * 2)
                expectation.fulfill()
            })
        })
        wait(for: [expectation], timeout: 30)
    }
    
    func testDispatchClickFavorAction() throws {
        let expectation = XCTestExpectation(description: "time_out")
        store.dispatch(.refresh())
        DispatchQueue.main.asyncAfter(deadline: .now() + 5, execute: {
            let item = self.store.state.data[0]
            XCTAssertFalse(item.collected == true)
            self.store.dispatch(.clickFavor(favor: true, id: item.id))
            let item2 = self.store.state.data[0]
            XCTAssertTrue(item2.collected == true)
            
            self.store.dispatch(.refresh())
            DispatchQueue.main.asyncAfter(deadline: .now() + 5, execute: {
                let item1 = self.store.state.data[0]
                let item2 = self.store.state.data[1]
                XCTAssertTrue(item1.collected == true)
                XCTAssertTrue(item2.collected == false)
                
                expectation.fulfill()
            })
        })
        wait(for: [expectation], timeout: 30)
    }
    
}
