//
//  ContentView.swift
//  AppDemo
//
//  Created by Jeffrey Wei on 2022/6/27.
//  列表视图
//

import SwiftUI
import Combine

struct DemoListView: View {
    @StateObject var store = ListViewStore()
    @State var isError = false
    var body: some View {
        let isEmpty = store.dataSource.count == 0
        NavigationView {
            switch isEmpty {
            case true:
                ProgressView().onAppear(perform: store.refresh).navigationTitle("App")
            case false:
                listView
            }
        }
                .onReceive(store.$currentError) {
                    guard $0 != nil else { return }
                    isError = true
                }
                .alert(isPresented: $isError) {
                    // 错误处理暂时简单的alert一下
                    Alert(title: Text(store.currentError?.localizedDescription ?? "暂无错误提示"))
                }
    }

    var listView: some View {
        List {
            ForEach(store.dataSource.indices, id: \.self) { index in
                DemoCell(model: store.dataSource[index]) {
                    store.doCollected(index: index)
                }
                        .listRowBackground(Color(.systemGray6))
                        .listRowSeparator(.hidden)
            }
            HStack(alignment: .center, spacing: 8) {
                if store.hasMore == true {
                    ProgressView().onAppear(perform: store.loadMore)
                }
                let loadingString = store.hasMore ? "Loading..." : "No more data"
                Text(loadingString).font(.body).foregroundColor(Color(.secondaryLabel))
            }
                    .frame(maxWidth: .infinity)
                    .listRowBackground(Color(.systemGray6))
                    .listRowSeparator(.hidden)
        }
                .listStyle(.plain)
                .navigationTitle("App")
                .background(Color(.systemGray6))
                .refreshable(action: store.refresh)
    }
}
