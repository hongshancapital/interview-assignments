//
//  DataProvider.swift
//  SwiftUIDemo
//
//  Created by chenghao on 2022/5/11.
//

import Foundation

protocol DataProvider {
    func request<T: Codable>(url:URL,completed:@escaping (T)->())
}

class NetworkingProvider: DataProvider {
    func request<T: Codable>(url: URL, completed: @escaping (T) -> ()) {
        
    }
}

class FileDataProvider: DataProvider {
    func request<T: Codable>(url: URL, completed: @escaping (T) -> ()) {
        DispatchQueue.global().asyncAfter(deadline: .now() + 2) {
            do {
                let data = try Data(contentsOf: url)
                let results: ResultModel<T> = try JSONDecoder().decode(ResultModel.self, from: data)
                completed(results.results)
            } catch {
                debugPrint("序列化数据失败: \(error.localizedDescription)  url: \(url.absoluteString)")
            }
        }
    }
}
