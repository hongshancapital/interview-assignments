//
//  SearchListViewModelTest.swift
//  ListDemoTests
//
//  Created by kent.sun on 2023/2/3.
//

import XCTest
import Combine
@testable import ListDemo

class SearchListViewModelTest: XCTestCase {
    
    private var networking: MockSearchAppAPI!
    private var viewModel: SearchListViewModel!
    
    override func setUp() {
        super.setUp()
        networking = MockSearchAppAPI()
        viewModel = SearchListViewModel(networking: networking)
    }
    
    override func tearDownWithError() throws {
        Database().reset()
        super.tearDown()
    }

    func test_searchAppItems() {
        // Given
        let sut = viewModel
        // When
        sut!.searchAppItems(false)
        // Then
        XCTAssertTrue(sut!.listFull)
        XCTAssertEqual(sut!.items.count, 1)
    }

    func test_loadMoreAppItems() {
        // Given
        let sut = viewModel
        // When
        sut!.loadMoreAppItems()
        // Then
        XCTAssertEqual(sut!.state, .loaded)
        XCTAssertTrue(sut!.listFull)
    }
    
    func test_refreshAppItems() {
        // Given
        let sut = viewModel
        // When
        sut!.loadMoreAppItems()
        // Then
        XCTAssertEqual(sut!.state, .loaded)
        XCTAssertTrue(sut!.listFull)
    }
    
    func test_contains() {
        // Given
        let appItem = DataModel.AppItem(artworkUrl100: "xxx.jpg", description: "Google Chat is an intelligent and secure communication", trackId: 000011233111, title: "xxx")
        let sut = viewModel
        
        // When
        let result = sut!.contains(appItem)
        
        // Then
        XCTAssertTrue(!result)
    }
    
    func test_toggleFav() {
        // Given
        let appItem = DataModel.AppItem(artworkUrl100: "xxx.jpg", description: "Google Chat is an intelligent and secure communication", trackId: 0000112338, title: "xxx")
        let sut = viewModel
        
        // When
        sut!.toggleCollect(appItem)
        
        // Then
        let result = sut!.contains(appItem)
        XCTAssertTrue(result)
    }
}
