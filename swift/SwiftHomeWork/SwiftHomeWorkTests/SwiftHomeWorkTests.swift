//
//  SwiftHomeWorkTests.swift
//  SwiftHomeWorkTests
//
//  Created by apple on 2022/4/12.
//

import XCTest
@testable import SwiftHomeWork

class SwiftHomeWorkTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testNetworkAPI() throws {
        let e = self.expectation(description: "api")
        Task{
            do {
                let welcome = try await NetService.init().getData()
                print(welcome)
            } catch {
                print(error)
                XCTFail()
            }
            e.fulfill()
        }
        self.wait(for: [e], timeout: 10)
    }
    
    func testMock() throws{
        do {
            try mockResult()
        } catch {
            print(error)
            XCTFail()
        }
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
