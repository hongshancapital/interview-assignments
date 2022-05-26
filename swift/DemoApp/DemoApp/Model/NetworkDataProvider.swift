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
        URLSession.shared.dataTask(with: requestUrl!) { [self] (data, response, error) in
            guard (response as? HTTPURLResponse)?.statusCode == 200 && data != nil else {
                delay(appList: nil, error: DataProviderError.requestFailed, completion: completion)
                return
            }

            do {
                let apiRsp = try JSONDecoder().decode(ApiResponse.self, from: data!)
                let subList = self.subList(within: apiRsp.results, from: last, count: count)
                delay(appList: subList, error: nil, completion: completion)
            } catch {
                delay(appList: nil, error: error, completion: completion)
            }
        }.resume()
    }
}
