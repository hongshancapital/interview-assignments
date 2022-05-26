//
//  HSNetWorkTests.swift
//  HSAppStoreTests
//
//  Created by Sheng ma on 2022/5/16.
//

import XCTest
@testable import HSAppStore

struct HSTestAppModel: Codable, Identifiable, Hashable {
    var id: Int
    let url: String
    let name: String
    let description: String
    var isFavorite: Bool = false
    
    private enum CodingKeys: String, CodingKey {
        case id = "trackId"
        case url
        case name
        case description
    }
}

class HSNetWorkTests: XCTestCase {
    
    let network = HSNetWork.shared
    let params: HSNetworkParams = ["entity": "software",
                                   "limit": 15,
                                   "term": "chat"]
    let api = "https://itunes.apple.com/search"
    let errorApi = "https://itunes.apple.com/searchv2"
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testRequestData() async throws {
        do {
            let appData: HSAppDataSource = try await self.network.requestAppData(from: api, params: params)
            XCTAssertNotNil(appData)
        } catch {
            XCTAssertEqual(error as? HSNetWorkStatus, HSNetWorkStatus.responseError)
        }
    }
    
    func testRequestDataWithErrorApi() async throws {
        do {
            let appData: HSAppDataSource = try await self.network.requestAppData(from: errorApi, params: params)
            XCTAssertNil(appData)
        } catch {
            XCTAssertEqual(error as? HSNetWorkStatus, HSNetWorkStatus.invaildURL)
        }
    }
    
    func testInvaildURL() async throws {
        do {
            let _: HSAppModel = try await self.network.requestAppData(from: "invaild url", params: [:])
            XCTFail("Excepted to throw HSNetWorkStatus.parseError while await, but succeed")
        } catch {
            XCTAssertEqual(error as? HSNetWorkStatus, HSNetWorkStatus.invaildURL)
        }
    }
    
    func testDecodeModel() async throws {
        do {
            let _: HSTestAppModel = try await self.network.requestAppData(from: api, params: params)
            XCTFail("Excepted to throw HSNetWorkStatus.decodeError while await, but succeed")
        } catch {
            XCTAssertEqual(error as? HSNetWorkStatus, HSNetWorkStatus.decodeError)
        }
    }
}
