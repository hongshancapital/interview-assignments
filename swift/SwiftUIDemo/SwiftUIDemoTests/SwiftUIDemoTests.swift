//
//  SwiftUIDemoTests.swift
//  SwiftUIDemoTests
//
//  Created by Machao on 2022/4/15.
//

import XCTest
@testable import SwiftUIDemo

class SwiftUIDemoTests: XCTestCase {
    
    var jsonString: String!
    var decoder = JSONDecoder()
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        let url = Bundle.main.url(forResource: "Links", withExtension: ".json")
        if let url = url {
            jsonString = try String(contentsOf: url)
        }
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        
    }

    func testDecodeLinkModel() throws {
        let data = jsonString.data(using: .utf8)
        XCTAssertNotNil(data)
        let result = try decoder.decode(ResultModel<[LinkModel]>.self, from: data!)
        XCTAssert(result.resultCount == 50, "测试数据读取失败")
        XCTAssert(result.results.count == 50, "测试数据读取失败")
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
