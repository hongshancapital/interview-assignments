//
//  ProductList.swift
//  ProductHunt
//
//  Created by Jinya on 2022/10/6.
//

import SwiftUI

struct ProductList: View {
    @StateObject var viewModel: ProductListViewModel

    var body: some View {
        NavigationStack {
            Group {
                if viewModel.isInitialLoading {
                    ProgressView()
                        .progressViewStyle(.circular)
                } else {
                    if viewModel.products.isEmpty {
                        if let error = viewModel.error {
                            makeErrorView(error)
                        } else {
                            placeholder
                        }
                    } else {
                        productsContent
                    }
                }
            }
            .navigationTitle("App")
            .navigationBarTitleDisplayMode(.automatic)
            .task {
                await viewModel.loadInitialProducts()
            }
        }
    }

    private var productsContent: some View {
        List {
            ForEach(viewModel.products) { product in
                ProductRow(
                    product: Binding(
                        get: { product },
                        set: { _ in
                            Task(priority: .userInitiated) {
                                await viewModel.toggleLike(forProduct: product)
                            }
                        }
                    )
                )
                .listRowInsets(EdgeInsets(top: 6, leading: 0, bottom: 6, trailing: 0))
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
            }
            refreshFooter
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
        }
        .scrollContentBackground(.hidden)
        .background(Color(uiColor: .systemGroupedBackground))
        .refreshable {
            await viewModel.refreshProducts()
        }
    }

    private var refreshFooter: some View {
        HStack(spacing: 8) {
            Spacer()
            if viewModel.canLoadMore {
                ProgressView()
                    .progressViewStyle(.circular)
                Text("Loading...")
            } else {
                Text("No more data.")
            }
            Spacer()
        }
        .frame(height: 32)
        .foregroundColor(.secondary)
        .task {
            if viewModel.canLoadMore {
                await viewModel.loadMoreProducts()
            }
        }
    }

    private var placeholder: some View {
        VStack(spacing: 20) {
            HStack {
                Image(systemName: "bookmark")
                Text("No products, please retry...")
            }
            .font(.headline)
            .foregroundStyle(.secondary)
            retryButton
        }
    }

    private func makeErrorView(_ error: Error) -> some View {
        VStack {
            Text(error.localizedDescription)
                .foregroundStyle(.secondary)
            retryButton
        }
    }
    
    private var retryButton: some View {
        Button("Retry") {
            Task(priority: .userInitiated) {
                await viewModel.loadInitialProducts()
            }
        }
        .buttonStyle(.bordered)
        .buttonBorderShape(.capsule)
    }
}
