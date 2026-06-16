//
//  RedwoodTestProjectTests.swift
//  RedwoodTestProjectTests
//
//  Created by 徐栋 on 2023/2/7.
//

import XCTest
@testable import RedwoodTestProject

final class RedwoodTestProjectTests: XCTestCase {
    var adsModel: XXAdsModel!

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        let url = URL(filePath: XXAdsListViewModel.resourcePath ?? "")
        let data = try Data(contentsOf: url)
        let adsModel = try JSONDecoder().decode(XXAdsModel.self, from: data)
        self.adsModel = adsModel
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
        XCTAssert(self.adsModel.results.count > 0)
        
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
    func testViewModelLoad() throws {
        
    }

}

class RedwoodTestStaticTests: XCTestCase {
  func testTallestTowersShouldNotBeEmpty() {
      XCTAssert(URL(filePath: XXAdsListViewModel.resourcePath ?? "").isFileURL)
  }
}
