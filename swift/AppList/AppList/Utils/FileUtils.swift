//
//  FileUtils.swift
//  AppList
//
//  Created by 钟逊超 on 2022/8/1.
//

import Foundation

struct FileUtils {
    
    static func loadJson<T: Codable>(fileName: String) throws -> T {
        guard let filePath = Bundle.main.path(forResource: fileName, ofType: "geojson") else {
            fatalError("未能找到正确的json文件:\(fileName)")
        }
        
        let url = URL(fileURLWithPath: filePath)
        let data = try Data(contentsOf: url)
        let result = try JSONDecoder().decode(T.self, from: data)
        return result
    }
}
