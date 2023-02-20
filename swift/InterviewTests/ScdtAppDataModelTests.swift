//
//  ScdtAppDataModelTests.swift
//  InterviewTests
//
//  Created by 梁宇峰 on 2023/2/19.
//
@testable import Interview

import Foundation
import XCTest
import Combine

class MockedTestLoader: ScdtAppIntroductionsLoader {
    private let block: () throws ->Data
    
    init(block: @escaping () throws -> Data) {
        self.block = block
    }
    
    func loadIntroductions() throws -> Data {
        try block()
    }
}

class ScdtAppDataModelTests: XCTestCase {
    func testFirstLoadFailed() {
        let dataModel = ScdtAppDataModel(MockedTestLoader(block: {
            throw NSError(domain: "fist load failed", code: -1)
        }), DispatchQueue.global())
        while dataModel.isLoading {
            Thread.sleep(until: .now + 1)
        }
        
        XCTAssertTrue(dataModel.loadFailed)
        XCTAssertTrue(dataModel.appIntroductions.isEmpty)
    }
    
    func testNoContentLoad() {
        let dataModel = ScdtAppDataModel(MockedTestLoader(block: {
            let dict = ["resultCount" : 0]
            return try JSONSerialization.data(withJSONObject: dict, options: .prettyPrinted)
        }), DispatchQueue.global())
        while dataModel.isLoading {
            Thread.sleep(until: .now + 1)
        }
        
        XCTAssertFalse(dataModel.loadFailed)
        XCTAssertTrue(dataModel.appIntroductions.isEmpty)
    }
    
    func testLoading() {
        var loop = true
        let dataModel = ScdtAppDataModel(MockedTestLoader(block: {
            while loop {
                Thread.sleep(until: .now + 1)
            }
            return Data()
        }), DispatchQueue.global())
        
        XCTAssertFalse(dataModel.loadFailed)
        XCTAssertTrue(dataModel.appIntroductions.isEmpty)
        XCTAssertTrue(dataModel.isLoading)
        loop = false
    }
    
    func testLoadSuccess() {
        let dataModel = ScdtAppDataModel(MockedTestLoader(block: {
            jsonString.data(using: .utf8)!
        }), DispatchQueue.global())
        while dataModel.isLoading {
            Thread.sleep(until: .now + 1)
        }
        
        XCTAssertEqual(dataModel.appIntroductions.count, 2)
    }
    
    func testLoadFailedForSecondTime() {
        var time = 1
        let dataModel = ScdtAppDataModel(MockedTestLoader(block: {
            if time == 2 {
                throw NSError(domain: "fail for second time", code: -1)
            }
            time += 1
            return jsonString.data(using: .utf8)!
        }), DispatchQueue.global())
        while dataModel.isLoading {
            Thread.sleep(until: .now + 1)
        }
        XCTAssertEqual(dataModel.appIntroductions.count, 2)
        
        dataModel.loadMoreIntroductions()
        while dataModel.isLoading {
            Thread.sleep(until: .now + 1)
        }
        XCTAssertTrue(dataModel.loadFailed)
    }
    
    func testLoadingForSecondTime() {
        var time = 1
        var loop = true
        let dataModel = ScdtAppDataModel(MockedTestLoader(block: {
            if time == 2 {
                while loop {
                    Thread.sleep(until: .now + 1)
                }
            }
            time += 1
            return jsonString.data(using: .utf8)!
        }), DispatchQueue.global())
        while dataModel.isLoading {
            Thread.sleep(until: .now + 1)
        }
        XCTAssertEqual(dataModel.appIntroductions.count, 2)
        
        dataModel.loadMoreIntroductions()
        XCTAssertTrue(dataModel.isLoading)
        loop = false
    }
    
    func testCantLoadMore() {
        var time = 1
        let dataModel = ScdtAppDataModel(MockedTestLoader(block: {
            if time == 2 {
                return """
                        {
                            "resultCount": 0,
                            "results": []
                        }
                    """.data(using: .utf8)!
            }
            time += 1
            return jsonString.data(using: .utf8)!
        }), DispatchQueue.global())
        while dataModel.isLoading {
            Thread.sleep(until: .now + 1)
        }
        XCTAssertEqual(dataModel.appIntroductions.count, 2)
        
        dataModel.loadMoreIntroductions()
        while dataModel.isLoading {
            Thread.sleep(until: .now + 1)
        }
        XCTAssertTrue(dataModel.cantLoadMore)
    }
}
