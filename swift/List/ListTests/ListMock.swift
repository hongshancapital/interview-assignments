//
//  ListMock.swift
//  ListTests
//
//  Created by 王明友 on 2022/7/17.
//

import XCTest
@testable import List

class ListMock: XCTestCase {
    var sut: LoadData!
    override func setUpWithError() throws {
        try super.setUpWithError()
        sut = LoadData()
    }

    override func tearDownWithError() throws {
        sut = nil
        try super.tearDownWithError()
    }
    
    func testLoadDataCompletes() throws {
        // given
        let promise = expectation(description: "Completion handler invoked")
        var statusCode: Int?
        var responseError: NSError?
        sut.loadLocalData(count: 20) { result in
            switch result {
            case .success(_):
                statusCode = 200
                promise.fulfill()
            case .failure(let error):
                statusCode = error.code
                responseError = error
            }
            
        }
        wait(for: [promise], timeout: 5)
        
        // then
        XCTAssertNil(responseError)
        XCTAssertEqual(statusCode, 200)
    }

}
