//
//  ImageRepositoryTests.swift
//  DemoTests
//
//  Created by 葬花桥 on 2023/3/17.
//

import XCTest
import Combine
@testable import Demo

final class ImageRepositoryTests: XCTestCase {

    private var sut: DemoRealImageRepository!
    private var subscriptions = Set<AnyCancellable>()
    private let testImage = UIImage(systemName: "suit.heart.fill")!

    typealias Mock = RequestMocking.MockedResponse

    override func setUp() {
        subscriptions = Set<AnyCancellable>()
        sut = DemoRealImageRepository(session: .mockedResponsesOnly,
                                     baseURL: "https://www.test.com")
    }

    override func tearDown() {
        RequestMocking.removeAllMocks()
    }
    
    func test_load_image_success() async throws {
        
        let imageURL = URL(string: "https://www.test.com/test.png")!
        let responseData = testImage.pngData()!
        let mock = Mock(url: imageURL, result: .success(responseData))
        RequestMocking.add(mock: mock)
        
        let _ = try await sut.load(imageURL: imageURL)
    }
    
    func test_loadImage_failure() async throws {
        let imageURL = URL(string: "https://www.test.com/test.png")!
        do {
            _ = try await self.sut.load(imageURL: imageURL)
            XCTFail(NSError.test.localizedDescription)
        } catch {
            
        }
    }
}
