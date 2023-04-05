//
//  AppsListPage.swift
//  AppsList
//
//  Created by 贺建华 on 2023/4/2.
//

import SwiftUI

struct AppsListPage: View {
    
    @StateObject private var viewModel = AppsListViewModel()
    @State private var headerState: LoadingState = .loading
    @State private var footerState: LoadingState = .success
        
    var body: some View {
        NavigationView {
            ZStack {
                if viewModel.items.isEmpty {
                    VStack {
                        LoadingStateView(state: $headerState, emptyText: "No data.")
                            .frame(height: 400)
                            .frame(maxWidth: .infinity)
                            .onAppear {
                                Task {
                                    await viewModel.requestAppItems(more: false)
                                    headerState = viewModel.state
                                }
                            }
                            .onTapGesture {
                                if headerState == .failure {
                                    headerState = .loading
                                    Task {
                                        await viewModel.requestAppItems(more: false)
                                        headerState = viewModel.state
                                    }
                                }
                            }
                        Spacer()
                    }
                } else {
                    List(0..<viewModel.items.count, id: \.self) { i in
                        AppItemRow(model: viewModel.items[i])
                            .frame(height: 70)
                        
                        if i == viewModel.items.count - 1 {
                            LoadingStateView(state: $footerState, loadingText: "Loading...")
                                .listRowSeparator(.hidden)
                                .listRowBackground(EmptyView().background(.clear))
                                .frame(maxWidth: .infinity, alignment: .center)
                                .onAppear {
                                    print("[Log] [Page] footer appear.")
                                    if viewModel.hasMore, footerState != .loading {
                                        footerState = .loading
                                        Task {
                                            await viewModel.requestAppItems(more: true)
                                            footerState = viewModel.state
                                        }
                                    }
                                }
                        }
                    }
                    .listStyle(.plain)
                    .refreshable {
                        await viewModel.requestAppItems(more: false)
                    }
                }
            }
            .navigationTitle("App")
            .background(Const.backgroundColor)
        }
    }
}

struct AppsListPage_Previews: PreviewProvider {
    static var previews: some View {
        AppsListPage()
    }
}
