//
//  NetworkMonitorTests.swift
//  SwiftUIAssignmentsTests
//
//  Created by zcj on 2023/6/7.
//

import XCTest
@testable import SwiftUIAssignments
import Combine

final class NetworkMonitorTests: XCTestCase {

    private var cancelable = Set<AnyCancellable>()
    // MARK: - system
    override func setUpWithError() throws {
    }

    override func tearDownWithError() throws {
        cancelable.forEach { $0.cancel() }
    }

    func testSingletonInstance() {
        // Given, When
        let monitor1 = NetworkMonitor.shared
        let monitor2 = NetworkMonitor.shared

        // Then
        XCTAssertIdentical(monitor1, monitor2)
    }

    func testStatusIsReachableOnWiFiOrCellular() {
        // Given, When
        let monitor = NetworkMonitor.shared

        // Then
        XCTAssertEqual(monitor.status, .connected)
    }

    func testStatusIsNotReachable() {
        // Given, When
        let monitor = NetworkMonitor.shared

        // Then
        XCTAssertEqual(monitor.status, .unConnected)
    }


    func testPublisherIsReachableWiFiOrCellular() {
        // Given
        let monitor = NetworkMonitor.shared
        var result: NetworkMonitor.Status?
        let expect = expectation(description: "listener closure should be executed")

        // When
        monitor.publisher.sink { status in
            result = status
            expect.fulfill()
        }.store(in: &cancelable)

        // Then
        wait(for: [expect], timeout: 10)
        XCTAssertEqual(result, .connected)
    }

    func testPublisherIsNotReachable() {
        // Given
        let monitor = NetworkMonitor.shared
        var result: NetworkMonitor.Status?
        let expect = expectation(description: "listener closure should be executed")

        // When
        monitor.publisher.sink { status in
            result = status
            expect.fulfill()
        }.store(in: &cancelable)

        // Then
        wait(for: [expect], timeout: 10)
        XCTAssertEqual(result, .unConnected)
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
