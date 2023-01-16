//
//  ModelData.swift
//  Demo
//
//  Created by 石超 on 2022/6/22.
//

import Foundation
import SwiftUI

final class ModelData: ObservableObject {
    /// true 刷新数据； false 加载数据
    @Published var isRefreshing: Bool = false
    @Published var isLoading: Bool = false
    @Published var isNoMoreData: Bool = false
    @Published var apps: [AppModel] = []
    
    var favoriteApps: [Int] = []
    let results: Results? = load("appsData.json")
    
    let pageCount: Int = 25
    var pageIndex: Int = 1
    
    func updateApps() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            self.apps = self.getApps()
        }
    }
    
    func getApps() -> [AppModel] {
        
        guard self.isRefreshing || !self.isNoMoreData else { return apps}
        guard let items = self.results?.results else { return apps}
        
        var startIndex = 0
        var endIndex = 0
        
        if self.isRefreshing {
            // 刷新页面
            self.apps.removeAll()
            pageIndex = 1
            
            startIndex = 0
            endIndex = self.pageCount
        } else {
            // 加载更多
            self.pageIndex += 1
            startIndex = self.apps.count
            
            if items.count > self.pageCount*self.pageIndex {
                endIndex = self.pageCount + startIndex
            } else {
                endIndex = items.count - self.pageCount * (self.pageIndex - 1) + startIndex
                self.isNoMoreData = true
            }
        }
        
        guard endIndex <= items.count else { return apps}

        for idx in startIndex..<endIndex {
            var app = AppModel()
            app.id = idx
            app.identifer = items[idx].trackId
            app.name = items[idx].trackName
            app.description = items[idx].description
            app.imageUrlStr = items[idx].artworkUrl60
        
            if favoriteApps.contains(app.identifer) {
                app.isFavorite = true
            }
            
            apps.append(app)
        }
        
        if self.isRefreshing {
            self.isRefreshing = false
        } else {
            self.isLoading = false
        }
        
        return apps
    }
}

func load<T: Decodable> (_ fileName: String) -> T {
    let data: Data

    guard let fileUlr = Bundle.main.url(forResource: fileName, withExtension: nil)
    else {
        fatalError("Couldn't find \(fileName) in main bundle.")
    }

    do {
        data = try Data(contentsOf: fileUlr)
    } catch {
        fatalError("Couldn't load \(fileName) from main bundle:\n\(error)")
    }

    do {
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("Couldn't parse \(fileName) as \(T.self):\n\(error)")
    }
}

