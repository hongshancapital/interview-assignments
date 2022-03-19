//
//  AppsDemoTests.swift
//  AppsDemoTests
//
//  Created by changcun on 2022/3/13.
//

import XCTest
@testable import AppsDemo
import ViewInspector

extension AppInfoRow: Inspectable {}
extension AppList: Inspectable {}

class AppsDemoUITests: XCTestCase {

    func testAppInfoRow() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
                
        let appInfo = AppInfoModel(name: "google",
                                   description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.",
                                   imageUrlString: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/06/8c/e5/068ce5a0-8a33-41ee-488a-95067d2b241a/source/100x100bb.jpg")
        
        let row = AppInfoRow(appInfo: .constant(appInfo), index: 0) { index in
            XCTAssertEqual(index, 0)
        }
        
        /// 标题
        let titleView = try row.inspect().find(viewWithId: AppInfoRow.Identifiers.title).text()
        let title = try titleView.string()
        XCTAssertEqual(title, "google")
        
        /// 副标题
        let subTitleView = try row.inspect().find(viewWithId: AppInfoRow.Identifiers.subTitle).text()
        let subtitle = try subTitleView.string()
        XCTAssertEqual(subtitle, "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.")
        
        /// 收藏按钮
        let collectButton = try? row.inspect().find(viewWithId: AppInfoRow.Identifiers.collectButton).button()
        
        XCTAssertNotNil(collectButton)

        try collectButton?.tap()
    }
}
