//
//  PreviewMock.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/23.
//

import Foundation

class MockData {
  static var page1: ChatAppList = load("page1.json")
  static var page2: ChatAppList = load("page2.json")
  
  static func emptyPage(page: Int) -> ChatAppList {
    ChatAppList(totalCount: 20, page: page, results: [])
  }
  
  static func favoriteApp(appId: UInt, isFavorite: Bool) {
    if let index = MockData.page1.results.firstIndex(where: { $0.trackId == appId }) {
      MockData.page1.results[index].isFavorite = isFavorite
    } else if let index = MockData.page2.results.firstIndex(where: { $0.trackId == appId }) {
      MockData.page2.results[index].isFavorite = isFavorite
    }
  }
}

func load<T: Decodable>(_ filename: String) -> T {
  let data: Data

  guard let file = Bundle.main.url(forResource: filename, withExtension: nil) else {
    fatalError("Couldn't find \(filename) in main bundle.")
  }

  do {
    data = try Data(contentsOf: file)
  } catch {
    fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
  }

  do {
    let decoder = JSONDecoder()
    return try decoder.decode(T.self, from: data)
  } catch {
    fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
  }
}
