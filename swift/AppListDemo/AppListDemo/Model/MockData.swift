//
//  MockData.swift
//  AppListDemo
//
//  Created by arthur on 2022/10/22.
//

import Foundation

struct MockData {
    
    enum Errors: Error, CustomStringConvertible {
        case fileNotExist
        case decodeFailed(Error)
        
        var description: String {
            switch self {
            case .fileNotExist:
                return "json file not exist"
            case .decodeFailed(let err):
                return "json data decode failed: \(err)"
            }
        }
    }
    
    static func list() throws -> [AppInfo] {
        guard let url = Bundle.main.url(forResource: "mock", withExtension: "json") else {
            throw Errors.fileNotExist
        }
        do {
            let data = try Data(contentsOf: url)
            let resp = try JSONDecoder().decode(AppListResponse.self, from: data)
            return resp.results
        } catch let error {
            print(error)
            throw Errors.decodeFailed(error)
        }
    }
}


