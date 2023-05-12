//
//  ViewModelTests.swift
//  DemoTests
//
//  Created by GNR on 11/1/22.
//

import XCTest
import Combine
@testable import Demo

final class ViewModelTests: XCTestCase {

    private var normalViewModel: AppsViewModel!
    private var errorViewModel: AppsViewModel!
    private var cancellables: Set<AnyCancellable> = []

    @MainActor override func setUpWithError() throws {
        normalViewModel = AppsViewModel()
        errorViewModel = AppsViewModel()
        errorViewModel.pageSize = 0
    }

    override func tearDownWithError() throws {
        normalViewModel = nil
        errorViewModel = nil
        cancellables.forEach { $0.cancel() }
    }

    @MainActor func testNormalRefreshing() async throws {
        let exp1 = XCTestExpectation(description: "Begin refreshing.")
        let exp2 = XCTestExpectation(description: "End refreshing.")
        let exp3 = XCTestExpectation(description: "Refresh normal.")

        normalViewModel.$isRefreshing
            .dropFirst(1)
            .first()
            .sink(receiveValue: { isRefreshing in
                XCTAssertEqual(isRefreshing, true)
                exp1.fulfill()
            })
            .store(in: &cancellables)
        
        normalViewModel.$isRefreshing
            .dropFirst(2)
            .sink(receiveValue: { isRefreshing in
                XCTAssertEqual(isRefreshing, false)
                exp2.fulfill()
            })
            .store(in: &cancellables)
        
        normalViewModel.$apps
            .sink(receiveValue: { _ in
                exp3.fulfill()
            })
            .store(in: &cancellables)
        
        await normalViewModel.loadNewApps()
        wait(for: [exp1, exp2, exp3], timeout: 2.0)
        XCTAssertLessThanOrEqual(normalViewModel.apps.count, normalViewModel.pageSize)
    }
    
    @MainActor func testErrorRefreshing() async throws {
        let exp1 = XCTestExpectation(description: "Begin refreshing.")
        let exp2 = XCTestExpectation(description: "End refreshing.")
        let exp3 = XCTestExpectation(description: "Refresh error.")

        errorViewModel.$isRefreshing
            .dropFirst(1)
            .first()
            .sink(receiveValue: { isRefreshing in
                XCTAssertEqual(isRefreshing, true)
                exp1.fulfill()
            })
            .store(in: &cancellables)
        
        errorViewModel.$isRefreshing
            .dropFirst(2)
            .sink(receiveValue: { isRefreshing in
                XCTAssertEqual(isRefreshing, false)
                exp2.fulfill()
            })
            .store(in: &cancellables)
        
        errorViewModel.$error
            .sink(receiveValue: { _ in
                exp3.fulfill()
            })
            .store(in: &cancellables)
        
        await errorViewModel.loadNewApps()
        wait(for: [exp1, exp2, exp3], timeout: 2.0)
        XCTAssert(errorViewModel.error != nil)
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
