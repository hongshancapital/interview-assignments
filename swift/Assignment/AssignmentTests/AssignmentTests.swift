//
//  AssignmentTests.swift
//  AssignmentTests
//
//  Created by Yikai Deng on 2022/6/25.
//

import XCTest
@testable import Assignment

class AssignmentTests: XCTestCase {
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func testFetchData() throws {
        var cellModels: [CellModel] = []
        let predicate = NSPredicate() { _,_ in
            cellModels.count == 10
        }
        let expection = XCTNSPredicateExpectation(predicate: predicate, object: cellModels)
        cellModels = DataProvider.fetchData(pageNum: 1)
        let result = XCTWaiter().wait(for: [expection], timeout: 2.0)
        switch result {
        case .completed:
            XCTAssertEqual(cellModels.count, 10, "The count of the items in data array should be 10")
        default:
            XCTFail("Failed to fetch data")
        }
    }
    
    func testLoadMoreData() throws {
        var cellModels: [CellModel] = []
        let predicate = NSPredicate() { _,_ in
            cellModels.count == 20
        }
        let expection = XCTNSPredicateExpectation(predicate: predicate, object: cellModels)
        cellModels = DataProvider.fetchData(pageNum: 1)
        cellModels = DataProvider.fetchData(pageNum: 2)
        let result = XCTWaiter().wait(for: [expection], timeout: 2.0)
        switch result {
        case .completed:
            XCTAssertEqual(cellModels.count, 20, "The count of the items in data array should be 20")
        default:
            XCTFail("Failed to fetch data")
        }
    }
    
    func testChangeFavoriteState() throws {
        var cellModel = DataProvider.fetchData(pageNum: 1).first!
        cellModel.isFavorite = !(cellModel.isFavorite)
        DataProvider.changeFavorToFile(model: cellModel) {
            print("Succeeded to change favorite state")
        } failure: { error in
            print("Failed to change favorite state")
        }
        
        let testModel = DataProvider.fetchData(pageNum: 1).first!
        XCTAssert(testModel.isFavorite == cellModel.isFavorite, "The value of The two model's isFavorite property should be equal")
    }
    
    func testModelDecoding() throws {
        guard let url = Bundle.main.url(forResource: "data.json", withExtension: nil) else {
            XCTFail("Failed to find the json file")
            return
        }
        
        guard let data = try? Data(contentsOf: url) else {
            XCTFail("Failed to read data from json file")
            return
        }
        
        let result = Result(from: data)
        XCTAssert(result.result.count == 50, "The count of items in model array should be 50")
    }
    
    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    
}
