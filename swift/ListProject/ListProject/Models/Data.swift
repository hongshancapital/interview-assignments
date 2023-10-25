//
//  Data.swift
//  ListProject
//
//  Created by shencong on 2022/6/9.
//

import UIKit
import SwiftUI

/// 全部响应数据
let allResponseData: ResponseModel = loadResponeData("data.json")
/// 全部列表数据
let allListData: [ItemModel] = load("data.json")

func load<T: Decodable>(_ filename: String) -> T {
    let dicResult: NSDictionary
    
    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
    else {
        fatalError("没有找到json文件：\(filename)")
    }
    
    do {
        let dataStr:String = try String(contentsOf: file)
        dicResult = dicWithJson(jsonString: dataStr)
    } catch {
        fatalError("不能加载json文件： \(filename) ，error:\n\(error)")
    }
    
    guard let results = dicResult["results"] else {
        fatalError("无数据")
    }

    var data: Data
    do {
        data = try JSONSerialization.data(withJSONObject: results, options: .prettyPrinted)
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    } catch {
        fatalError("不能解析json文件： \(filename) as \(T.self):\n\(error)")
    }
}

/// 加载响应数据
/// - Returns: 返回一个ResponseModel对象
func loadResponeData<T: Decodable>(_ filename: String) -> T {
    let data: Data
    
    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
    else {
        fatalError("没有找到json文件：\(filename)")
    }
    
    do {
        data = try Data(contentsOf: file)
        return try JSONDecoder().decode(T.self, from: data)
    } catch {
        fatalError("不能加载json文件： \(filename) ，error:\n\(error)")
    }
}

func dicWithJson(jsonString:String) ->NSDictionary{
    let jsonData:Data = jsonString.data(using: .utf8)!
    
    let dict = try? JSONSerialization.jsonObject(with: jsonData, options: .mutableContainers)
    if dict != nil {
        return dict as! NSDictionary
    }
    return NSDictionary()
}
