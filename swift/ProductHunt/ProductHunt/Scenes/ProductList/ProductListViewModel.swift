//
//  ProductListViewModel.swift
//  ProductHunt
//
//  Created by Jinya on 2022/10/6.
//

import Foundation
import os.log

enum ProductListError: Error, Equatable {
    case networkFailure
    
    var description: String {
        switch self {
        case .networkFailure:
            return "Failed to load products due to network error."
        }
    }
}

@MainActor final class ProductListViewModel: ObservableObject {
    @Published private(set) var products: [Product] = []
    
    @Published private(set) var isInitialLoading = false
    @Published private(set) var isPulledToRefreshing = false
    @Published private(set) var isLoadingMore = false
    
    @Published private(set) var error: ProductListError? = nil
    
    private var totalNumber: Int = 0
    private var capacityOfEachPage: Int = 20
    private var currentPage: Int = 1
    
    // External product provider dependencies
    private let productProvider: (_ page: Int, _ capacity: Int) async throws -> ProductProviderResponse
    
    private let logger = Logger(
        subsystem: Bundle.main.bundleIdentifier!,
        category: "\(ProductListViewModel.self)"
    )
    
    var canLoadMore: Bool {
        return totalNumber > 0 && products.count < totalNumber
    }
    
    init(
        capacityOfEachPage: Int,
        productProvider: @escaping (Int, Int) async throws -> ProductProviderResponse
    ) {
        self.capacityOfEachPage = capacityOfEachPage
        self.productProvider = productProvider
    }
    
    private func refresh(isInitial: Bool) async {
        if isInitialLoading || isPulledToRefreshing { return }
        
        // Show loading indicator.
        if isInitial {
            isInitialLoading = true
        } else {
            isPulledToRefreshing = true
        }
        
        do {
            // Fetch products for first page.
            let response = try await productProvider(1, capacityOfEachPage)
            // Update the total number
            totalNumber = response.totalNumber
            // Update products
            if isInitial {
                products = response.products
            } else {
                // If not initial, insert unique products at start.
                let uniqueProducts = response.products.filter { !products.contains($0) }
                products = uniqueProducts + products
            }
        } catch {
            logger.error("\(error.localizedDescription)")
            self.error = .networkFailure
        }
        
        // Dismiss loading indicator.
        if isInitial {
            isInitialLoading = false
        } else {
            isPulledToRefreshing = false
        }
    }
    
    private func loadMore() async {
        if isLoadingMore { return }
        
        // Show loading indicator.
        isLoadingMore = true
        
        do {
            // Fetch products for next page.
            let response = try await productProvider(currentPage + 1, capacityOfEachPage)
            // Update the total number
            totalNumber = response.totalNumber
            // Append unique products at end.
            let uniqueProducts = response.products.filter { !products.contains($0) }
            products.append(contentsOf: uniqueProducts)
            // If not empty, increase current page number
            if !response.products.isEmpty {
                currentPage += 1
            }
        } catch {
            logger.error("\(error.localizedDescription)")
            self.error = .networkFailure
        }
        
        // Dismiss loading indicator.
        isLoadingMore = false
    }
}

// MARK: - Public Methods
extension ProductListViewModel {
    func loadInitialProducts() async {
        await refresh(isInitial: true)
    }

    func refreshProducts() async {
        await refresh(isInitial: false)
    }
    
    func loadMoreProducts() async {
        await loadMore()
    }
    
    func toggleLike(forProduct product: Product) async {
        guard let index = products.firstIndex(of: product) else {
            fatalError("❌ Can't find the tapped product to continue `toggleLike` action...")
        }
        products[index].isLiked.toggle()
    }
}
