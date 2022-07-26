//
//  ChatAppListView.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/23.
//

import SwiftUI

struct ChatAppListView: View {
  @StateObject var viewModel: ChatAppListViewModel
  
  init(provider: Api) {
    _viewModel = StateObject(wrappedValue: ChatAppListViewModel(provider: provider))
  }
  
  fileprivate init(viewModelForPreview: ChatAppListViewModel) {
    _viewModel = StateObject(wrappedValue: viewModelForPreview)
  }
  
  var body: some View {
    NavigationView {
      list()
    }
    // Navigation constraints issue
    // https://stackoverflow.com/a/66299785/4968633
    .navigationViewStyle(.stack)
  }
  
  private func list() -> some View {
    List {
      ForEach(viewModel.apps) {app in
        ChatAppRow(app: app) {
          Task {
            await viewModel.favoriteApp(app.trackId, isFavorite: !app.isFavorite)
          }
        }
          .listRowBackground(EmptyView())
          .listRowInsets(EdgeInsets(top: 6, leading: 0, bottom: 6, trailing: 0))
          .listRowSeparator(.hidden)
      }
      if !viewModel.apps.isEmpty {
        LoadMoreView(hasMore: viewModel.hasMore)
          .task {
            await viewModel.loadMore()
          }
          .listRowBackground(EmptyView())
      }
    }
    .task {
      await viewModel.refresh()
    }
    .refreshable {
      await viewModel.refresh()
    }
    .overlay(alignment: .center) {
      overlayView()
    }
    .navigationTitle("App")
  }
  
  @ViewBuilder
  private func overlayView() -> some View {
    switch viewModel.state {
    case .empty:
      Text("No content")
    case .error:
      if viewModel.apps.isEmpty {
        Button {
          Task {
            await viewModel.refresh()
          }
        } label: {
          Text("Retry")
        }
      }
    case .loading:
      if viewModel.apps.isEmpty {
        ProgressView()
      }
    case .none:
      EmptyView()
    }
  }
}

struct ChatAppListView_Previews: PreviewProvider {
  static let provider = NetworkApi()
  
  static var previews: some View {
    let viewModel = ChatAppListViewModel(forPreiview: provider, apps: MockData.page1.results, state: .none)
    return ChatAppListView(viewModelForPreview: viewModel)
  }
}
