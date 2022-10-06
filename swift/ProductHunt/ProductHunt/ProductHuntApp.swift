//
//  ProductHuntApp.swift
//  ProductHunt
//
//  Created by Jinya on 2022/10/6.
//

import SwiftUI

@main
struct ProductHuntApp: App {
    var body: some Scene {
        WindowGroup {
            AppView()
        }
    }
}

private struct AppView: View {
    @StateObject private var viewModel = ProductListViewModel(
        capacityOfEachPage: 20,
        productProvider: ProductProvider.shared.products(forPage:capacity:)
    )
    
    var body: some View {
        ProductList(viewModel: viewModel)
    }
}
