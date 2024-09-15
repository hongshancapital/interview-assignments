//
//  FileUtilsTests.swift
//  AppListTests
//
//  Created by 钟逊超 on 2022/8/2.
//

import XCTest
@testable import AppList

class FileUtilsTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        let appResultModel: AppResultModel? = try FileUtils.loadJson(fileName: "Apps")
        XCTAssertNotNil(appResultModel)
        XCTAssertEqual(appResultModel?.resultCount, 50)
        XCTAssertEqual(appResultModel?.results.count, 50)
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
