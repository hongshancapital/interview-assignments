//
//  Data.swift
//  mahefei-swift-answer
//
//  Created by Mars on 2023/2/2.
//

import UIKit
import SwiftUI
import Combine


var appData:[DataItem] = load("AppData.json")


func load<T:Decodable>(_ fileName:String) -> T {
    let data: Data
    
    guard let file = Bundle.main.url(forResource: fileName, withExtension: nil) else {
        fatalError("Couldn't find \(fileName) in main bundle.")
    }
    
    do {
        data = try Data(contentsOf: file)
    } catch {
        fatalError("Couldn't load \(fileName) from main bundle:\n\(error)")
    }
    
    do {
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    } catch {
       fatalError("Couldn't parse \(fileName) as \(T.self):\n\(error)")
    }
}

