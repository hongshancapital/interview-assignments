//
//  ContentView.swift
//  Store
//
//  Created by 张欣宇 on 2022/11/1.
//

import SwiftUI
import UIKit

fileprivate enum Constants {
    enum Layout {
        static let itemSpacing: CGFloat = 16
        static let listPandding: CGFloat = 20
        static let itemCornerRadius: CGFloat = 10
    }
    
    enum Name {
        static let app = "APP"
    }
}

struct StoreList: View {
    
    @ObservedObject var viewModel: StoreListViewModel
    
    var body: some View {
        NavigationView {
            content
                .background(Color(UIColor.tertiarySystemGroupedBackground))
                .navigationTitle(Constants.Name.app)
        }
    }
    
    var content: some View {
        Group {
            if viewModel.loadFinished {
                list
            } else {
                ProgressView()
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
    
    var list: some View {
        ScrollView {
            LazyVGrid(
                columns: [GridItem(.flexible())],
                spacing: Constants.Layout.itemSpacing
            ) {
                ForEach(viewModel.apps, id: \.trackId) { app in
                    StoreItem(viewModel: viewModel, app: app)
                        .background(Color(.systemBackground))
                        .cornerRadius(Constants.Layout.itemCornerRadius)
                }
                refreshFooter
            }
            .padding(Constants.Layout.listPandding)
        }
        .refreshable {
            try? await viewModel.refreshData()
        }
    }
    
    var refreshFooter: some View {
        RefreshFooter(state: viewModel.refreshState)
            .foregroundColor(Color(.systemGray))
            .onAppear {
                viewModel.loadMoreData()
            }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        StoreList(viewModel: StoreListViewModel())
    }
}
