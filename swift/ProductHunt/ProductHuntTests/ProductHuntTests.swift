//
//  ProductHuntTests.swift
//  ProductHuntTests
//
//  Created by Jinya on 2022/10/6.
//

import XCTest
@testable import ProductHunt
import Combine

private enum TestProductProvider {
    case normal // always get response with random number of products from (0 ... capacity).
    case error // awlays failed to get response.
    
    var fetch: (Int, Int) async throws -> ProductProviderResponse {
        switch self {
        case .normal:
            return {
                let count = max(0, $1)
                let products = Product.makePreviews(count: (0 ... count).randomElement()!)
                return .init(totalNumber: 10000, products: products)
            }
        case .error:
            return { _, _ in
                try? await Task.sleep(until: .now + .seconds(1), clock: .continuous)
                throw URLError(.badServerResponse)
            }
        }
    }
}

@MainActor
final class ProductHuntTests: XCTestCase {
    private var cancellables: Set<AnyCancellable> = []
    
    // The max number of products on each page response.
    private var capacity: Int!
    
    // View model always with success response
    private var viewModelNormal: ProductListViewModel!
    // View model always with error response
    private var viewModelError: ProductListViewModel!

    override func setUpWithError() throws {
        capacity = (1 ..< 99).randomElement()!
        viewModelNormal = ProductListViewModel(
            capacityOfEachPage: capacity,
            productProvider: TestProductProvider.normal.fetch
        )
        viewModelError = ProductListViewModel(
            capacityOfEachPage: capacity,
            productProvider: TestProductProvider.error.fetch
        )
    }

    override func tearDownWithError() throws {
        cancellables.forEach { $0.cancel() }
        viewModelNormal = nil
        viewModelError = nil
        capacity = nil
    }
    
    func testProductProviderService() async {
        for _  in 0 ..< 20 {
            let page = (0 ..< 999999).randomElement()!
            let capacity = (0 ..< 999999).randomElement()!
            async let _ = try! await ProductProvider.shared.products(forPage: page, capacity: capacity)
        }
    }

    func testInitialState() async {
        XCTAssertEqual(viewModelNormal.products.isEmpty, true)
        XCTAssertEqual(viewModelNormal.isInitialLoading, false)
        XCTAssertEqual(viewModelNormal.isPulledToRefreshing, false)
        XCTAssertEqual(viewModelNormal.isLoadingMore, false)
        XCTAssertEqual(viewModelNormal.error, nil)
        XCTAssertEqual(viewModelNormal.canLoadMore, false)
        
        XCTAssertEqual(viewModelError.products.isEmpty, true)
        XCTAssertEqual(viewModelError.isInitialLoading, false)
        XCTAssertEqual(viewModelError.isPulledToRefreshing, false)
        XCTAssertEqual(viewModelError.isLoadingMore, false)
        XCTAssertEqual(viewModelError.error, nil)
        XCTAssertEqual(viewModelError.canLoadMore, false)
    }
    
    func testInitialLoad() async {
        // Test view model with normal response
        let exp1 = XCTestExpectation(description: "Begin initial loading.")
        let exp2 = XCTestExpectation(description: "End initial loading.")
        let exp3 = XCTestExpectation(description: "Received initial response.")
        
        viewModelNormal.$isInitialLoading
            .dropFirst(1)
            .first()
            .sink(receiveValue: { _isInitialLoading in
                XCTAssertEqual(_isInitialLoading, true)
                exp1.fulfill()
            })
            .store(in: &cancellables)

        viewModelNormal.$isInitialLoading
            .dropFirst(2)
            .sink(receiveValue: { _isInitialLoading in
                XCTAssertEqual(_isInitialLoading, false)
                exp2.fulfill()
            })
            .store(in: &cancellables)
        
        viewModelNormal.$products
            .sink { _ in
                exp3.fulfill()
            }
            .store(in: &cancellables)
        
        await viewModelNormal.loadInitialProducts()
        wait(for: [exp1, exp2, exp3], timeout: 2)
        XCTAssertLessThanOrEqual(viewModelNormal.products.count, capacity)
        
        // Test view model with error response
        let exp4 = XCTestExpectation(description: "Begin initial loading.")
        let exp5 = XCTestExpectation(description: "End initial loading.")
        let exp6 = XCTestExpectation(description: "Received error.")
        
        viewModelError.$isInitialLoading
            .dropFirst(1)
            .first()
            .sink(receiveValue: { _isInitialLoading in
                XCTAssertEqual(_isInitialLoading, true)
                exp4.fulfill()
            })
            .store(in: &cancellables)

        viewModelError.$isInitialLoading
            .dropFirst(2)
            .sink(receiveValue: { _isInitialLoading in
                XCTAssertEqual(_isInitialLoading, false)
                exp5.fulfill()
            })
            .store(in: &cancellables)
        
        viewModelError.$error
            .sink { _ in
                exp6.fulfill()
            }
            .store(in: &cancellables)
        
        await viewModelError.loadInitialProducts()
        wait(for: [exp4, exp5, exp6], timeout: 2)
        XCTAssertEqual(viewModelError.products.isEmpty, true)
    }
    
