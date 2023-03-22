//
//  AppItemTest.swift
//  ListDemoTests
//
//  Created by kent.sun on 2023/2/3.
//

import XCTest
@testable import ListDemo
final class AppItemTest: XCTestCase {

    override func setUpWithError() throws {
        super.setUp()

    }

    override func tearDownWithError() throws {
        super.tearDown()
    }

    func testJsonMapping() throws {
        // Given
        let data = try getData(fromJSON: "SearchResponse")
        let sut = try JSONDecoder().decode(DataModel.AppItems.self, from: data)
        
        // Then
        XCTAssertEqual(sut.resultCount, 2)
        XCTAssertEqual(sut.results[0].title, "Google Chat")
        XCTAssertEqual(sut.results[0].trackId, 1163852619)
        XCTAssertEqual(
            sut.results[0].description,
            "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export"
        )
        
        XCTAssertEqual(
            sut.results[0].artworkUrl100, "https://is1-ssl.mzstatic.com/image/thumb/Purple123/v4/19/91/17/199117a1-e4ab-8990-4934-74cb99662f26/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-0-85-220-0.png/100x100bb.jpg"
        )
    }

}
