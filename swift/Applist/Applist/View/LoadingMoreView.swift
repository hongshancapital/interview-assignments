//
//  LoadingMoreView.swift
//  Applist
//
//  Created by santcool on 2023/1/29.
//

import Foundation
import SwiftUI

struct LoadingMoreView: View {
    @StateObject var viewModel: AppListViewModel

    var body: some View {
        HStack {
            Spacer()
            if viewModel.loadAll {
                Text("No more data.")
            } else if viewModel.isLoading {
                ProgressView()
                Text("Loading...")
                    .padding(.leading)
            }
            Spacer()
        }
        .foregroundColor(Color.gray)
        .frame(height: (viewModel.loadAll || viewModel.isLoading) ? 50 : 0)
        .onAppear {
            Task {
                if !viewModel.loadAll {
                    await viewModel.loadMore()
                }
            }
        }
    }
}

struct LoadingMore_Preview: PreviewProvider {
    static var previews: some View {
        LoadingMoreView(viewModel: AppListViewModel(page: 0))
    }
}
