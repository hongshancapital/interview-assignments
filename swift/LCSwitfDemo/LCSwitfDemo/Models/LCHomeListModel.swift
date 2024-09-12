//
//  LCHomeListModel.swift
//  LCSwitfDemo
//
//  Created by 梁杰 on 2022/3/23.
//

import SwiftUI
import CoreLocation

struct LCHomeListModel: Hashable,Codable {
//    var id: Int
//    标题
    var trackName : String
//    描述
    var description : String
//    图片iconurl
    var artworkUrl512 : String
}


let listModels: [LCHomeListModel] = load("appData.json")

func load<T: Decodable>(_ filename: String) -> T {
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

