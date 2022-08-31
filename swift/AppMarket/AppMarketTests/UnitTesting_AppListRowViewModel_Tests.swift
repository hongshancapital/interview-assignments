//
//  UnitTesting_AppListRowViewModel_Tests.swift
//  AppMarketTests
//
//  Created by xcz on 2022/8/29.
//

import XCTest
@testable import AppMarket

class UnitTesting_AppListRowViewModel_Tests: XCTestCase {


    @MainActor
    func test_AppListRowViewModel_fetchImage_shouldReturnedImage() async throws {
        
        let appInfo = UnitTesting_CollectedAppsHelper.simpleAppInfoModel()
        
        let vm = ApplistRowViewModel(appInfo: appInfo)
        await vm.fetchImage()
        
        XCTAssertTrue(Thread.isMainThread)
        XCTAssertFalse(vm.isLoading)
        XCTAssertNotNil(vm.image)
        
    }


}
