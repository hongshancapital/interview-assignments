//
//  MockKit.swift
//  DemoUITests
//
//  Created by Xiaoping Tang on 2023/4/9.
//

import Foundation

protocol MockProtocol: AnyObject {
    var bundle: Bundle { get }
    func loadJson<T: Decodable>(fileName: String, type: T.Type) -> T
}

extension MockProtocol {
    var bundle: Bundle {
        return Bundle(for: type(of: self))
    }
    
    func loadJson<T: Decodable>(fileName: String, type: T.Type) -> T {
        guard let path = bundle.url(forResource: fileName, withExtension: "json") else {
            fatalError("Load Json file failed")
        }
        
        do {
            let data = try Data(contentsOf: path)
            let decodedObject = try JSONDecoder().decode(type, from: data)
            
            return decodedObject
        } catch {
            fatalError("Decode Json failed")
        }
    }
}

