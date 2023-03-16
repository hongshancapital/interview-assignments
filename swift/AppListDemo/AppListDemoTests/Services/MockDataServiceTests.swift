//
//  MockDataServiceTests.swift
//  AppListDemoTests
//
//  Created by Kimi on 2023/3/7.
//

import XCTest
@testable import AppListDemo

@MainActor
final class MockDataServiceTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testFetchAppList() async {
        do {
            let response = try await MockDataService.shared.fetchAppList(atPage: 0, pageCount: 10)
            
            XCTAssertEqual(response.code, .success)
            XCTAssert(!response.appModels.isEmpty)
        } catch {
            print(error)
        }
    }
    
    func testFetchAppListError() async {
        do {
            let response = try await MockErrorDataService.shared.fetchAppList(atPage: 0, pageCount: 10)
            
            XCTAssertEqual(response.code, .fail)
            XCTAssert(response.appModels.isEmpty)
        } catch {
            print(error)
        }
    }
    
    func testToggleFavouriteApp() async {
        do {
            let appModel = AppModel(id: "id", appName: "name", appDescription: "desc", appIconUrlString: "url")
            let response = try await MockDataService.shared.toggleFavouriteApp(appModel)
            
            XCTAssertEqual(response.code, .success)
        } catch {
            print(error)
        }
    }
    
    func testToggleFavouriteAppError() async {
        do {
            let appModel = AppModel(id: "id", appName: "name", appDescription: "desc", appIconUrlString: "url")
            let response = try await MockErrorDataService.shared.toggleFavouriteApp(appModel)
            
            XCTAssertEqual(response.code, .fail)
        } catch {
            print(error)
        }
    }
}
