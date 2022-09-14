//
//  ContentView.swift
//  ProductSearch
//
//  Created by foolbear on 2021/10/1.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject var viewModel = SearchTextViewModel()
    
    var body: some View {
        NavigationView {
            SearchContent(searchText: $viewModel.searchTextO)
                .navigationTitle("Search")
        }.searchable(text: $viewModel.searchTextI, placement: .navigationBarDrawer, prompt: "Tap here to search")
    }
}

struct SearchContent: View {
    @Environment(\.isSearching) var isSearching
    @StateObject private var viewModel = HttpViewModel()
    @Binding var searchText: String
    
    var body: some View {
//        print(Self._printChanges()); return
        Group {
            if isSearching {
                if viewModel.isVendorsEmpty {
                    VStack {
                        Spacer()
                        Text("No result")
                            .foregroundColor(.secondary)
                        Spacer()
                        Spacer()
                    }
                } else {
                    List {
                        ForEach(viewModel.vendors) { vendor in
                            ForEach(vendor.kinds) { kind in
                                Section(kind.name) {
                                    ForEach(kind.products) { product in
                                        ProductView(product: product)
                                            .task {
                                                if viewModel.isLastProduct(product) && viewModel.hasMore {
                                                    print("load more")
                                                    await viewModel.search(for: searchText)
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
        }
        .animation(.default, value: viewModel.vendors)
        .onChange(of: searchText) { searchText in
            print("searchText changed as \(searchText)")
            viewModel.reset()
            if searchText.isEmpty {
                return
            }
            Task {
                await viewModel.search(for: searchText)
            }
        }
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
