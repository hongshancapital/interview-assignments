//
//  DataService.swift
//  SwiftHomeWork
//
//  Created by apple on 2022/4/12.
//

import Foundation

class NetService{
    static let url = "https://itunes.apple.com/search?entity=software&limit=50&term=chat"
    
    enum Err:Error{
        case invalidURL
    }
    
    func getData() async throws -> Welcome {
        guard let url = URL.init(string: Self.url) else {
            throw Err.invalidURL
        }
        let (data,_) = try await URLSession.shared.data(for: URLRequest.init(url: url), delegate: nil)
        return try JSONDecoder().decode(Welcome.self, from: data)
    }
    
}
