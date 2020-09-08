//
//  SearchView.swift
//  SearchDemo
//
//  Created by Mason Sun on 2020/9/8.
//

import SwiftUI

struct SearchView: View {
    @EnvironmentObject private var viewModel: SearchViewModel
    @State private var text: String = ""

    var body: some View {
        SearchNavigationView(
            text: $text,
            placeholder: "Tap here to search",
            search: search,
            cancel: cancel) {
            contentView
            .navigationBarTitle("Search")
        }
        .edgesIgnoringSafeArea(.top)
    }

    private var contentView: some View {
        Group {
            if viewModel.isSearching && viewModel.result.isEmpty {
                Text("No result")
            } else {
                List {
                    ForEach(viewModel.result) { category in
                        Section(header: Text(category.name)) {
                            ForEach(category.items) { item in
                                ItemRow(item: item)
                            }
                        }
                    }
                }
                .listStyle(GroupedListStyle())
                // Fix animation glitch
                .id(UUID())
            }
        }
    }

    private func search() {
        viewModel.search(with: text)
    }

    private func cancel() {
        text = ""
        viewModel.cancel()
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView()
            .environmentObject(SearchViewModel())
    }
}
