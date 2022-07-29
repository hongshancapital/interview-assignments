//
//  AppListViewModel.swift
//  Assignment
//
//  Created by shinolr on 2022/7/27.
//

import Foundation

class AppListViewModel: ObservableObject {

  @Published var items: [ListItem] = []
  @Published var isLoading = false
  private var page = 0
  var canLoadMore = true

  @MainActor
  func loadData(_ request: MockRequest = MockRequest.initialLoad) async {
    isLoading = true
    try? await Task.sleep(nanoseconds: 2_000_000_000)
    let result = await MockDataClient.shared.fetchResult(with: request)
    isLoading = false
    
    // simulating no more data
    guard request.parameter.offset < (try! result.get()).results.count else {
      canLoadMore = false
      return
    }
    
    switch result {
    case .success(let infoList):
      items = Array(infoList.results[..<min(request.parameter.offset, infoList.results.count)])
    case .failure(let error):
      handle(error)
    }
  }

  func refresh() async {
    page = 0
    canLoadMore = true
    items = []
    await loadData()
  }

  func loadMoreIfNeeded(currentItem item: ListItem?) async {
    guard let item else {
      await loadMore()
      return
    }
    
    if item == items.last {
      await loadMore()
    }
  }

  @MainActor
  private func loadMore() async {
    guard canLoadMore else {
      return
    }
    
    page += 1
    await loadData(MockRequest(parameter: .init(page: page)))
  }

  @MainActor
  func toggleFavorite(for item: ListItem) async {
    guard let index = items.firstIndex (where: { $0.id == item.id }) else {
      return
    }
    items[index].isFavorite.toggle()

    // persistence and request below
  }

  private func handle(_ error: GeneralError) {
    switch error {
    case .networkFailure(let int):
      _ = int
      break
    case .invalidResponse:
      break
    case .invalidData:
      break
    case .nestedError(let error):
      _ = error
      break
    }
  }
}
