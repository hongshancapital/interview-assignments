//
//  LHAppListNetworkTest.swift
//  LHAppListDemoTests
//
//  Created by 刘志华 on 2022/5/15.
//

import XCTest

@testable import LHAppListDemo

struct LHTestModel: Codable {
    let resultCount: [Int]
}

class LHAppListNetworkTest: XCTestCase {
    func testSearchApp() async {
        let limit = 10
        let result = await request(LHAppAPI.searchApp(entity: "software", limit: limit, term: "chat", offset: 0), modelType: LHAppInfoRes.self)
        if let resp = result.resp {
            XCTAssertLessThanOrEqual(resp.resultCount, limit)
        } else if let err = result.err {
            XCTAssertNotEqual(err.code, 0)
        }
    }

    func testDecodeError() async {
        let result = await request(LHAppAPI.searchApp(entity: "", limit: 10, term: "", offset: 0), modelType: LHTestModel.self)
        if let err = result.err {
            XCTAssertEqual(err.code, -111)
        }
    }
}
