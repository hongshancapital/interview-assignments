//
//  AppListServiceTests.swift
//  AppGalleryTests
//
//  Created by X Tommy on 2022/8/11.
//

import XCTest
@testable import AppGallery

class AppListServiceTests: XCTestCase {

    private let appListService = RemoteAppListService()
    
    // limit should be 1-200 https://developer.apple.com/library/archive/documentation/AudioVideo/Conceptual/iTuneSearchAPI/Searching.html#//apple_ref/doc/uid/TP40017632-CH5-SW1
    
    func testFetchTracksByLimit() async throws {
        
        let min = 1
        let max = 200
        
        async let fetchTracks0 = appListService.fetchApps(limit: 300)
        async let fetchTracks1 = appListService.fetchApps(limit: -300)
        
        do {
            let results0 = try await fetchTracks0
            let results1 = try await fetchTracks1
            
            XCTAssertLessThanOrEqual(results0.count, max)
            XCTAssertGreaterThanOrEqual(results0.count, min)
            
            XCTAssertLessThanOrEqual(results1.count, max)
            XCTAssertGreaterThanOrEqual(results1.count, min)
            
            XCTAssertGreaterThanOrEqual(results0.count, results1.count)
        } catch {
            XCTFail(error.localizedDescription)
        }
        
    }

}
