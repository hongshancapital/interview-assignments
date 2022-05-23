//
//  NetworkDataProvider.swift
//  DemoApp
//
//  Created by liang on 2022/5/20.
//

import Foundation

class NetworkDataProvider: DataProvider {
    let requestUrl = URL(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat")
    
    func fetchAppModel(from last: AppModel?, count: Int, on completion: @escaping ([AppModel]?, Error?) -> Void) {
        URLSession.shared.dataTask(with: requestUrl!) { (data, rsp, error) in
            guard let data = data else {
                return
            }
            guard let json = try? JSONSerialization.jsonObject(with: data, options: []) else {return}
            
            var appList = [AppModel]()
            if let rootJson = json as? [String: Any] {
                guard let jsonArray = rootJson["results"] as? [[String: Any]] else {return}
                for aJson in jsonArray {
                    let anApp = AppModel(data: aJson)
                    appList.append(anApp)
                }
            }
            var startIndex = 0
            if last != nil {
                for i in 0..<appList.count {
                    if appList[i].id == last?.id {
                        startIndex = i + 1
                        break
                    }
                }
            }
            
            DispatchQueue.global().asyncAfter(deadline: .now() + 2) {
                if (startIndex+count) <= appList.count {
                    let list = [AppModel].init(appList[startIndex...startIndex+count-1])
                    completion(list, nil)
                } else {
                    completion([], NSError.init(domain: "", code: 999, userInfo: nil))
                }
            }
            
        }.resume()
    }
}
