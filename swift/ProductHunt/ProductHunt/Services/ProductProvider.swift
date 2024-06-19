//
//  ProductProvider.swift
//  ProductHunt
//
//  Created by Jinya on 2022/10/6.
//

import Foundation
import os.log

private let logger = Logger(
    subsystem: Bundle.main.bundleIdentifier!,
    category: String(describing: ProductProvider.self)
)

actor ProductProvider {
    private lazy var caches: [Product] = {
        if let mock: MockModel = load("mock.json") {
            return mock.products
        }
        return []
    }()
    
    // Public initializr disabled, use `ProductProvider.shared` instead.
    private init() {}
    
    private func cachedProducts(forPage page: Int, capacity: Int) -> [Product] {
        // WARN: care `Int` overflow
        let start = capacity &* (page - 1)
        let end = capacity &* page
        guard start < end else { return [] }
        return caches.enumerated()
            .filter {
                (start ..< end).contains($0.offset)
            }
            .compactMap { $0.element }
    }
    
    private func load<T>(_ fileName : String) -> T? where T: Decodable {
        var value: T? = nil
        guard let url = Bundle.main.url(forResource: fileName, withExtension: nil) else {
            return value
        }
        do {
            let data = try Data(contentsOf: url)
            value = try JSONDecoder().decode(T.self, from: data)
        } catch {
            logger.error("\(error.localizedDescription)")
        }
        return value
    }
}

extension ProductProvider {
    static let shared = ProductProvider()
    
    /// Returns products from the given page.
    /// - Parameters:
    ///   - page: Number of page.
    ///   - capacity: The largest number of products that each page contains.
    /// - Returns: An model contains the total number of all products and products at the given page.
    func products(forPage page: Int, capacity: Int) async throws -> ProductProviderResponse {
        // Mocking network delay for one second.
        try await Task.sleep(until: .now + .seconds(1), clock: .continuous)

        let products = cachedProducts(forPage: page, capacity: capacity)
        return ProductProviderResponse(
            totalNumber: caches.count,
            products: products
        )
    }
}
