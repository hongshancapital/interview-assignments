//
//  UnitTesting_AppListRowDataService_Tests.swift
//  AppMarketTests
//
//  Created by xcz on 2022/8/29.
//

import XCTest
@testable import AppMarket

class UnitTesting_AppListRowDataService_Tests: XCTestCase {

    func test_AppListRowDataService_downloadImage_shouldUpdateCollectedApps() async throws {
        let dataService = ApplistRowDataService()
        
        let image = try await dataService.downloadImage(url: URL(string: TestDatasHelper.simpleAppInfoModel().artworkUrl100)!)
        
        XCTAssertNotNil(image)
        XCTAssertFalse(Thread.isMainThread)
        
    }
    
}
