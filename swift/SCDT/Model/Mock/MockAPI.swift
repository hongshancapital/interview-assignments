//
//  MockAPI.swift
//  scdt-chn
//
//  Created by Zhao Sam on 2022/8/5.
//

import Foundation

enum APIError: Error {
    case runtimeError(String)
}

struct MockAPI {
    fileprivate static let APPLICATIONFILE = "applications.json"
    fileprivate static var mockData = [String: Any]()
    fileprivate static func readJSON<T: Decodable>(_ filename: String, as type: T.Type = T.self) -> T {
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
    
    public static func getMockApplicationList(pagination: Pagination) async throws -> [ApplicationModel]? {
        if pagination.size < 0 {
            throw APIError.runtimeError("Pagination size must not be less than 0")
        }
        // Cache
        var mock = mockData[APPLICATIONFILE] as? MockModel
        if mock == nil {
            mock = readJSON(APPLICATIONFILE)
            if mock == nil {
                return []
            }
            mockData[APPLICATIONFILE] = mock
        }
        pagination.hasMore = pagination.offset < mock!.resultCount
        // Mock http request delay
        do {
            sleep(1)
        }
        return Array(mock!.results[pagination.offset ..< min(mock!.resultCount, pagination.offset + pagination.size)])
    }
}
