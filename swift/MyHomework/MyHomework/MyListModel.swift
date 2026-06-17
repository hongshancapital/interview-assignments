//
//  MyListModel.swift
//  MyHomework
//
//  Created by mao on 2022/6/10.
//

import Foundation

var myListData: [MylistObj] = load("currentData.json")

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

//    var newStr = String(data:data,encoding:.utf8)!
    
    
    let dict : NSDictionary = try! JSONSerialization.jsonObject(with: data, options: .mutableContainers) as! NSDictionary
    
    
    var jsonData : Data?
    if dict.count > 0 {
       
        
        let arrDatas : NSArray = dict.object(forKey: "results") as! NSArray;
        let newArr:NSMutableArray = NSMutableArray.init();
        for i in 0 ..< arrDatas.count{
            
            let diced:NSDictionary = arrDatas[i] as! NSDictionary;
            let mutableDic:NSMutableDictionary = NSMutableDictionary.init(dictionary: diced);
            mutableDic.setValue(String(format: "%i", i), forKey: "id");
            mutableDic.setValue(0, forKey: "isLike");

            newArr.add(mutableDic)
        }
        

        jsonData = try? JSONSerialization.data(withJSONObject: newArr, options: [])
    }

//    stringByReplacingOccurrencesOfString
//    newStr = newStr.replacingOccurrences(of: "[]", with: "\"\"")


//    let data : NSData! = try? JSONSerialization.data(withJSONObject: array, options: []) as NSData!
    
    
    do {
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: jsonData!)
    } catch {
        fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
    }
}
