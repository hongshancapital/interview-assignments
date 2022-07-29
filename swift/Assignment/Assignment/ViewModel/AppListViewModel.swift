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
  @Published var isLoadingMore = false
  @Published var canLoadMore = true
  private var page = 0

  init() {
    Task {
      await loadData()
    }
  }

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
      var items = Array(infoList.results[..<min(request.parameter.offset, infoList.results.count)])
      fakePreprocessFavorites(with: &items)
    case .failure(let error):
      handle(error)
    }
  }

  @MainActor
  func refresh() async {
    page = 0
    canLoadMore = true
    await loadData()
  }

  func loadMoreIfNeeded(currentItem item: ListItem?) async {
    guard let item = item else {
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
    isLoadingMore = true
    await loadData(MockRequest(parameter: .init(page: page)))
    isLoadingMore = false
  }

  @MainActor
  func toggleFavorite(for item: ListItem) async {
    guard let index = items.firstIndex (where: { $0.id == item.id }) else {
      return
    }
    items[index].isFavorite.toggle()

    // fake favorites
    await fakeStoreFavorite(with: item.id)
  }

  private func fakeStoreFavorite(with id: Int) async {
    try? await Task.sleep(nanoseconds: 2_000_000_000)
    var currentFavoriteIDs = UserDefaults.standard.object(forKey: "MockFavorite") as? [Int] ?? []
    if let index = currentFavoriteIDs.firstIndex(of: id) {
      currentFavoriteIDs.remove(at: index)
    } else {
      currentFavoriteIDs.append(id)
    }
    UserDefaults.standard.set(currentFavoriteIDs, forKey: "MockFavorite")
  }

  private func fakePreprocessFavorites(with items: inout [ListItem]) {
    defer { self.items = items }
    
    guard let currentFavoriteIDs = UserDefaults.standard.object(forKey: "MockFavorite") as? [Int] else {
      return
    }

    for id in currentFavoriteIDs {
      guard let index = items.firstIndex(where: { $0.id == id }) else {
        continue
      }
      items[index].isFavorite = true
    }
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
