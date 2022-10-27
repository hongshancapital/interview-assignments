//
//  DataManager.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI

let appResultData: AppResultData = load("appsData.json")

func load<T: Decodable>(_ filename: String) -> T {
    let data: Data
    
    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
        else {
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

final class DataManager {
    static let shared = DataManager()
    var appModels: [AppModel] = appResultData.results

    func fetchApps(_ pageSize: Int = 10, _ pageIndex: Int = 0) async -> [AppModel] {
        guard appModels.count > 0, pageSize > 0 else {
            return []
        }
        let remainder = appModels.count % pageSize
        let pageCount = (appModels.count / pageSize) + (remainder > 0 ? 1 : 0)
        guard pageIndex >= 0 && pageIndex < pageCount else {
            return []
        }
        let start = pageIndex * pageSize
        let offset = pageIndex == (pageCount - 1) ? remainder : pageSize
        do {
            try await Task.sleep(nanoseconds: 1 * 1_000_000_000)
            return Array(appModels[start..<(start + offset)])
        } catch {
            return []
        }
    }
    
    func favoriteApp(_ app: AppModel, _ isFavorite: Bool) -> AppModel {
        guard let index = appModels.firstIndex(where: {$0.id == app.id}) else {
            return app
        }
        appModels[index].isFavorite = isFavorite
        return appModels[index]
    }
}

final class ImageLoader {
    static let shared = ImageLoader()
    
    func loadImage(url: String) async -> UIImage {
        guard let taskUrl = URL(string: url) else {
            return UIImage()
        }
        do {
            let request = URLRequest(url: taskUrl)
            let resp = try await URLSession.shared.download(for: request)
            return UIImage(contentsOfFile: resp.0.path) ?? UIImage()
        } catch {
            return UIImage()
        }
    }
}

extension Color {
    public static let bgColor: Color = Color("bgColor", bundle: nil)
}
