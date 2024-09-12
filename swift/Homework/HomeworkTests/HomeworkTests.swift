//
// Homework
// HomeworkTests.swift
//
// Created by wuyikai on 2022/4/27.
// 
// 

import XCTest
@testable import Homework

class HomeworkTests: XCTestCase {
    
    let appStore = AppStore()
    let favoriteAppManager = FavoriteAppManager()

    override func setUpWithError() throws {
    }

    override func tearDownWithError() throws {
    }

    func testAppStore() async throws {
        var apps = await appStore.apps
        XCTAssertTrue(apps.isEmpty)
        
        try await appStore.reload()
        apps = await appStore.apps
        XCTAssertFalse(apps.isEmpty)
        XCTAssert(apps.count == 10)
        
        try await appStore.loadNextPage()
        apps = await appStore.apps
        XCTAssert(apps.count == 20)
        
        let nomoreData = await appStore.nomoreData
        XCTAssertFalse(nomoreData)
        
        await updateNomoreData(true)
        try await appStore.loadNextPage()
        apps = await appStore.apps
        XCTAssert(apps.count == 20)
    }
    
    func testFavoriteManager() async throws {
        XCTAssertTrue(favoriteAppManager.favoriteAppIds.isEmpty)
        
        let sample = AppInfo.sampleData
        favoriteAppManager.toggleFavorite(appID: sample[0].id)
        
        XCTAssertFalse(favoriteAppManager.favoriteAppIds.isEmpty)
        XCTAssert(favoriteAppManager.favoriteAppIds.count == 1)
        XCTAssertEqual(favoriteAppManager.favoriteAppIds.first!, sample[0].id)
        
        favoriteAppManager.toggleFavorite(appID: sample[0].id)
        XCTAssertTrue(favoriteAppManager.favoriteAppIds.isEmpty)
        
        favoriteAppManager.toggleFavorite(appID: sample[0].id)
        favoriteAppManager.toggleFavorite(appID: sample[1].id)
        XCTAssert(favoriteAppManager.favoriteAppIds.count == 2)
    }
    
    func testImageCache() async throws {
        let imageCache = AutoPurgingImageCache()
        XCTAssertTrue(imageCache.cachedImages.isEmpty)
        
        let testURL = "https://www.baidu.com/images/test.png"
        
        imageCache.addImageData(Data(), with: testURL)
        let addExpection = expectation(description: "添加缓存")
        DispatchQueue.main.asyncAfter(deadline: .now() + 3.0, execute: {
            addExpection.fulfill()
        })
        wait(for: [addExpection], timeout: 5)
        XCTAssertEqual(imageCache.cachedImages.count, 1)
        XCTAssertNotNil(imageCache.imageData(with: testURL))
        
        imageCache.removeImageData(with: testURL)
        let removeExpection = expectation(description: "移除缓存")
        DispatchQueue.main.asyncAfter(deadline: .now() + 3.0, execute: {
            removeExpection.fulfill()
        })
        wait(for: [removeExpection], timeout: 5)
        XCTAssertNil(imageCache.imageData(with:testURL))
    }

    @MainActor func updateNomoreData(_ nomoreData: Bool) {
        appStore.nomoreData = nomoreData
    }

}
