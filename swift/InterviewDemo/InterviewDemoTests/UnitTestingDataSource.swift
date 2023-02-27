//
//  UnitTestingDataSource.swift
//  InterviewDemoTests
//
//  Created by Peng Shu on 2023/2/27.
//

import XCTest
@testable import InterviewDemo
import Combine

final class UnitTestingDataSource: XCTestCase {
    var cancellables = Set<AnyCancellable>()
    
    override func setUpWithError() throws {
        
    }

    override func tearDownWithError() throws {

    }

    func test_UnitTestingDataSource_loadNextUsingConcurrency_shouldNotEmpty() async throws {
        // Given
        let dataSource = DataSource()
        
        // When
        await dataSource.loadNextUsingConcurrency(currentItem: nil)

        // Then
        XCTAssertGreaterThan(dataSource.items.count, 0)
    }
    
    func test_UnitTestingDataSource_loadNextUsingCombine_shoudNotEmpty() throws {
        // Given
        let dataSource = DataSource()
        
        // When
        let expectation = XCTestExpectation(description: "Should fetch items after 5 seconds.")
        
        dataSource.$items
            .dropFirst()
            .sink { items in
                expectation.fulfill()
            }.store(in: &cancellables)
        
        dataSource.loadNextUsingCombine(currentItem: nil)

        // Then
        wait(for: [expectation], timeout: 5)
        XCTAssertGreaterThan(dataSource.items.count, 0)
    }
    
    func test_UnitTestingDataSource_reset_shoudEmpty() async throws {
        // Given
        let dataSource = DataSource()
        
        // When
        await dataSource.loadNextUsingConcurrency(currentItem: nil)
        dataSource.reset()

        // Then
        XCTAssertTrue(dataSource.items.isEmpty)
    }
}
