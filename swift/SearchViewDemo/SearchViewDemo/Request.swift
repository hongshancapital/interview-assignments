//
//  Request.swift
//  SearchDemo
//
//  Created by Mason Sun on 2020/9/9.
//

import Foundation

struct RequestResponse: Decodable {
    var categories: [Category]
}

enum RequestError: Error {
    case emptyPath
}

enum Request {
    static func getMockData(completion: @escaping (Result<[Category], Error>) -> Void) {
        DispatchQueue.global().async {
            guard let url = Bundle.main.url(forResource: "data", withExtension: "json") else { return completion(.failure(RequestError.emptyPath))}
            do {
                let data = try Data(contentsOf: url)
                let response = try JSONDecoder().decode(RequestResponse.self, from: data)
                completion(.success(response.categories))
            } catch {
                completion(.failure(error))
            }
        }
    }
}
