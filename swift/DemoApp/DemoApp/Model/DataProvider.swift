//
//  DataProvider.swift
//  DemoApp
//
//  Created by liang on 2022/5/18.
//

import Foundation

class DataProvider {
    var appList = [AppModel]()
    func requestModelList(from lastApp:AppModel?, count:Int, on completion:(_ modelList:[AppModel]?, _ err: Error?) -> Void) {
        var startIndex = 0
        if let startId = lastApp?.id {
            for i in 0...appList.count {
                let anApp = appList[i]
                if anApp.id == startId {
                    startIndex = i+1
                    break
                }
            }
        }
        if (startIndex+count) <= appList.count  {
            let list = [AppModel].init(appList[startIndex...startIndex+count-1])
            completion(list, nil)
        } else {
            completion(nil, NSError.init(domain: "", code: 999, userInfo: nil))
        }
    }
    
    init() {
        guard let url = Bundle.main.url(forResource: "data", withExtension: "json") else {return}
        guard let data = try? Data.init(contentsOf: url) else {return}
        guard let json = try? JSONSerialization.jsonObject(with: data, options: []) else {return}
        
        if let rootJson = json as? [String: Any] {
            guard let jsonArray = rootJson["results"] as? [[String: Any]] else {return}
            for aJson in jsonArray {
                let anApp = AppModel(data: aJson)
                appList.append(anApp)
            }
        }
    }
}
