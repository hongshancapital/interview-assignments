//
//  ContentView.swift
//  InterviewDemo
//
//  Created by Peng Shu on 2023/2/24.
//

import SwiftUI

/// 在DataSource中已实现2种方法来加载数据
/// 1. 使用Swift并发, 更直观
/// 2. 使用Combine框架
/// 下面是开关
let USING_CONCURRENCY = true

struct ContentView: View {
    @StateObject var dataSource = DataSource()
    
    var body: some View {
        ZStack {
            List {
                Section {
                    ForEach($dataSource.items) { $item in
                        Cell(item: $item)
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color.clear)
                            .onAppear {
                                if USING_CONCURRENCY {
                                    Task {
                                        await dataSource.loadNextUsingConcurrency(currentItem: item)
                                    }
                                } else {
                                    dataSource.loadNextUsingCombine(currentItem: item)
                                }
                            }
                    }
                    
                    // load more cell
                    if dataSource.items.count > 0 {
                        loadMoreCell
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color.clear)
                            .frame(maxWidth: .infinity, alignment: .center)
                    }
                }
            }
            .background(
                Color(.systemGroupedBackground)
            )
            .listStyle(.plain)
            .refreshable {
                dataSource.reset()
                if USING_CONCURRENCY {
                    await dataSource.loadNextUsingConcurrency(currentItem: nil)
                } else {
                    dataSource.loadNextUsingCombine(currentItem: nil)
                }
            }
            
            if dataSource.items.count == 0 {
                ProgressView()
            }
        }
        .navigationTitle("App")
        .task {
            if USING_CONCURRENCY {
                await dataSource.loadNextUsingConcurrency(currentItem: nil)
            } else {
                dataSource.loadNextUsingCombine(currentItem: nil)
            }
        }
    }
    
    var loadMoreCell: some View {
        Group {
            if dataSource.hasMoreData == false {
                Text("No more data.")
            } else if dataSource.isLoadingPage {
                HStack(spacing: 6) {
                    ProgressView()
                    Text("Loading...")
                }
            }
        }
        .foregroundColor(.secondary)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            ContentView()
        }
    }
}
