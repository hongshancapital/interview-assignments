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
            var appList = [AppModel]()
            if let list = AppModel.parseList(from: data) {
                appList = list
            }
            
            let subList = self.subList(within: appList, from: last, count: count)
            DispatchQueue.global().asyncAfter(deadline: .now() + 2) {
                if let result = subList {
                    completion(result, nil)
                } else {
                    completion(nil, NSError.init(domain: "", code: 999, userInfo: nil))
                }
            }
            
        }.resume()
    }
}
