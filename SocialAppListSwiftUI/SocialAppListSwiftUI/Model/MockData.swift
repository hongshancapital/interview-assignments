//
//  MockData.swift
//  SocialAppListSwiftUI
//
//  Created by luobin on 2022/3/27.
//

import UIKit

struct MockData {
    static func getData() -> SocialAppListResultModel {
        let model: SocialAppListResultModel = load("getSocialAppList.json")
        return model
    }
    
    private static func load<T: Decodable>(_ filename: String) -> T {
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
}
