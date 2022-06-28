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
        NavigationView {
            List {
                ForEach(store.dataSource) {
                    DemoCell(model: $0)
                            .listRowBackground(Color(.systemGray6))
                            .listRowSeparator(.hidden)
                }
                Text("123")
            }
                    .listStyle(.plain)
                    .navigationTitle("App")
                    .background(Color(.systemGray6))
                    .refreshable(action: store.refresh)
        }
    }
}
