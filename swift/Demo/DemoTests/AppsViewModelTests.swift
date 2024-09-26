//
//  DemoTests.swift
//  DemoTests
//
//  Created by Xiaoping Tang on 2023/4/9.
//

import XCTest
import Combine
@testable import Demo


final class DemoTests: XCTestCase {
    
    var sut: AppsViewModel!
    var bag: Set<AnyCancellable> = Set<AnyCancellable>()

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        sut = AppsViewModel(appService: MockAppService(), coreDataStack: CoreDataStack(modelName: "Model"))
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testRefreshApps() async throws {
        // Given
        let stateExpectation = XCTestExpectation(description: #function)
//        sut.viewState = .idle
        sut.$viewState
            .dropFirst(2)  // init and start refresh
            .sink { value in
                print("value=\(value)")
                stateExpectation.fulfill()
            }
            .store(in: &bag)
        
        // When
        await sut.refresh()
        
    
        // Then
        wait(for: [stateExpectation], timeout: 20)
        XCTAssertEqual(sut.viewState, .idle)
        XCTAssertEqual(sut.pageIndex, 0)
        XCTAssertEqual(sut.apps.count, 50)
        XCTAssertEqual(sut.pageIndex, 0)
    }
    
    func testRefreshAppsWithCombine() throws {
        // Given
        let stateExpectation = XCTestExpectation(description: #function)
        sut.viewState = .idle
        sut.$viewState
            .dropFirst(2)  // init and start refresh
            .sink { value in
                stateExpectation.fulfill()
            }
            .store(in: &bag)
        
        // When
        sut.doRefresh()
        
    
        // Then
        wait(for: [stateExpectation], timeout: 20)
        XCTAssertEqual(sut.viewState, .idle)
        XCTAssertEqual(sut.apps.count, 50)
        XCTAssertEqual(sut.pageIndex, 0)
    }
    
    func testLoadMore() throws {
        // Given
        let stateExpectation = XCTestExpectation(description: #function)
        sut.viewState = .idle
        sut.$viewState
            .dropFirst(2)  // init and start refresh
            .sink { value in
                stateExpectation.fulfill()
            }
            .store(in: &bag)
        
        // When
        sut.loadMore()
        
    
        // Then
        wait(for: [stateExpectation], timeout: 20)
        XCTAssertEqual(sut.viewState, .idle)
        XCTAssertEqual(sut.apps.count, 50)
        XCTAssertEqual(sut.pageIndex, 1)
    }
    
    func testFavorite() throws {
        // Given
        let stateExpectation = XCTestExpectation(description: #function)
        sut.viewState = .idle
        sut.$viewState
            .dropFirst(2)  // init and start refresh
            .sink { value in
                stateExpectation.fulfill()
            }
            .store(in: &bag)
        sut.doRefresh()
        wait(for: [stateExpectation], timeout: 20)
        
        // When
        guard let firstApp = sut.apps.first else {
           return
        }
        let oldValue = firstApp.favorite
        sut.favoriteAppToggle(app: firstApp)
        
        // Then
        XCTAssertEqual(!oldValue, firstApp.favorite)
    }
    
    func testCheckFavoriteed() throws {
        // Given
        let stateExpectation = XCTestExpectation(description: #function)
        sut.viewState = .idle
        sut.$viewState
            .dropFirst(2)  // init and start refresh
            .sink { value in
                stateExpectation.fulfill()
            }
            .store(in: &bag)
        sut.doRefresh()
        wait(for: [stateExpectation], timeout: 20)
        
        guard let firstApp = sut.apps.first else {
           return
        }
        
        if firstApp.favorite == false {
            sut.favoriteAppToggle(app: firstApp)
        }
        
        // When
        sut.checkFavorate(items: sut.apps)
        
        // Then
        XCTAssertTrue(firstApp.favorite)
        
        // When
        sut.favoriteAppToggle(app: firstApp)
        
        // Then
        XCTAssertFalse(firstApp.favorite)
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}

class MockAppService: MockProtocol, AppServiceProtocol {
    func requestApps(pageIndex: Int, pageSize: Int) -> AnyPublisher<[Demo.AppModel], Error> {
        let result = loadJson(fileName: "apps", type: AppResponseModel.self)
        return Just(result.results)
            .setFailureType(to: Error.self)
            .eraseToAnyPublisher()
    }
    
    
    func getApps(pageIndex: Int, pageSize: Int) async -> Result<[AppModel], Error> {
        let result = loadJson(fileName: "apps", type: AppResponseModel.self)
        return .success(result.results)
    }
    
}
