import XCTest
@testable import AppList

final class CardListDataProviderTest: XCTestCase {
    
    var provider: CardListDataProvider!

    override func setUpWithError() throws {
        provider = CardListDataProvider()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testLoadCardList() async {
        do {
            await provider.loadCardList()
            let list = try await provider.fetchData(from: 0, stride: 10)
            XCTAssertFalse(list.isEmpty)
        } catch {
            XCTFail("load data failed")
        }
        
    }
    
    func testFetchDataWhenParametersAreInvalid() async {
        
        let exp1 = XCTestExpectation(description: "test CardListDataProvider fetch data when from is invalid")
        let exp2 = XCTestExpectation(description: "test CardListDataProvider fetch data when stride is invalid")
        
        let exp3 = XCTestExpectation(description: "test CardListDataProvider fetch data when from and stride are invalid")
        
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
    
    func testFetchDataWhenAllDataIsNil() async {
        do {
            let list = try await provider.fetchData(from: 0, stride: 10)
            XCTAssertEqual(list.count, 0)
        } catch {
            XCTFail("fetch data failed")
        }
    }
    
    func testFetchDataWhenSuccess() async {
        do {
            await provider.loadCardList()
            let list = try await provider.fetchData(from: 0, stride: 10)
            XCTAssertEqual(list.count, 10)
        } catch {
            XCTFail("fetch data failed")
        }
    }
}
