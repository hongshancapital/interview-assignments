//
//  ViewModel.swift
//  ProductSearch
//
//  Created by foolbear on 2021/10/1.
//

import Foundation
import Combine

class SearchTextViewModel: ObservableObject {
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

class HttpViewModel: ObservableObject {
    @Published public var vendors: [Vendor] = []
    var pageIndex: Int = 0
    var hasMore: Bool = true
    var isVendorsEmpty: Bool { vendors.isEmpty }
    
    @MainActor
    func search(for searchText: String) async {
        let urlString = "http://127.0.0.1:\(HttpViewModel.port)/\(searchText.lowercased().trimmingCharacters(in: .whitespaces))/\(pageIndex)"
        guard let url = URL(string: urlString) else { return }
        print("load page \(pageIndex): \(urlString)")
        do {
            let (data, _) = try await URLSession.shared.data(from: url)
            let resp = try JSONDecoder().decode(Response.self, from: data)
            vendors.append(contentsOf: resp.vendors)
            hasMore = resp.hasMore
            print("succ, vendors count = \(vendors.count)")
        } catch {
            print("error: " + error.localizedDescription)
        }
        pageIndex = hasMore ? pageIndex+1 : 0
    }
    
    func reset() {
        pageIndex = 0
        hasMore = true
        vendors = []
    }
    
    func isLastProduct(_ product: Product) -> Bool {
        guard let vendor = vendors.last, let kind = vendor.kinds.last else { return false }
        return product == kind.products.last
    }
}

extension HttpViewModel {
    static let port = 8086
    static let rsponse0: String = """
            {
                "hasMore": true,
                "vendors": [
                    {
                        "id": 0,
                        "name": "Dyson",
                        "kinds": [
                            {
                                "id": 0,
                                "name": "Vacuume",
                                "products": [
                                    {
                                        "id": 0,
                                        "name": "V11",
                                        "price": 599.99,
                                        "inStock": true
                                    },
                                    {
                                        "id": 9,
                                        "name": "V10",
                                        "price": 399.99,
                                        "inStock": false
                                    }
                                ]
                            },
                            {
                                "id": 5,
                                "name": "Hair Dryer",
                                "products": [
                                    {
                                        "id": 15,
                                        "name": "Supersonic",
                                        "price": 399.99,
                                        "inStock": true
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
            """
    
    static let rsponse1: String = """
            {
                "hasMore": false,
                "vendors": [
                    {
                        "id": 3,
                        "name": "Dyson",
                        "kinds": [
                            {
                                "id": 30,
                                "name": "Airwrap",
                                "products": [
                                    {
                                        "id": 50,
                                        "name": "W21",
                                        "price": 159.99,
                                        "inStock": true
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
            """
    
    static let rsponse2: String = """
            {
                "hasMore": false,
                "vendors": []
            }
            """
}
