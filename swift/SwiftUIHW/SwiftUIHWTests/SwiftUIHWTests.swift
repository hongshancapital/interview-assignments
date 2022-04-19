//
//  SwiftUIHWTests.swift
//  SwiftUIHWTests
//
//  Created by 施治昂 on 4/14/22.
//

import XCTest
import Combine
@testable import SwiftUIHW

class SwiftUIHWTests: XCTestCase {
    var subscriptions = Set<AnyCancellable>()

    func testLoadData() async throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
        do {
            let loader1 = DataLoader(from: "2.txt")
            let result = try await loader1.requestData(page: 0)
            XCTAssertNil(result)
        }
        catch {
            XCTAssertNotNil(error)
        }
        let loader2 = DataLoader(from: "1.txt")
        let items2 = try await loader2.requestData(page: 0)
        XCTAssertNotNil(items2)
        
        let item3 = try await loader2.requestData(page: 20)
        XCTAssertNil(item3)
    }
    
    func testAppListViewModel() async throws {
        let mock = DataLoaderMock()
        let vm1 = AppListViewModel(onePageNum: 15, loader: mock)
        try await vm1.refresh()
        let _ = vm1.$apps
            .sink { item in
                XCTAssertEqual(item.count, 0)
            }
    }
    
    func testImageLoader() async throws {
        let session = URLSessionMock()
        let loader = ImageLoader(urlSession: session)
        let image = try await loader.fetchImage(from: "https://is2-ssl.mzstatic.com/image/thumb/PurpleSource115/v4/55/a0/a1/55a0a1ba-6f1d-a6b6-54a7-51a083b5d98f/92d383e6-75ab-43a8-9cc7-9c626d1d4055_iOS_5.5_03.png/392x696bb.png")
        XCTAssertNotNil(image)
    }
}
