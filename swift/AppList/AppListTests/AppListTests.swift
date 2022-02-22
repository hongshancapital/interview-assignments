//
//  AppListTests.swift
//  AppListTests
//
//  Created by jay on 2022/2/22.
//

import XCTest
@testable import AppList

class AppListTests: XCTestCase {
    
    var page1: Page!
    var page2: Page!
    var page3: Page!
    var dataLoader: DataLoader!
    var decoder: Decoder!

    func testPage() throws {
        XCTAssertEqual(page1.totalPage, 9)
        XCTAssertEqual(page3.totalPage, 0)

        XCTAssertEqual(page1.nextPage, 5)

        XCTAssertEqual(page1.range, 30..<40)
        XCTAssertEqual(page2.range, 80..<83)
        XCTAssertEqual(page3.range, nil)
        
        XCTAssertEqual(page1.rangeOfAll, 0..<83)
        XCTAssertEqual(page2.rangeOfAll, 0..<83)
        XCTAssertEqual(page3.rangeOfAll, nil)
        
        XCTAssertEqual(page1.page(of: 82), 9)
        XCTAssertEqual(page3.page(of: 82), nil)
        
        XCTAssertEqual(page1.range(for: 9), 80..<83)
        XCTAssertEqual(page3.range(for: 9), nil)

        XCTAssertEqual(page1.range(of: 33), 30..<40)
        XCTAssertEqual(page1.range(of: 82), 80..<83)
        XCTAssertEqual(page3.range(of: 82), nil)
    }
    
    
    /// getData(of:) 依赖于 DataLoader 中的其他方法，所以测这个就可以了
    func testDataLoader() throws {
        let page = Page(total: 50)
        let tuple = dataLoader.getData(of: page)
        let total = tuple.total
        let item = tuple.list.first
        XCTAssertEqual(total, 50)
        XCTAssertEqual(item?.title ?? "", "Google Chat")
    }
    
    override func setUpWithError() throws {
        page1 = Page(total: 83, page: 4, perPage: 10)
        page2 = Page(total: 83, page: 9, perPage: 10)
        page3 = Page(total: 0)
        
        dataLoader = DataLoader()
        
        decoder = Decoder()
    }

    override func tearDownWithError() throws {
        page1 = nil
        page2 = nil
        
        dataLoader = nil
        
        decoder = nil
    }
}
