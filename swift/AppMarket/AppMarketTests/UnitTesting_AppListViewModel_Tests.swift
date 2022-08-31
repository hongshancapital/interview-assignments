//
//  UnitTestingAppListViewModel_Tests.swift
//  AppMarketTests
//
//  Created by xcz on 2022/8/29.
//

import XCTest
@testable import AppMarket

class UnitTesting_AppListViewModel_Tests: XCTestCase {
    
    var dataService: AppListDataService!
    var vm: AppListViewModel!

    override func setUpWithError() throws {
        dataService = AppListDataService()
        vm = AppListViewModel()
    }

    override func tearDownWithError() throws {
        dataService = nil
        vm = nil
        TestDatasHelper.reductOldCollectedApps()
    }

    
    
    @MainActor
    func test_AppListViewModel_init() async throws {
        try await Task.sleep(nanoseconds: 3_000_000_000)
        
        XCTAssertFalse(vm.appInfos.isEmpty)
        XCTAssertFalse(vm.isLoding)
        XCTAssertFalse(vm.showNoMoreData)
    }
    
    
    @MainActor
    func test_AppListViewModel_fetchFirstPageData_shouldReturnedFirstPageDatas() async throws {

        await vm.fetchFirstPageData()
        try await Task.sleep(nanoseconds: 3_000_000_000)
        
        // 跟服务器返回数据对比是否正确
        let datas = await dataService.fetchAppInfos()
        
        XCTAssertTrue(vm.appInfos.count == datas.count)
        for index in 0...(datas.count - 1) {
            XCTAssertTrue(vm.appInfos[index].trackId == datas[index].trackId)
            XCTAssertTrue(vm.appInfos[index].isCollected == datas[index].isCollected)
        }
        
        // 检测线程，isLoading，showNoMoreData 是否正确
        XCTAssertTrue(Thread.isMainThread)
        XCTAssertFalse(vm.isLoding)
        XCTAssertTrue(vm.showNoMoreData == !(datas.count == dataService.pageCount))
        
    }
    

    @MainActor
    func test_AppListViewModel_fetchNextPageData_shouldReturnedNextPageDatas() async throws {
        
        await vm.fetchFirstPageData()
        try await Task.sleep(nanoseconds: 3_000_000_000)
        var datas = await dataService.fetchAppInfos()
    
        // 模拟不停下滑
        for page in 1...5 {
            await vm.fetchNextPageData()
            try await Task.sleep(nanoseconds: 3_000_000_000)
            let returnedData = await dataService.fetchAppInfos(page: page)

            datas.append(contentsOf: returnedData)
            
            // 跟服务器返回数据对比是否正确
            XCTAssertTrue(vm.appInfos.count == datas.count)
            for index in 0...(datas.count - 1) {
                XCTAssertTrue(vm.appInfos[index].trackId == datas[index].trackId)
                XCTAssertTrue(vm.appInfos[index].isCollected == datas[index].isCollected)
            }
            
            // 检测线程，isLoading，showNoMoreData 是否正确
            XCTAssertTrue(Thread.isMainThread)
            XCTAssertFalse(vm.isLoding)
            XCTAssertTrue(vm.showNoMoreData == !(returnedData.count == dataService.pageCount))
        }
        
    }
    
    
    func test_AppListViewModel_updateUserCollectedApps() async throws {
        
        
        await vm.fetchFirstPageData()
        try await Task.sleep(nanoseconds: 3_000_000_000)
        
        for _ in 0...5 {
            await vm.fetchNextPageData()
            try await Task.sleep(nanoseconds: 3_000_000_000)
        }
        
        for _ in 0...999 {
            // 随机取模型
            let index = Int.random(in: 0...(vm.appInfos.count - 1))
            var appInfo = vm.appInfos[index]
            
            
            appInfo.isCollected.toggle()
            vm.updateUserCollectedApps(appInfo: appInfo)
            
            // 检测appInfos 中的数据是否与UI保持一致
            let app = vm.appInfos.first(where: { $0.trackId == appInfo.trackId })
            XCTAssertTrue(app?.isCollected == appInfo.isCollected)
            
            // 检测本地保存是否更新
            let collectedApps = TestDatasHelper.fetchCollectedApps()
            XCTAssertTrue(appInfo.isCollected == collectedApps.contains(appInfo.trackId))
        }
        
    }
}
