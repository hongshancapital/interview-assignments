//
//  SCDTTests.swift
//  SCDTTests
//
//  Created by Zhao Sam on 2022/8/5.
//

import XCTest
@testable import SCDT

class SCDTTests: XCTestCase {
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testfetchApplicationDataWithInitialPagination_ReturnAppList() async throws {
        let pagination = Pagination(size: 10)
        let apps = await APIManager.fetchApplicationData(pagination: pagination)
        XCTAssertTrue(apps != nil && !apps!.isEmpty)
    }
    
    func testfetchApplicationDataWithZeroSizePagination_ReturnEmptyAppList() async throws {
        let pagination = Pagination(size: 0)
        let apps = await APIManager.fetchApplicationData(pagination: pagination)
        XCTAssertTrue(apps != nil && apps!.isEmpty)
    }
    
    func testfetchApplicationDataWithInvalidPagination_ReturnEmptyAppList() async throws {
        let pagination = Pagination(size: -1)
        let apps = await APIManager.fetchApplicationData(pagination: pagination)
        XCTAssertTrue(apps != nil && apps!.isEmpty)
    }
    
    func testPaginationNextPageWithHasMoreTrue_ReturnOffset10() throws {
        let pagination = Pagination(size: 10)
        pagination.hasMore = true
        pagination.nextPage()
        XCTAssertEqual(pagination.offset, 10)
    }
    
    func testPaginationNextPageWithHasMoreFalse_ReturnOffset0() throws {
        let pagination = Pagination(size: 10)
        pagination.hasMore = false
        pagination.nextPage()
        XCTAssertEqual(pagination.offset, 0)
    }
    
    func testPaginationReset_ReturnOffset0() throws {
        let pagination = Pagination(size: 10)
        pagination.hasMore = true
        pagination.nextPage()
        pagination.hasMore = false
        pagination.reset()
        XCTAssertTrue(pagination.offset == 0 && pagination.hasMore)
    }
    
    func testApplicationInitFromValidModel_ReturnApplication() throws {
        let model = ApplicationModel(trackId: 1, trackCensoredName: "name", description: "desp", artworkUrl60: "url")
        let application = Application(from: model)
        XCTAssertTrue(application.id == 1 && application.name == "name" && application.description == "desp" && application.iconUrl == "url")
    }
    
    func testApplicationInit_ReturnApplication() throws {
        let application = Application(id: 1, name: "name", iconUrl: "url", description: "desp")
        XCTAssertEqual(application.id, 1)
        XCTAssertEqual(application.name, "name")
        XCTAssertEqual(application.description, "desp")
        XCTAssertEqual(application.iconUrl, "url")
    }
    
    func testApplicationFavoriteWithoutPresetUserDefault_ReturnApplication() throws {
        let application = Application(id: 1, name: "name", iconUrl: "desp", description: "url")
        XCTAssertFalse(application.isFavorite)
    }
    
    func testApplicationFavoriteWithPresetUserDefault_ReturnApplication() throws {
        var dict = UserDefaults.standard.dictionary(forKey: APPLICATIONKEY) ?? [:] as [String:Bool]
        let formatKey = String(format: APPLICATIONKEYIDF, 1)
        dict[formatKey] = true
        UserDefaults.standard.set(dict, forKey: APPLICATIONKEY)
        UserDefaults.standard.synchronize()
        
        let application = Application(id: 1, name: "name", iconUrl: "desp", description: "url")
        
        dict = UserDefaults.standard.dictionary(forKey: APPLICATIONKEY) ?? [:] as [String:Bool]
        dict[formatKey] = nil
        UserDefaults.standard.set(dict, forKey: APPLICATIONKEY)
        UserDefaults.standard.synchronize()
        
        XCTAssertTrue(application.isFavorite)
    }
    
    func testApplicationViewModelInit_ReturnValidApplicationList() throws {
        let applicationViewModel = ApplicationViewModel()
        let exp = expectation(description: "testApplicationViewModelAppend_ReturnValidApplicationList")
        
        let queue = DispatchQueue(label: "appTest")
        let delay: DispatchTimeInterval = .seconds((2))
        queue.asyncAfter(deadline: .now() + delay) {
            XCTAssertEqual(applicationViewModel.applications.count, 10)
            exp.fulfill()
        }
        wait(for: [exp], timeout: 3)
    }
    
    func testApplicationViewModelAppend_ReturnValidApplicationList() throws {
        let applicationViewModel = ApplicationViewModel()
        let exp = expectation(description: "testApplicationViewModelAppend_ReturnValidApplicationList")
        
        let queue = DispatchQueue(label: "appTest")
        let delay: DispatchTimeInterval = .seconds((4))
        queue.asyncAfter(deadline: .now() + delay) {
            XCTAssertEqual(applicationViewModel.applications.count, 20)
            exp.fulfill()
        }
        Task {
            await applicationViewModel.fetchApplications()
        }
        wait(for: [exp], timeout: 5)
    }
    
    func testApplicationViewModelRefresh_ReturnValidApplicationList() throws {
        let applicationViewModel = ApplicationViewModel()
        let exp = expectation(description: "testApplicationViewModelAppend_ReturnValidApplicationList")
        
        let queue = DispatchQueue(label: "appTest")
        let delay: DispatchTimeInterval = .seconds((2))
        queue.asyncAfter(deadline: .now() + delay + delay + delay) {
            XCTAssertEqual(applicationViewModel.applications.count, 10)
            exp.fulfill()
        }
        
        queue.asyncAfter(deadline: .now() + delay) {
            Task {
                await applicationViewModel.fetchApplications()
            }
        }
        
        queue.asyncAfter(deadline: .now() + delay + delay) {
            Task {
                await applicationViewModel.refresh()
            }
        }
        wait(for: [exp], timeout: 8)
    }
    
    func testApplicationViewModelSyncUserDefault_ReturnValidUserDefault() throws {
        let applicationViewModel = ApplicationViewModel()
        let exp = expectation(description: "testApplicationViewModelAppend_ReturnValidApplicationList")
        
        let queue = DispatchQueue(label: "appTest")
        let delay: DispatchTimeInterval = .seconds((2))
        var saveFav = false
        queue.asyncAfter(deadline: .now() + delay) {
            let application = applicationViewModel.applications[0]
            saveFav = !application.isFavorite
            applicationViewModel.applications[0].isFavorite = saveFav
            applicationViewModel.syncUserDefault()
            let dict = UserDefaults.standard.dictionary(forKey: APPLICATIONKEY) ?? [:] as [String:Bool]
            let formatKey = String(format: APPLICATIONKEYIDF, application.id)
            let getFav = dict[formatKey] as! Bool
            
            applicationViewModel.applications[0].isFavorite = !saveFav
            applicationViewModel.syncUserDefault()
            
            XCTAssertEqual(getFav, saveFav)
            exp.fulfill()
        }
        wait(for: [exp], timeout: 3)
    }
}
