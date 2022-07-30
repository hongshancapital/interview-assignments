//
//  RequestTests.swift
//  AssignmentTests
//
//  Created by shinolr on 2022/7/30.
//

import XCTest
@testable import Assignment

class RequestTests: XCTestCase {

  var sut: MockDataClient!

  override func setUpWithError() throws {
    sut = MockDataClient.shared
  }

  override func tearDownWithError() throws {
    sut = nil
  }

  func testFetchDataProperly() async {
    let result = await sut.fetchResult(with: MockRequest.initialLoad)
    let goods = try! result.get()
    XCTAssertNotNil(goods)
    XCTAssertEqual(goods.results.first!.id, 1163852619)
  }
}
