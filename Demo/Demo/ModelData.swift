//
//  ModelData.swift
//  Demo
//
//  Created by mac on 2022/7/19.
//

import Foundation

var apps:[AppModel] = load("data.json")

func load<T:Decodable>(_ filename:String)->T{
    var data:Data
    let json:Dictionary<String, Any>
    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
    else{
        fatalError("Couldn't find \(filename) in main bundle")
    }
    
    do {
        data = try Data(contentsOf: file)
    } catch  {
        fatalError("Couldn't load \(file) from main bundle \n\(error):")
    }
    
    do {
        json = try JSONSerialization.jsonObject(with: data) as! Dictionary<String, Any>
    } catch  {
        fatalError("Couldn't parse \(file) with error\(error)")
    }
    
    let result:AnyObject = json["results"] as AnyObject
    data = try! JSONSerialization.data(withJSONObject: result, options: .fragmentsAllowed)
    
    do {
        let decoder = JSONDecoder();
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
    }
}
