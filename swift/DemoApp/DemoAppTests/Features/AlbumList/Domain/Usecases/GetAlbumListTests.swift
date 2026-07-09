//
//  GetSoftwareListTests.swift
//  DemoAppTests
//
//  Created by 黄瑞 on 2023/2/15.
//

import XCTest
@testable import DemoApp

final class GetSoftwareListTests: XCTestCase {

    var usecase: GetSoftwareList!
    var repository: MockSoftwareListRepository!
    
    let page = 1;
    let expect = [Software.default]

    override func setUpWithError() throws {
        self.repository = MockSoftwareListRepository()
        self.usecase = GetSoftwareList(repository: self.repository)
    }

    override func tearDownWithError() throws {
        self.repository = nil
        self.usecase = nil
    }

    func testGetSoftwareListFromTheRepository() async throws {
        self.repository!.getSoftwareResult = .success(expect)
        let result = await self.usecase.call(params: GetSoftwareListParam(count: page))
        switch result {
        case .success(let response):
            XCTAssertEqual(response, expect)
        case .failure:
            XCTAssert(false, "")
        }
        XCTAssertTrue(self.repository.isGetSoftwareCalled)
    }
}
