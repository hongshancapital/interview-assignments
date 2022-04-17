//
//  ContentView.swift
//  SwiftUIHW
//
//  Created by 施治昂 on 4/14/22.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject private var viewModel = AppListViewModel()
    
    var body: some View {
        NavigationView {
            AppListView(apps: viewModel.apps, refresh: {
                Task {
                    try await viewModel.refresh()
                }
            }, loadMore: {
                Task {
                    try await viewModel.loadMore()
                }
            }, noMoreData: viewModel.noMoreData)
            .navigationTitle("App")
        }
        .onAppear {
            Task {
                try? await viewModel.refresh()
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
