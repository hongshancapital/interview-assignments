//
//  DataManager.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI

enum NetworkError: Error {
    case badId
    case badPageSize
    case badPageIndex
    case systemError
}

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

final class NetWorkManager {
    static let shared = NetWorkManager()
    var mockBackendApps: [AppModel] = appResultData.results
    
    func fetchApps(_ pageSize: Int = 10, _ pageIndex: Int = 0) async throws -> [AppModel] {
        guard pageSize > 0 else {
            throw NetworkError.badPageSize
        }
        let remainder = mockBackendApps.count % pageSize
        let pageCount = (mockBackendApps.count / pageSize) + (remainder > 0 ? 1 : 0)
        guard pageIndex >= 0 && pageIndex < pageCount else {
            throw NetworkError.badPageIndex
        }
        let start = pageIndex * pageSize
        let offset = pageIndex == (pageCount - 1) ? remainder : pageSize
        do {
            try await Task.sleep(nanoseconds: 1 * 1_000_000_000)
            return Array(mockBackendApps[start..<(start + offset)])
        } catch {
            throw NetworkError.systemError
        }
    }
    
    @discardableResult func favoriteRequest(_ app: AppModel, _ isFavorite: Bool) async throws -> AppModel {
        guard let index = mockBackendApps.firstIndex(where: {$0.id == app.id}) else {
            throw NetworkError.badId
        }
        mockBackendApps[index].isFavorite = isFavorite
        return mockBackendApps[index]
    }
}

enum WebImageError: Error {
    case badUrl
    case badImage
}

final class ImageLoader {
    static let shared = ImageLoader()
    
    func loadImage(url: String) async throws -> UIImage {
        let request = URLRequest(url: URL(string: url)!)
        let (url, resp) = try await URLSession.shared.download(for: request)
        guard (resp as? HTTPURLResponse)?.statusCode == 200 else {
            throw WebImageError.badUrl
        }
        guard let image = UIImage(contentsOfFile: url.path) else {
            throw WebImageError.badImage
        }
        return image
    }
}

extension Color {
    public static let bgColor: Color = Color("bgColor", bundle: nil)
}
