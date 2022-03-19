//
//  OTNetwork.swift
//  OTAppStore
//
//  Created by liuxj on 2022/3/18.
//

import Foundation

typealias OTNetworkParams = [String: Any]

enum OTNetworkError: Error {
    case invaildURL
    case badResponse
    case parseError
}

class OTNetwork {
    static let shared = OTNetwork()
    let decoder = JSONDecoder()
    
    func getData<T: Decodable> (from path: String, params: OTNetworkParams) async throws -> T {
        guard let url = url(from: path, params: params) else {
            throw OTNetworkError.invaildURL
        }

        var request = URLRequest(url: url,
                                 cachePolicy: .returnCacheDataElseLoad,
                                 timeoutInterval: 30.0)
        request.httpMethod = "GET"
        
        let (data, _) = try await URLSession.shared.data(for: request)
        do {
            return try decoder.decode(T.self, from:data)
        } catch {
            throw OTNetworkError.parseError
        }
    }
    
    private func url(from path: String, params: OTNetworkParams) -> URL? {
        guard var urlComonents = URLComponents(string: path) else {
            return nil
        }
        
        var queryItems = [URLQueryItem]()
        for item in params {
            queryItems.append(URLQueryItem(name: item.key, value: "\(item.value)"))
        }
        urlComonents.queryItems = queryItems
        return urlComonents.url
    }
}
