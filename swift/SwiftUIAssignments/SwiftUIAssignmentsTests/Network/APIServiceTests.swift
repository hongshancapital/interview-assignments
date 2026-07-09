//
//  APIServiceTests.swift
//  SwiftUIAssignmentsTests
//
//  Created by zcj on 2023/6/7.
//

import XCTest
@testable import SwiftUIAssignments

final class APIServiceTests: XCTestCase {

    override func setUpWithError() throws {
    }

    override func tearDownWithError() throws {
    }

    func testMockData() async {
        // Given
        let sevice = APIService.shared

        // When
        let result: MockData? = await sevice.mockData()

        // Then
        XCTAssertNotNil(result)
        XCTAssertEqual(result?.total, 50)
        XCTAssertEqual(result?.appModels.isEmpty, false)
    }

    // MARK: - mockRequest
    func testMockRequestPageError() async {
        // Given
        let endpoint = APIService.Endpoint.appList(page: -1, count: 20)
        let sevice = APIService.shared

        // When
        let result = await sevice.mockRequest(endpoint)

        // Then
        XCTAssertNil(result.success)
        XCTAssertEqual(result.failure, .parameterError)
    }

    func testMockRequestCountError() async {
        // Given
        let endpoint = APIService.Endpoint.appList(page: 1, count: -20)
        let sevice = APIService.shared

        // When
        let result = await sevice.mockRequest(endpoint)

        // Then
        XCTAssertNil(result.success)
        XCTAssertEqual(result.failure, .parameterError)
    }

    func testMockRequestCountOverLength() async {
        // Given
        let endpoint = APIService.Endpoint.appList(page: 1, count: 60)
        let sevice = APIService.shared

        // When
        let result = await sevice.mockRequest(endpoint)

        // Then
        XCTAssertNil(result.failure)
        XCTAssertEqual(result.success?.currentPage, 1)
        XCTAssertEqual(result.success?.total, 50)
        XCTAssertEqual(result.success?.appModels.count, 50)
    }

    func testMockRequest() async {
        // Given
        let service = APIService.shared
        let request1 = APIService.Endpoint.appList(page: 1, count: 20)
        let request2 = APIService.Endpoint.appList(page: 2, count: 20)
        let request3 = APIService.Endpoint.appList(page: 3, count: 20)

        // When
        async let result1 = service.mockRequest(request1)
        async let result2 = service.mockRequest(request2)
        async let result3 = service.mockRequest(request3)

        // Then
        let results = await [result1, result2, result3]
        XCTAssertEqual(results.count, 3)
        XCTAssertTrue(results.allSatisfy(\.isSuccess))

        XCTAssertEqual(results[0].success?.currentPage, 1)
        XCTAssertEqual(results[0].success?.appModels.count, 20)
        XCTAssertEqual(results[1].success?.currentPage, 2)
        XCTAssertEqual(results[1].success?.appModels.count, 20)
        XCTAssertEqual(results[2].success?.currentPage, 3)
        XCTAssertEqual(results[2].success?.appModels.count, 10)
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
}

