//
//  UnitTesting_AppListRowViewModel_Tests.swift
//  AppMarketTests
//
//  Created by xcz on 2022/8/29.
//

import XCTest
@testable import AppMarket

class UnitTesting_CachedAsyncImageViewModel_Tests: XCTestCase {

    @MainActor
    func test_AppListRowViewModel_init() async throws {

        let vm = CachedAsyncImageViewModel(url: URL(string: TestDatasHelper.simpleAppInfoModel().artworkUrl100)!)
        
        try await Task.sleep(nanoseconds: 3_000_000_000)
        XCTAssertTrue(Thread.isMainThread)
        XCTAssertFalse(vm.isLoading)
        XCTAssertNotNil(vm.image)
        
        
    }
    

    @MainActor
    func test_AppListRowViewModel_fetchImage_shouldReturnedImage() async throws {
        
        let vm = CachedAsyncImageViewModel(url: URL(string: TestDatasHelper.simpleAppInfoModel().artworkUrl100)!)
        try await Task.sleep(nanoseconds: 3_000_000_000)
        
        vm.image = nil
        await vm.fetchImage()
        
        try await Task.sleep(nanoseconds: 3_000_000_000)
        XCTAssertTrue(Thread.isMainThread)
        XCTAssertFalse(vm.isLoading)
        XCTAssertNotNil(vm.image)
        
    }
    


}
