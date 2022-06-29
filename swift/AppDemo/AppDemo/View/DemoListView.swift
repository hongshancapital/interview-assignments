//
//  ContentView.swift
//  AppDemo
//
//  Created by Jeffrey Wei on 2022/6/27.
//
//

import SwiftUI
import Combine

struct DemoListView: View {
    @ObservedObject var store = ListViewStore()
    var body: some View {
        let isEmpty = store.dataSource.count == 0
        NavigationView {
            switch isEmpty {
            case true:
                ProgressView().onAppear(perform: store.refresh).navigationTitle("App")
            case false:
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
                            ProgressView()
                                    .onAppear(perform: store.loadMore)
                        }
                        let loadingString = store.hasMore ? "Loading..." : "No more data"
                        Text(loadingString).font(.headline).foregroundColor(Color(.secondaryLabel))
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
    }
}
