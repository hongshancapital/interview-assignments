//
//  SearchView.swift
//  SearchDemo
//
//  Created by Mason Sun on 2020/9/8.
//

import SwiftUI

struct SearchView: View {
    @EnvironmentObject private var viewModel: SearchViewModel

    var body: some View {
        GeometryReader { geometry in
            VStack {
                VStack {
                    self.titleLabel
                    self.searchBar
                }
                .padding(.horizontal, 16)
                self.listView
                Spacer()
            }
            .padding(.top, geometry.safeAreaInsets.top + self.topMargin)
            .background(Color.gray.opacity(0.1))
            .edgesIgnoringSafeArea(.vertical)
        }
        .onAppear {
            UITableView.appearance().separatorStyle = .none
            UITableView.appearance().backgroundColor = UIColor.gray.withAlphaComponent(0.01)
        }
    }

    // MARK: Components

    private var titleLabel: some View {
        Group {
            if !viewModel.isSearching {
                HStack {
                    Text("Search")
                        .font(.title)
                        .fontWeight(.bold)
                    Spacer()
                }
            }
        }
    }

    private var searchBar: SearchBar {
        SearchBar(text: $viewModel.text, isEditing: $viewModel.isSearching) {
            self.viewModel.search()
        }
    }

    private var listView: some View {
        Group {
            if viewModel.isEmptyResult {
                Text("No result")
                    .frame(height: 200)
            } else {
                List {
                    ForEach(viewModel.result) { category in
                        Text(category.name)
                            .font(.headline)
                            .foregroundColor(.gray)
                            .listRowBackground(Color.gray.opacity(0.1))
                            .listRowInsets(EdgeInsets(top: 0, leading: 8, bottom: 0, trailing: 8))
                        ForEach(category.items) { item in
                            ItemRow(item: item)
                                .listRowInsets(EdgeInsets(top: 0, leading: 12, bottom: 0, trailing: 12))
                                .listRowBackground(Color.white)
                        }
                    }
                }
                .listStyle(PlainListStyle())
                .id(UUID())
            }
        }
    }

    // MARK: Accessors

    private var topMargin: CGFloat {
        viewModel.isSearching ? 8 : 32
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView()
            .environmentObject(SearchViewModel())
    }
}
