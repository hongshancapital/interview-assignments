//
//  DemoAppTests.swift
//  DemoAppTests
//
//  Created by liang on 2022/5/18.
//

import XCTest
@testable import DemoApp

class DemoAppTests: XCTestCase {
    let dataMgr: DataManagerV2 = DataManagerV2()

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }
    
    func testAppModelDecoding() throws {
        let dataFile = "data.json"
        guard let url = Bundle.main.url(forResource: dataFile, withExtension: nil)
        else {
            XCTFail("未找到数据文件\(dataFile)")
            return
        }
        guard let data = try? Data.init(contentsOf: url)
        else {
            XCTFail("未找加载数据文件\(dataFile)")
            return
        }
        
        let apiRsp = try? JSONDecoder().decode(ApiResponse.self, from: data)
        if let list = apiRsp?.results {
            XCTAssert(list.count == 50, "解析appModel对象失败")
        } else {
            XCTFail("解析数据文件失败\(dataFile)")
        }

    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
