//
//  DataLoader.swift
//  Homework
//
//  Created by miao jiafeng on 2022/6/12.
//

import Foundation
import UIKit

let kScreenWidth = UIScreen.main.bounds.size.width
let kScreenHeight = UIScreen.main.bounds.size.height

//模拟耗时操作
func waitForSomeTime() -> Void
{
    var count = 0
    while(count < 100000000)
    {
        count+=1
    }
}

//模拟从数据库中取出数据
func getRealListData(_ length: Int) -> [AppData]
{
    return Array(listData.prefix(length))
}
//从json文件中加载数据
let listData:[AppData] = load("ListData.json")

func load<T: Decodable>(_ fileName: String) -> T{
    let data: Data
    
    guard let file = Bundle.main.url(forResource: fileName, withExtension: nil)
            else
            {
                fatalError("Couldn't find \(fileName) in main bundle.")
            }
    
    do{
        data = try Data(contentsOf: file)
    }catch{
        fatalError("Couldn't load \(fileName) from main bundle:\n\(error)")
    }
    
    do{
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    }catch{
        fatalError("Couldn't parse \(fileName) as \(T.self):\n\(error)")
    }
}
