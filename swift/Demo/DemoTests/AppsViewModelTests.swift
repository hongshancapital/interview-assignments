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
        sut = AppsViewModel(appService: MockAppService())
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testFetchApps() async throws {
        // Given
        let stateExpectation = XCTestExpectation(description: #function)
        sut.$viewState
            .dropFirst(2)
            .sink { value in
                stateExpectation.fulfill()
            }
            .store(in: &bag)
        
        // When
        await sut.refresh()
        
    
        // Then
        XCTAssertEqual(sut.viewState, .idle)
        XCTAssertEqual(sut.apps.count, 50)
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}

class MockAppService: MockProtocol, AppServiceProtocol {
    
    func getApps(pageIndex: Int, pageSize: Int) async -> Result<[AppModel], Error> {
        let result = loadJson(fileName: "apps", type: AppResponseModel.self)
        return .success(result.results)
    }
    
}
