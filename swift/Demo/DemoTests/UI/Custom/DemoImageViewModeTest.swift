//
//  DemoImageViewModeTest.swift
//  DemoTests
//
//  Created by 葬花桥 on 2023/3/17.
//

import XCTest
@testable import Demo
import Combine

class MockDemoImageService: Mock, DemoImageService {
    
    enum Action: Equatable {
        case load(url: URL)
    }
    
    var actions: MockActions<Action>
    
    init(expected: [Action]) {
        self.actions = .init(expected: expected)
    }
        
    var response: UIImage! = UIImage(systemName: "suit.heart.fill")!
    
    var invalidURLError: APIError?
    
    var responseError: Error?
    
    func load(imageURL: URL) async throws -> UIImage {
        register(.load(url: imageURL))
        
        if let invalidURLError {
            throw invalidURLError
        }
        
        if let responseError {
            throw responseError
        }
        
        return response
    }
}

final class DemoImageViewModelTest: XCTestCase {
    var sut: DemoImageView.ViewModel!
    var mockService: MockDemoImageService!
    let testURL = "https://www.test.com/mypng.png"
    let testImage = UIImage(systemName: "suit.heart.fill")!
    
    override func setUp() {
        super.setUp()
        let url = URL(string: testURL)!
        mockService = MockDemoImageService(expected: [.load(url: url)])
        sut = DemoImageView.ViewModel(imageURL: testURL, service: mockService)
        
    }
    
    /// 非法url测试
    func test_load_image_invalid_url() async throws {
        mockService = MockDemoImageService(expected: [.load(url: URL(string: "testURL")!)])
        mockService.invalidURLError = APIError.invalidURL
        sut = DemoImageView.ViewModel(imageURL: "testURL", service: mockService)
        await sut.load()
        
        for try await loadableImage in sut.$image
            .timeoutSequence(.milliseconds(5000), scheduler: DispatchQueue.main) {
            XCTAssertNotNil(loadableImage.error)
            break
        }
        
        mockService.verify()
    }
    
    /// 加载成功测试
    func test_load_image_succeed() async throws {
        await sut.load()
        
        for try await loadableImage in sut.$image
            .timeoutSequence(.milliseconds(5000), scheduler: DispatchQueue.main) {
            XCTAssertNotNil(loadableImage.value)
            break
        }
        
        mockService.verify()
    }
    
    /// 加载失败测试
    func test_load_image_failed() async throws {
        mockService = MockDemoImageService(expected: [.load(url: URL(string: "testURL")!)])
        mockService.responseError = NSError.test
        sut = DemoImageView.ViewModel(imageURL: "testURL", service: mockService)
        await sut.load()
        
        for try await loadableImage in sut.$image
            .timeoutSequence(.milliseconds(5000), scheduler: DispatchQueue.main) {
            XCTAssertNotNil(loadableImage.error)
            break
        }
        
        mockService.verify()
    }
    
    /// 从缓存加载测试
    func test_load_image_in_cache() async throws {
        mockService = MockDemoImageService(expected: [])
        sut = DemoImageView.ViewModel(imageURL: testURL, service: mockService)
 
        DemoCache.shared.set(key: testURL, image: testImage)
        await sut.load()
        
        for try await loadableImage in sut.$image
            .timeoutSequence(.milliseconds(5000), scheduler: DispatchQueue.main) {
            XCTAssertNotNil(loadableImage.value)
            break
        }
        
        mockService.verify()
    }
}
