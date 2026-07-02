//
//  DataManager.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI

enum NetworkError: Error {
    case networkError
    
    var description: String {
        switch self {
        case .networkError:
            return "Network error."
        }
    }
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
    
    @discardableResult func fetchApps(_ pageSize: Int = 10, _ pageIndex: Int = 0) async throws -> [AppModel] {
        guard pageSize > 0 else {
            throw NetworkError.networkError
        }
        let remainder = mockBackendApps.count % pageSize
        let pageCount = (mockBackendApps.count / pageSize) + (remainder > 0 ? 1 : 0)
        guard pageIndex >= 0 && pageIndex < pageCount else {
            throw NetworkError.networkError
        }
        let start = pageIndex * pageSize
        let offset = pageIndex == (pageCount - 1) ? remainder : pageSize
        do {
            try await Task.sleep(nanoseconds: 1 * 1_000_000_000)
            return Array(mockBackendApps[start..<(start + offset)])
        } catch {
            throw NetworkError.networkError
        }
    }
    
    @discardableResult func favoriteRequest(_ appId: Int, _ isFavorite: Bool) async throws -> AppModel {
        guard let index = mockBackendApps.firstIndex(where: {$0.id == appId}) else {
            throw NetworkError.networkError
        }
        mockBackendApps[index].isFavorite = isFavorite
        return mockBackendApps[index]
    }

}

extension Color {
    public static let bgColor: Color = Color("bgColor", bundle: nil)
}