    func testPullToRefresh() async {
        await viewModelNormal.loadInitialProducts()
        await viewModelError.loadInitialProducts()
        
        let numberOfProductsBeforeRefresh_normal = viewModelNormal.products.count
        let numberOfProductsBeforeRefresh_error = viewModelError.products.count
        
        // Test view model with normal response
        let exp1 = XCTestExpectation(description: "Begin pulled to refresh.")
        let exp2 = XCTestExpectation(description: "End pulled to refresh.")
        let exp3 = XCTestExpectation(description: "Received response.")
        
        viewModelNormal.$isPulledToRefreshing
            .dropFirst(1)
            .first()
            .sink(receiveValue: { _isPulledToRefreshing in
                XCTAssertEqual(_isPulledToRefreshing, true)
                exp1.fulfill()
            })
            .store(in: &cancellables)

        viewModelNormal.$isPulledToRefreshing
            .dropFirst(2)
            .sink(receiveValue: { _isPulledToRefreshing in
                XCTAssertEqual(_isPulledToRefreshing, false)
                exp2.fulfill()
            })
            .store(in: &cancellables)
        
        viewModelNormal.$products
            .sink { _ in
                exp3.fulfill()
            }
            .store(in: &cancellables)
        
        await viewModelNormal.refreshProducts()
        wait(for: [exp1, exp2, exp3], timeout: 2)
        XCTAssertLessThanOrEqual(viewModelNormal.products.count, numberOfProductsBeforeRefresh_normal + capacity)
        
        // Test view model with error response
        let exp4 = XCTestExpectation(description: "Begin pulled to refresh.")
        let exp5 = XCTestExpectation(description: "End pulled to refresh.")
        let exp6 = XCTestExpectation(description: "Received error.")
        
        viewModelError.$isPulledToRefreshing
            .dropFirst(1)
            .first()
            .sink(receiveValue: { _isPulledToRefreshing in
                XCTAssertEqual(_isPulledToRefreshing, true)
                exp4.fulfill()
            })
            .store(in: &cancellables)

        viewModelError.$isPulledToRefreshing
            .dropFirst(2)
            .sink(receiveValue: { _isPulledToRefreshing in
                XCTAssertEqual(_isPulledToRefreshing, false)
                exp5.fulfill()
            })
            .store(in: &cancellables)
        
        viewModelError.$error
            .sink { _ in
                exp6.fulfill()
            }
            .store(in: &cancellables)
        
        await viewModelError.refreshProducts()
        wait(for: [exp4, exp5, exp6], timeout: 2)
        XCTAssertEqual(viewModelError.products.count, numberOfProductsBeforeRefresh_error)
    }
    
    func testLoadMore() async {
        await viewModelNormal.loadInitialProducts()
        await viewModelError.loadInitialProducts()
        
        let numberOfProductsBeforeMoreLoaded_normal = viewModelNormal.products.count
        let numberOfProductsBeforeMoreLoaded_error = viewModelError.products.count
        
        // Test view model with normal response
        let exp1 = XCTestExpectation(description: "Begin loading more.")
        let exp2 = XCTestExpectation(description: "End loading more.")
        let exp3 = XCTestExpectation(description: "Received response.")
        
        viewModelNormal.$isLoadingMore
            .dropFirst(1)
            .first()
            .sink(receiveValue: { _isLoadingMore in
                XCTAssertEqual(_isLoadingMore, true)
                exp1.fulfill()
            })
            .store(in: &cancellables)

        viewModelNormal.$isLoadingMore
            .dropFirst(2)
            .sink(receiveValue: { _isLoadingMore in
                XCTAssertEqual(_isLoadingMore, false)
                exp2.fulfill()
            })
            .store(in: &cancellables)
        
        viewModelNormal.$products
            .sink { _ in
                exp3.fulfill()
            }
            .store(in: &cancellables)
        
        await viewModelNormal.loadMoreProducts()
        wait(for: [exp1, exp2, exp3], timeout: 2)
        XCTAssertLessThanOrEqual(viewModelNormal.products.count, numberOfProductsBeforeMoreLoaded_normal + capacity)
        
        // Test view model with error response
        let exp4 = XCTestExpectation(description: "Begin loading more.")
        let exp5 = XCTestExpectation(description: "End loading more.")
        let exp6 = XCTestExpectation(description: "Received error.")
        
        viewModelError.$isLoadingMore
            .dropFirst(1)
            .first()
            .sink(receiveValue: { _isLoadingMore in
                XCTAssertEqual(_isLoadingMore, true)
                exp4.fulfill()
            })
            .store(in: &cancellables)

        viewModelError.$isLoadingMore
            .dropFirst(2)
            .sink(receiveValue: { _isLoadingMore in
                XCTAssertEqual(_isLoadingMore, false)
                exp5.fulfill()
            })
            .store(in: &cancellables)
        
        viewModelError.$error
            .sink { _ in
                exp6.fulfill()
            }
            .store(in: &cancellables)
        
        await viewModelError.loadMoreProducts()
        wait(for: [exp4, exp5, exp6], timeout: 2)
        XCTAssertEqual(viewModelError.products.count, numberOfProductsBeforeMoreLoaded_error)
    }
    
    func testToggleLikes() async {
        await viewModelNormal.loadInitialProducts()
        
        // Select a random product
        guard let index = viewModelNormal.products.indices.randomElement() else {
            return
        }
        let product = viewModelNormal.products[index]
        
        let expectation = XCTestExpectation(description: "Products changes after toggled some product's like")
        viewModelNormal.$products
            .dropFirst()
            .sink { changedProducts in
                XCTAssertNotEqual(product.isLiked, changedProducts[index].isLiked)
                expectation.fulfill()
            }
            .store(in: &cancellables)
        
        await viewModelNormal.toggleLike(forProduct: product)
        wait(for: [expectation], timeout: 1)
    }
}
