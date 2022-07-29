//
//  ListView.swift
//  Assignment
//
//  Created by shinolr on 2022/7/28.
//

import SwiftUI

struct ListView: View {
  
  @StateObject
  private var viewModel = AppListViewModel()
  
  var body: some View {
    NavigationStack {
      List {
        ForEach(viewModel.items) { item in
          ItemRow(item: item)
            .listRowSeparator(.hidden)
            .listRowBackground(Color.clear)
            .listRowInsets(EdgeInsets(top: 5, leading: 10, bottom: 5, trailing: 10))
            .environmentObject(viewModel)
            .task {
              await viewModel.loadMoreIfNeeded(currentItem: item)
            }
        }
        indicator
          .listRowBackground(Color.clear)
          .listRowSeparator(.hidden)
          .frame(maxWidth: .infinity, alignment: .center)
      }
      // please review code on Xcode 14.0 beta 3
      // after beta 4 `Color` is no longer an acceptable parameter
      .scrollContentBackground(Color(uiColor: .systemGroupedBackground))
      .listStyle(.plain)
      .task {
        await viewModel.loadData()
      }
      .refreshable {
        await viewModel.refresh()
      }
      .overlay {
        if viewModel.isLoading && viewModel.items.isEmpty {
          ProgressView()
            .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
      }
      .navigationTitle("App")
    }
  }
  
  var indicator: some View {
    Group {
      if viewModel.isLoading, !viewModel.items.isEmpty {
        HStack(spacing: 6) {
          ProgressView()
          Text("Loading...")
        }
      }
      if !viewModel.canLoadMore {
        Text("No more data.")
      }
    }
    .foregroundColor(.gray)
  }
}

struct ListView_Previews: PreviewProvider {
  static var previews: some View {
    ListView()
  }
}
