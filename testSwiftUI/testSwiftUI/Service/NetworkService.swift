//
//  AppService.swift
//  testSwiftUI
//
//  Created by pchen on 2023/4/5.
//

import Foundation

enum FetchError: Error{
    case badUrlError
    case networkError
    case decodeModelError
}

class NetworkService<T: Decodable> {
    
    static func fetchAppData(_ urlString: String) async throws -> T {
        
        guard let url = URL(string: urlString) else {
            throw FetchError.badUrlError
        }
        
        guard let data = try? await URLSession.shared.data(from:url) else {
            throw FetchError.networkError
        }
        
        guard (data.1 as? HTTPURLResponse)?.statusCode == 200 else { throw FetchError.networkError }
        
        guard let resonseMode = try? JSONDecoder().decode(T.self, from: data.0) else {
            throw FetchError.decodeModelError
        }
        return resonseMode
    }
    
    static func fetchAppData(_ urlString: String, _ complete: @escaping (T?, Error?) -> Void) {
        
        guard let url = URL(string: urlString) else {
            complete(nil, FetchError.badUrlError)
            return
        }
        
        URLSession.shared.dataTask(with: URLRequest.init(url: url)) { data, resonse, error in
            
            guard (resonse as? HTTPURLResponse)?.statusCode == 200, let data = data else {
                complete(nil, FetchError.networkError)
                return
            }
            
            guard let resonseMode = try? JSONDecoder().decode(T.self, from: data ) else {
                complete(nil, FetchError.networkError)
                return
            }
            complete(resonseMode, nil)
        }.resume()
    }
}
