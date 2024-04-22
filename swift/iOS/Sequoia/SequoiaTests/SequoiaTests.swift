//
//  SequoiaTests.swift
//  SequoiaTests
//
//  Created by 徐锐 on 2024/4/11.
//

import XCTest
@testable import Sequoia

final class SequoiaTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

    func testSequoialRow() {
        let appleData: AppleData = AppleData(id: 1, trackId: 1, artworkUrl60: "https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/81/b6/25/81b6255c-1972-7ce6-24a2-148a710ce65c/logo_chat_2023q4_color-0-1x_U007emarketing-0-0-0-6-0-0-0-85-220-0.png/60x60bb.jpg", trackName: "sdf", description: "描述文字里是江东父老考上吉林")
        
        let _: SequoialRow = SequoialRow(appleData: appleData)
        XCTAssertTrue(appleData.isFavorite == false)
    }

    func testDataManager() {
        var dataManager: DataManager = DataManager.instance
        var netResultData = AppleNetResultData()
        netResultData.resultCount = 1
        var netData = AppleNetData(trackId: 1, artworkUrl60: "https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/81/b6/25/81b6255c-1972-7ce6-24a2-148a710ce65c/logo_chat_2023q4_color-0-1x_U007emarketing-0-0-0-6-0-0-0-85-220-0.png/60x60bb.jpg", trackName: "ss", description: "描述文字里是江东父老考上吉林")
        netResultData.results.append(netData)
        
        dataManager.loadNetData(appleNetDatas: netResultData)
        
        XCTAssertTrue(dataManager.appleDatas.results[0].id == 1)
        XCTAssertTrue(dataManager.appleDatas.results[0].isFavorite == false)
    }
}
