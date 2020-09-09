//
//  SearchViewDemoTests.swift
//  SearchViewDemoTests
//
//  Created by Mason Sun on 2020/9/9.
//

import XCTest
@testable import SearchViewDemo

class SearchViewDemoTests: XCTestCase {
    var sut: SearchViewModel!

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        sut = SearchViewModel()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testSearch() {
        sut.text = "Dyson"
        let searchDyson = XCTestExpectation(description: "Search text: `Dyson`")
        sut.search { _ in
            XCTAssertEqual(self.sut.result.count, 2)
            XCTAssertEqual(self.sut.isEmptyResult, false)
            searchDyson.fulfill()
        }
        wait(for: [ searchDyson ], timeout: 10)

        let searchDysonx = XCTestExpectation(description: "Search text: `Dysonx`")
        sut.text = "Dysonx"
        sut.search { _ in
            XCTAssertEqual(self.sut.result.count, 0)
            XCTAssertEqual(self.sut.isEmptyResult, true)
            searchDysonx.fulfill()
        }
        wait(for: [ searchDysonx ], timeout: 10)
    }

    func testCancel() {
        sut.text = "Dysonx"
        sut.search()
        XCTAssertEqual(sut.isSearching, true)
        XCTAssertEqual(sut.text, "Dysonx")

        sut.cancel()
        XCTAssertEqual(sut.isSearching, false)
        XCTAssertEqual(sut.text, "")
    }
}
