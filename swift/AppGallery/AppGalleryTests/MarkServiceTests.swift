//
//  MarkServiceTests.swift
//  AppGalleryTests
//
//  Created by X Tommy on 2022/8/11.
//

import XCTest
@testable import AppGallery

class MarkServiceTests: XCTestCase {

    private let markService = LocalMarkService.shared
    
    func testUpdateMarked() throws {
        let trackId = 1
        
        markService.update(by: trackId, marked: false)
        let isMarked0 = markService.isMarked(for: trackId)
        XCTAssertFalse(isMarked0)
        
        markService.update(by: trackId, marked: true)
        let isMarked1 = markService.isMarked(for: trackId)
        XCTAssertTrue(isMarked1)
        
        markService.update(by: trackId, marked: false)
        let isMarked2 = markService.isMarked(for: trackId)
        XCTAssertFalse(isMarked2)
    }

}
