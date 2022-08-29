//
//  AppListFile.swift
//  AppList
//
//  Created by 大洋 on 2022/8/23.
//

import Foundation
import UIKit

enum LoadError: Error {
    case noFile
    case loadError
}

enum AppListFile {
    
    static func loadJSON<T: Decodable>(filename: String) async throws -> T {
        let data: Data
        guard let file = Bundle.main.url(forResource: filename, withExtension: nil) else {
            throw LoadError.noFile
        }

        do {
            data = try Data(contentsOf: file)
        } catch {
            throw LoadError.loadError
        }
        
        do {
            let decoder = JSONDecoder()
            return try decoder.decode(T.self, from: data)
        } catch {
            throw LoadError.loadError
        }
    }
}
