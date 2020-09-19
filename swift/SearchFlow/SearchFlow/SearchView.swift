//
//  SearchView.swift
//  SearchFlow
//
//  Created by evan on 2020/9/19.
//  Copyright © 2020 evan. All rights reserved.
//

import SwiftUI
import PagingScrollView

struct SearchView: View {
    @EnvironmentObject private var viewModel: SearchViewModel

    var body: some View {
        GeometryReader { geometry in
            VStack {
                VStack {
                    if !self.viewModel.isSearching {
                        HStack {
                            Text("Search").font(.title).fontWeight(.bold)
                            Spacer()
                        }
                    }
                    SearchBar(text: self.$viewModel.text, isEditing: self.$viewModel.isSearching, commitHandler: {
                        self.viewModel.isSearching = true
                    }, cancelHandler: {
                        self.viewModel.cancel()
                    })
                }
                .padding(.horizontal, Spacing.base)
                self.listView
                Spacer()
            }
            .padding(.top, geometry.safeAreaInsets.top + (self.viewModel.isSearching ? Spacing.tiny : Spacing.extraLarge))
            .background(Color.gray.opacity(0.1))
            .edgesIgnoringSafeArea(.vertical)
        }
    }

    private var listView: some View {
        Group {
            if viewModel.isEmptyResult {
                Text("No Result").frame(height: 300)
            } else {
                PagingScrollView(isRefreshing: $viewModel.isRefreshing, refreshingAction: {
                    self.viewModel.search()
                }) {
                    ForEach(self.viewModel.series) { serie in
                        VStack(alignment: .leading) {
                            Text(serie.name)
                            ForEach(serie.elements) { element in
                                ElementView(element: element)
                            }
                        }
                    }
                    .padding(.horizontal, Spacing.base)
                }
            }
        }
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView()
    }
}
