//
//  HSImageCacheTests.swift
//  HSAppStoreTests
//
//  Created by Sheng ma on 2022/5/16.
//

import XCTest
@testable import HSAppStore

class HSImageCacheTests: XCTestCase {

    let imageCache = HSImageCache()
    let urlString = "https://is5-ssl.mzstatic.com/image/thumb/PurpleSource126/v4/d0/52/b8/d052b8a2-0417-7017-3b2f-9f9fbb686138/65ff0370-f3d4-48c1-b8c1-5430ff91ee98_Intro.png/392x696bb.png"
    let wrongUrl = "https://is5-ssl.mzstatic.com/image/thumb/PurpleSource126/v4/d0/52/b8/d052b8a2-0417-7017-3b2f-9f9fbb686138/392x696bb.png"
    
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

    func testImageForInvailUrl() async throws {
        // invail url
        HSImageCache.shared.imageForUrl(urlString: "urlTest", completionHandler: { (data) -> () in
            XCTAssertNil(data)
        })
    }
    
    func testImageForWrongUrl() async throws {
        // wrong url
        HSImageCache.shared.imageForUrl(urlString: wrongUrl, completionHandler: { (data) -> () in
            XCTAssertNil(data)
        })
    }
    
    func testImageForUrl() async throws {
        HSImageCache.shared.imageForUrl(urlString: urlString, completionHandler: { (data) -> () in
            XCTAssertNil(data)
        })
    }
    
    func testImageForUrlInMomery() async throws {
        // Test imageData In Momery
        let data: Data? = HSImageCache.shared.cache.object(forKey: urlString as AnyObject) as? Data
        if data == nil {
            XCTFail("Failed to get picture from memory")
        }
    }
    
    func testImageForUrlInDisck() async throws {
        // Test imageData In Momery
        let disckCache = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).map(\.path).last
        let fileName = urlString.replacingOccurrences(of:"/", with: "")
        let fullPath = URL(fileURLWithPath: disckCache ?? "").appendingPathComponent(fileName).path
        let data2: Data? = NSData(contentsOfFile: fullPath) as Data?
        if data2 == nil {
            XCTFail("Failed to get picture from disck")
        }
    }
    
    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
}
