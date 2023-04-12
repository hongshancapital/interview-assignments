import XCTest
@testable import AppList

final class CardListDataProviderTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testLoadCardList() async throws {
        
        let exp = XCTestExpectation(description: "test CardListDataProvider load card list")
        
        let provider = CardListDataProvider()
        do {
            try await provider.loadCardList()
            exp.fulfill()
        } catch {
            XCTFail("load card list failed")
        }
        
        await fulfillment(of: [exp], timeout: 3)
    }
    
    func testFetchDataWhenParametersAreInvalid() async throws {
        
        let exp1 = XCTestExpectation(description: "test CardListDataProvider fetch data when from is invalid")
        let exp2 = XCTestExpectation(description: "test CardListDataProvider fetch data when stride is invalid")
        
        let exp3 = XCTestExpectation(description: "test CardListDataProvider fetch data when from and stride are invalid")
        
        let provider = CardListDataProvider()
        do {
            let _ = try await provider.fetchData(from: -1, stride: 10)
        } catch {
            exp1.fulfill()
        }
        
        do {
            let _ = try await provider.fetchData(from: 10, stride: -1)
        } catch {
            exp2.fulfill()
        }
        
        do {
            let _ = try await provider.fetchData(from: -1, stride: -1)
        } catch {
            exp3.fulfill()
        }
        await fulfillment(of: [exp1, exp2, exp3], timeout: 3)
    }
    
    func testFetchDataWhenAllDataIsNil() async throws {
        let provider = CardListDataProvider()
        do {
            let list = try await provider.fetchData(from: 0, stride: 10)
            XCTAssertEqual(list.count, 0)
        } catch {
            XCTFail("fetch data failed")
        }
    }
    
    func testFetchDataWhenSuccess() async throws {

        let provider = CardListDataProvider()
        do {
            try await provider.loadCardList()
            let list = try await provider.fetchData(from: 0, stride: 10)
            XCTAssertEqual(list.count, 10)
        } catch {
            XCTFail("fetch data failed")
        }
    }
}
