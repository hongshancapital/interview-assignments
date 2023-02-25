//
//  InterviewDemoTests.swift
//  InterviewDemoTests
//
//  Created by Peng Shu on 2023/2/24.
//

import XCTest
@testable import InterviewDemo

class InterviewDemoTests: XCTestCase {
    var dataSource: DataSource!
    
    
    override func setUpWithError() throws {
        dataSource = DataSource()
    }

    override func tearDownWithError() throws {
        
    }

    func testLaodingDataUsingConcurrency() async throws {
        await dataSource.loadNextUsingConcurrency(currentItem: nil)
        XCTAssert(dataSource.items.count > 0)
    }
}
