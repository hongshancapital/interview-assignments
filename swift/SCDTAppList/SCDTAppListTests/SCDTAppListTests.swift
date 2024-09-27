//
//  SCDTAppListTests.swift
//  SCDTAppListTests
//
//  Created by freeblow on 2023/2/14.
//

import XCTest
@testable import SCDTAppList

@MainActor
final class SCDTAppListTests: XCTestCase {
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testDataLoader(){
        let result1 = DataLoader<AppListResultModel>.load(filename: "test")
        ///data error
        XCTAssertNotNil(result1.1,"test file data load exception:\(String(describing: result1.1?.msg()))")
        
        ///generic error
        let result2 = DataLoader<AppProductModel>.load(filename:"applist.json")
        XCTAssertNil(result2.0, "test generic error.")
        
        
        ///data load success
        ///generic right
        let result3 = DataLoader<AppListResultModel>.load(filename:"applist.json")
        XCTAssertNotNil(result3.0, "test file data load success.")
        
    }
    
    func testViewModelInitState() async {
        let viewModel = SCDTAppListViewModel()
        XCTAssertEqual(viewModel.datas.isEmpty, true)
        XCTAssertEqual(viewModel.loadStatus, .first)
        XCTAssertEqual(viewModel.isHaveMore, true)
        XCTAssertEqual(viewModel.error, .none)
    }
    
    func testEmptyDataList(){
        let viewModel = SCDTAppListViewModel()
        XCTAssert(viewModel.datas.isEmpty)
    }
    
    func testEmptyDataStatusListView() async{
        let viewModel = SCDTAppListViewModel()
        viewModel.updateLoadStatus(status: .empty)
        XCTAssert(viewModel.loadStatus == .empty)
        
        viewModel.updateLoadStatus(status: .first)
        await viewModel.refresh()
        XCTAssert(viewModel.loadStatus == .stop)
    }
    
    func testLoadDataErrorStatusListView() async{
        let viewModel = SCDTAppListViewModel()
        viewModel.updateLoadStatus(status: .error)
        XCTAssert(viewModel.loadStatus == .error)
        viewModel.updateLoadStatus(status: .first)
        await viewModel.refresh()
        XCTAssert(viewModel.loadStatus == .stop)
    }
    
    
    func testDataLoadStatusListView() async{
        let viewModel = SCDTAppListViewModel()
        XCTAssert(viewModel.loadStatus == .first)
        await viewModel.refresh()
        XCTAssert(viewModel.loadStatus == .stop)
        await viewModel.loadMore()
        XCTAssert(viewModel.loadStatus == .stop)
        await viewModel.loadMore()
        XCTAssert(viewModel.loadStatus == .finish)
    }
    
    func testPageSize() async{
        ///When the pagesize is less than or equal to 0, the pagesize will be forced to 20,
        ///and then go through the normal loading process.
        ///If it is not forced to 0, it will display an empty page status
        let viewModel1 = SCDTAppListViewModel(pageSize: 0)
        XCTAssert(viewModel1.loadStatus == .first)
        await viewModel1.refresh()
        XCTAssert(viewModel1.loadStatus == .stop)
        
        
        let viewModel2 = SCDTAppListViewModel(pageSize: 0, isForce: false)
        XCTAssert(viewModel2.loadStatus == .empty)
        await viewModel2.refresh()
        XCTAssert(viewModel2.loadStatus == .empty)
    }
    

}
