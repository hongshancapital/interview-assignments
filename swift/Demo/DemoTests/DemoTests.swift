//
//  DemoTests.swift
//  DemoTests
//
//  Created by jyt on 2023/3/21.
//

import XCTest
@testable import Demo

final class DemoTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        
        UserDefaults.standard.removeObject(forKey: "favoriteAppIDs")
    }

    func testLoadJSON() throws {
        let dir = ["test.json", "1.json"]

        var appList: AppItemList?
        for fileName in dir {
            XCTAssertNoThrow(appList = try loadJSON(fileName))
            print(appList?.resultCount ?? -1)
        }
        
        let url = Bundle.main.url(forResource: "1.json", withExtension: nil)!
        let data = try Data(contentsOf: url)
        appList = try loadJSON(data)
    }
    
    @MainActor
    func testViewModel1 () throws {
        let vm = AppListViewModel()
        vm.loadMore()
        Task {
            await vm.refreshData()
        }
    }
    
    func testViewModel2 () async throws {
        let vm = AppListViewModel()
        await vm.refreshData()
    }
    
    func testViewModel3 () throws {
        let vm = AppListViewModel()
        vm.toggleFavorite(2137923)
        vm.toggleFavorite(21379213792173)
        vm.toggleFavorite(3331)
        vm.toggleFavorite(2235)
        
        print(UserDefaults.standard.mutableSetValue(forKey: "favoriteAppIDs"))
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
