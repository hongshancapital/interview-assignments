//
//  AppStoreTests.swift
//  AppStoreTests
//
//  Created by huyanling on 2022/5/12.
//

@testable import AppStore
import XCTest

class AppStoreTests: XCTestCase {
    func testDataLoader() {
        let dataController = HSAppListDataController()
        dataController.mockJson = "appList.json"
        dataController.loadData(more: false)
        XCTAssertEqual(dataController.items.count, 50)
        dataController.loadData(more: true)
        XCTAssertEqual(dataController.items.count, 100)
    }

    func testImageLoder() async throws {
        ImageManager.shared.timeout = 60 * 60
        if let path = Bundle.main.path(forResource: "imageUrl.json", ofType: nil),
           let json = try? JSONSerialization.jsonObject(with: NSData(contentsOfFile: path) as Data) as? [String: Any],
           let urls = json["urls"] as? [String] {
            for (index, url) in urls.enumerated() {
                print("task ===================  \(index)")
                _ = try await ImageManager.shared.fetchImage(url: url)
            }
        }
    }

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
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
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        measure {
            // Put the code you want to measure the time of here.
        }
    }
}
