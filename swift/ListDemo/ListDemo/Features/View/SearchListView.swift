//
//  SearchListView.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import SwiftUI
import os.log

struct SearchListView: View {
    @StateObject private var viewModel = SearchListViewModel(networking: SearchAppAPI())
    @State private var selection: Int?
    @State private var noMore: Bool = false
    @State private var refreshID = false
    
    var body: some View {
        NavigationView {
            List {
                Section(content: {
                    ForEach(viewModel.items, id: \.trackId) { item in
                        AppItemCell(item: item, viewModel: viewModel)
                            .listRowSeparator(.hidden)
                            .listRowInsets(EdgeInsets(top: 5, leading: 8, bottom: 5, trailing: 8))
                            .listRowBackground(Color.clear)
                    }
                }, footer: {
                    HStack {
                        Spacer()
                        if viewModel.listFull {
                            Text("No more data.")
                        } else if case SearchListViewModel.PageState.loadingMore = viewModel.state {
                            ProgressView().id(refreshID)
                            Text("Loading...")
                                .padding(.leading)
                        }
                        Spacer()
                    }.onAppear {
                        // 利用 re-create 机制，每次 footer 显示时，重建 ProgressView
                        withAnimation {
                            refreshID.toggle()
                        }
                        if !viewModel.items.isEmpty && !viewModel.listFull {
                            Logger.ui.debug("loadMoreAppItems")
                            viewModel.loadMoreAppItems()
                        }
                    }
                })
            }
            .overlay(Group {
                if viewModel.items.count == 0 {
                    if case SearchListViewModel.PageState.failed(let error) = viewModel.state {
                        VStack {
                            Button {
                                Logger.ui.info ("Click Try Agian")
                                viewModel.refreshAppItems()
                            } label: {
                                Text("Try Agian")
                            }
                            ErrorView(error: error)
                        }
                    } else {
                        ProgressView()
                    }
                } else {
                    EmptyView()
                }
            })
            .listStyle(.grouped)
            .task {
                viewModel.refreshAppItems()
            }
            .refreshable {
                Logger.ui.debug("refreshAppItems")
                viewModel.refreshAppItems()
            }
            .navigationTitle("App")
        }
    }
}

