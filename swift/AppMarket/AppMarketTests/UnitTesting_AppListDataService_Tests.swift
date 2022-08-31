//
//  UnitTesting_AppListDataService_Tests.swift
//  AppMarketTests
//
//  Created by xcz on 2022/8/29.
//

import XCTest
@testable import AppMarket

class UnitTesting_AppListDataService_Tests: XCTestCase {
    
    var dataService: AppListDataService!
    var allAppInfos: [AppInfo]!
    var oldCollectedApps: [Double]!
    
    
    override func setUpWithError() throws {
        dataService = AppListDataService()
        allAppInfos =  UnitTesting_CollectedAppsHelper.loadMockData()
    }

    override func tearDownWithError() throws {
        dataService = nil
        allAppInfos = nil
        UnitTesting_CollectedAppsHelper.reductOldCollectedApps()
    }

    
    // 检测模拟获取数据的正确性
    func test_AppListDataService_fetchAppInfos_shouldReturnedRightDatas() async throws {
        
        // 多次循环测试以覆盖随机情况
        for _ in 0...999 {
        
            let collected = UnitTesting_CollectedAppsHelper.fetchCollectedApps()
            // 随机生成页数和每页数据条数
            let page: Int = Int.random(in: -9...99)
            let pageCount: Int = Int.random(in: -9...99)
            dataService.pageCount = pageCount

            let datas = await dataService.fetchAppInfos(page: page)
            
            if pageCount <= 0 || page < 0 {
                XCTAssertTrue(datas.isEmpty)
            } else if page * pageCount > allAppInfos.count - 1 {
                XCTAssertTrue(datas.isEmpty)
            } else {
                // 检测返回不为空的情况下数据的正确性
                if pageCount == 1 {
                    // 只有一条数据的情况
                    XCTAssertTrue(datas.count == pageCount)
                    XCTAssertTrue(datas[0].trackId == allAppInfos[page].trackId)
                    XCTAssertTrue(datas[0].isCollected == collected.contains(datas[0].trackId))
                } else {
                    let starIndex = page * pageCount
                    let endIndex = (page + 1) * pageCount - 1
                    if endIndex < allAppInfos.count {
                        // 整页取满的情况
                        XCTAssertTrue(datas.count == pageCount)
                        var dataIndex = 0
                        for allAppInfosIndex in starIndex...endIndex {
                            XCTAssertTrue(datas[dataIndex].trackId == allAppInfos[allAppInfosIndex].trackId)
                            XCTAssertTrue(datas[dataIndex].isCollected == collected.contains(datas[dataIndex].trackId))
                            dataIndex += 1
                        }
                        
                    } else {
                        // 非整页取满的情况
                        XCTAssertTrue(datas.count == pageCount - (endIndex - allAppInfos.count + 1))
                        var dataIndex = 0
                        for allAppInfosIndex in starIndex...(allAppInfos.count - 1) {
                            XCTAssertTrue(datas[dataIndex].trackId == allAppInfos[allAppInfosIndex].trackId)
                            XCTAssertTrue(datas[dataIndex].isCollected == collected.contains(datas[dataIndex].trackId))
                            dataIndex += 1
                        }
                    }
                }
            
            }
        }
    }

    // 检测收藏或取消的app的id是否正确保存在本地
    func test_AppListDataService_updateCollectedApps_shouldUpdateCollectedApps() throws {
       
        // 多次循环测试以覆盖随机情况
        for _ in 0...999 {
            
            // 随机取一个模型
            let oldCollected = UnitTesting_CollectedAppsHelper.fetchCollectedApps()
            let appInfo = allAppInfos[Int.random(in: 0...allAppInfos.count - 1)]
            let appInfoModel = AppInfoModel(
                trackId: appInfo.trackId,
                trackName: appInfo.trackName,
                artworkUrl100: appInfo.artworkUrl100,
                description: appInfo.description ?? "",
                isCollected: !oldCollected.contains(appInfo.trackId)
            )
            
            dataService.updateCollectedApps(appInfo: appInfoModel)
            
            let newCollected = UnitTesting_CollectedAppsHelper.fetchCollectedApps()
            XCTAssertTrue(appInfoModel.isCollected == newCollected.contains(appInfoModel.trackId))
            
        }
        
    }

}


