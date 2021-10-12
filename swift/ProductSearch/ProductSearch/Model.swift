//
//  Model.swift
//  ProductSearch
//
//  Created by foolbear on 2021/10/1.
//

import Foundation
import Combine

enum ProductState {
    case inStock, outOfStock
}

struct Response: Codable {
    var hasMore: Bool = false
    var vendors: [Vendor] = []
}

struct Vendor: Codable, Identifiable {
    var id: Int = 0
    var name: String = ""
    var kinds: [Kind] = []
}

struct Kind: Codable, Identifiable {
    var id: Int = 0
    var name: String = ""
    var products: [Product] = []
}

struct Product: Codable, Identifiable, Equatable {
    var id: Int = 0
    var name: String = ""
    var price: Double = 0
    var inStock: Bool = false
}

class SearchViewModel: ObservableObject {
    @Published var searchTextI: String = ""
    @Published var searchTextO: String = ""
    private var subscriptions: Set<AnyCancellable> = []
    
    init() {
        $searchTextI
            .debounce(for: .milliseconds(800), scheduler: DispatchQueue.main)
            .removeDuplicates()
            .receive(on: DispatchQueue.main)
            .assign(to: \.searchTextO, on: self)
            .store(in: &subscriptions)
    }
}
