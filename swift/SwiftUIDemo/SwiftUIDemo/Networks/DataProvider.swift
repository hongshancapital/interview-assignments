//
//  Networking.swift
//  SwiftUIDemo
//
//  Created by Machao on 2022/4/15.
//

import Foundation

protocol DataProvider {
    func request<T: Codable>(url: URL, completed: @escaping (T)->())
}

class NetworkingProvider: DataProvider {
    
    func request<T: Codable>(url: URL, completed: @escaping (T)->()) {
    }
}

class FileDataProvider: DataProvider {
    func request<T: Codable>(url: URL, completed: @escaping (T)->()) {
        DispatchQueue.global().asyncAfter(deadline: .now() + 2) {[self] in
            do {
                let data = try mockData ?? Data(contentsOf: url)
                let results: ResultModel<T> = try decode(data)
                completed(results.results)
                
            } catch  {
                debugPrint("序列化数据失败: \(error.localizedDescription)  url: \(url.absoluteString)")
            }
        }
    }
    
    func decode<T>(_ data: Data) throws -> T where T: Codable {
        let decoder = JSONDecoder()
        let model = try decoder.decode(T.self, from: data)
        return model
    }
}


