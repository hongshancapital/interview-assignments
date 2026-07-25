//
//  HSResultModel.swift
//  HSSearchList
//
//  Created by dongxia zhu on 2021/10/8.
//

import Foundation
import Combine
import SwiftUI

let backgroundColor = Color(red: 250.0 / 255, green: 250.0 / 255, blue: 250.0 / 255)
let titleColor = Color(red: 153.0 / 255, green: 153.0 / 255, blue: 153.0 / 255)


enum Category: String, CaseIterable, Codable {
    case vacuum = "Vacuum"
    case hairDryer = "Hair Dryer"
}

struct HSResultItemModel: Hashable, Codable, Identifiable {
    var id: Int
    var name: String
    var price: String
    var desc: String
    var brand: String
    var stock: Int
    var isInStock: Bool {
        get {
            return stock > 0
        }
    }
    var category: Category
    
}

struct HSResultModel: Hashable, Codable, Identifiable {
    var id: Int
    var category: Category
    var brand: String
    var itemData: [HSResultItemModel]
}


final class ResultData: ObservableObject {
    @Published var results: [HSResultModel] = []
    
    func requestSearchResult(_ text: String) -> [HSResultModel] {
        self.results = load(text)
        return self.results.filter{$0.brand == text}
    }
}

func load<T: Decodable>(_ searchText: String) -> T {
    let filename = "resultData.json"
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
