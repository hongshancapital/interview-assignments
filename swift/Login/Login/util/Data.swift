//
//  data.swift
//  Login
//
//  Created by xiwang wang on 2021/9/1.
//

import SwiftUI


let loginData: Login = load("login.json")
let registerData: Register = load("register.json")
func load<T: Decodable>(_ fileName: String) -> T {
    let data: Data
    
    guard let file = Bundle.main.path(forResource: fileName, ofType: "nil") else {
        fatalError("couldn't find \(fileName) in main bundle")
    }
    
    do {
        data = try Data(contentsOf: URL(fileURLWithPath: file))
    } catch {
        fatalError("Couldn't load \(fileName) from main bundle: \n\(error)")
    }
    
    do {
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("Couldn't parse \(fileName) as \(T.self): \n \(error)")
    }
}
