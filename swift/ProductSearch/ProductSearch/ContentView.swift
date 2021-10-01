//
//  ContentView.swift
//  ProductSearch
//
//  Created by foolbear on 2021/10/1.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject var viewModel = SearchViewModel()
    
    var body: some View {
        NavigationView {
            SearchContent(searchText: $viewModel.searchTextO)
                .navigationTitle("Search")
        }.searchable(text: $viewModel.searchTextI, placement: .navigationBarDrawer, prompt: "Tap here to search")
    }
}

struct SearchContent: View {
    @Environment(\.isSearching) var isSearching
    @State private var pageIndex: Int = 0
    @State private var hasMore: Bool = true
    @State private var vendors: [Vendor] = []
    @Binding var searchText: String
    var isEmpty: Bool { vendors.isEmpty }
    
    var body: some View {
//        print(Self._printChanges()); return
        Group {
            if isSearching {
                if isEmpty {
                    VStack {
                        Spacer()
                        Text("No result")
                            .foregroundColor(.secondary)
                        Spacer()
                        Spacer()
                    }
                } else {
                    List {
                        ForEach(vendors) { vendor in
                            ForEach(vendor.kinds) { kind in
                                Section(kind.name) {
                                    ForEach(kind.products) { product in
                                        ProductView(product: product)
                                            .task {
                                                if isLastProduct(product) && hasMore {
                                                    print("load more")
                                                    await load()
                                                }
                                            }
                                    }
                                }.textCase(nil)
                            }
                        }
                    }
                }
            } else {
                Spacer()
            }
        }.onChange(of: searchText) { searchText in
            print("searchText changed as \(searchText)")
            pageIndex = 0
            hasMore = true
            vendors = []
            if searchText.isEmpty {
                return
            }
            Task {
                await load()
            }
        }
    }
}

extension SearchContent {
    func load() async {
        do {
//            let (data, _) = try await URLSession.shared.data(from: URL(string: "https://loadhost/request")!)
            let (data, hasMoreItems) = try await MockHttpServer.shared.request(searchText: searchText, pageIndex: pageIndex)
            guard let data = data else {
                return
            }
            let items = try JSONDecoder().decode([Vendor].self, from: data)
            vendors.append(contentsOf: items)
            hasMore = hasMoreItems
        } catch {
            print(error.localizedDescription)
        }
        pageIndex = hasMore ? pageIndex+1 : 0
    }
    
    func isLastProduct(_ product: Product) -> Bool {
        guard let vendor = vendors.last, let kind = vendor.kinds.last else { return false }
        return product == kind.products.last
    }
}

struct ProductView: View {
    var product: Product
    var inStockDescription: String { product.inStock ? "In-stock" : "Out-of-stock" }
    var isDisable: Bool { product.inStock == false }
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text("\(product.name)")
                    .font(.body.bold())
                    .foregroundStyle(.primary)
                Text("\(inStockDescription)")
                    .font(.caption)
                    .foregroundStyle(.primary)
            }
            Spacer()
            Button(action: {}) {
                Text(String(format: "$%.2f", product.price))
                    .font(.caption)
            }
            .tint(isDisable ? .secondary : .blue)
            .buttonStyle(.bordered)
            .controlSize(.small)
            .disabled(isDisable)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
