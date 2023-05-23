//
//  DemoImageServiceTest.swift
//  DemoTests
//
//  Created by 葬花桥 on 2023/3/17.
//

import XCTest
import UIKit
@testable import Demo
import Combine

final class MockedImageWebRepository: Mock, DemoImageRepository {
    let session: URLSession = .mockedResponsesOnly
    let baseURL = "https://test.com"
    let bgQueue = DispatchQueue(label: "test")
    
    enum Action: Equatable {
        case loadImage(URL)
    }
    var actions = MockActions<Action>(expected: [])
    
    var imageResponse = UIImage(systemName: "suit.heart.fill")!
    
    var error: NSError?
    
    func load(imageURL: URL) async throws -> UIImage {
        register(.loadImage(imageURL))
        if let error {
            throw error
        } else {
            return imageResponse
        }
    }
}

final class DemoImageServiceTest: XCTestCase {
    var sut: DemoRealImageService!
    var mockedWebRepository: MockedImageWebRepository!
    var subscriptions = Set<AnyCancellable>()
    let testImageURL = URL(string: "https://test.com/test.png")!
    let testImage = UIImage(systemName: "suit.heart.fill")!
    typealias Mock = RequestMocking.MockedResponse
 
    override func setUp() {
        mockedWebRepository = MockedImageWebRepository()
        sut = DemoRealImageService(webRepository: mockedWebRepository)
        subscriptions = Set<AnyCancellable>()
    }
    
    func expectRepoActions(_ actions: [MockedImageWebRepository.Action]) {
        mockedWebRepository.actions = .init(expected: actions)
    }
    
    func verifyRepoActions(file: StaticString = #file, line: UInt = #line) {
        mockedWebRepository.verify(file: file, line: line)
    }
    
    
    func test_load_image_loadedFromWeb() async throws {
        expectRepoActions([.loadImage(testImageURL)])
        let image = try await sut.load(imageURL: testImageURL)
        XCTAssertEqual(image, testImage)
        self.verifyRepoActions()

    }
    
    func test_load_image_failed() async {
        let error = NSError.test
        mockedWebRepository.error = error
        expectRepoActions([.loadImage(testImageURL)])
        await XCTAssertThrowsError {
            try await self.sut.load(imageURL: self.testImageURL)
        }
        self.verifyRepoActions()
    }
}
