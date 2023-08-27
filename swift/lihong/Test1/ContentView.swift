//
//  ContentView.swift
//  Test1
//
//  Created by Stephen Li on 2022/10/18.
//

import SwiftUI

struct ContentView: View {
    // Maximum number of apps to get in one page
    private let pageCount = 10

    // Loaded page index
    @State private var currentPage = 1

    // Flag to show whether pull-to-refresh is loading or not
    @State private var headerRefreshing: Bool = false
    
    // Flag to show whether load-more is loading or not
    @State private var footerRefreshing: Bool = false
    
    // Flag to mark there is no more data from the backend
    @State private var flagNoMore: Bool = false
    
    // Flag to control when to kick off load-more
    @State private var flagTriggerLoadMore: Bool = false

    // Flag to show the list is empty, no single app data from backend
    @State private var flagEmptyList: Bool = false

    // View models loaded to show on screen
    @State private var items: [AppItemViewModel] = []

    init() {
#if DEBUG
        AppManager.shared.configBackend(MockServer("1", "txt", 2.0, 2.0, 20))
#else
        // MARK: Use release backend server instance in production
#endif
    }
    
    var body: some View {
        NavigationView {
            List {
                ForEach(items) { item in
                    AppItemCellView(itemVM: item)
                        .frame(height: 80)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color(.systemGroupedBackground))
                }

                // Setup load-more footer
                if items.count > 0 {
                    FooterView(refreshing: $footerRefreshing, flagNoMore: $flagNoMore, flagTrigger: $flagTriggerLoadMore) {
                        // Action to trigger
                        loadMore()
                    }
                    .onAppear {
                        // Allow to trigger load more if this view appears
                        flagTriggerLoadMore = true
                    }
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color(.systemGroupedBackground))
                    .frame(height: 30)
                }
            }
            .listRowBackground(Color(.systemGroupedBackground))
            .background(Color(.systemGroupedBackground))
            .scrollContentBackground(.hidden)
            .listStyle(.plain)
            .navigationTitle("App")
            .refreshable {
                // pull-to-refresh action
                reload()
            }
            .overlay(Group {
                // Initial indicator when content is loading from backend the first time
                if items.count == 0 {
                    if flagEmptyList {
                        EmptyView()  // Do empty, no more data from backend after reload()
                    } else {
                        ProgressView()  // Loading for the first time
                    }
                }
            })
            .onAppear {
                // Trigger to reload immediately
                reload()
            }
        }
    }

    ///
    /// Actions
    ///
    
    private func reload() {
        // Return immediately if last pull-to-refresh is not done yet
        guard !headerRefreshing else { return }

        // Reset every state, esp. for footer state, be careful
        flagNoMore = false
        headerRefreshing = true
        footerRefreshing = false
        flagTriggerLoadMore = false
        flagEmptyList = false

        // Reset current page index to first one
        currentPage = 1

        Task {
            let temp = await AppManager.shared.getApps(currentPage, pageCount)

            if temp.count == 0 {
                flagEmptyList = true
            } else {
                items = temp
            }

            headerRefreshing = false
        }
    }

    private func loadMore() {
        self.footerRefreshing = true

        // Advance to next page
        currentPage += 1

        Task {
            let moreItems = await AppManager.shared.getApps(currentPage, pageCount)

            // In case reload() happens before loadMore() completed, drop the response then
            if footerRefreshing {
                items += moreItems

                // If true, it means there is not enough items for one whole page, reach the end
                flagNoMore = (moreItems.count < pageCount)

                footerRefreshing = false
                flagTriggerLoadMore = false
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
