//
//  Network.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/19.
//

import UIKit
import SwiftyJSON
import Alamofire

class Network {
    
    private static let host = "https://itunes.apple.com/search"
    
    public class func getAppModelList(_ pageNumber: Int, _ pageSize: Int, _ callback: @escaping (_ data: Array<AppModel>?, _ error: Error?) -> Void) {
        let urlPath = "\(host)?limit=\(pageSize * pageNumber + pageSize)&entity=software&term=chat"
        _ = AF.request(urlPath, method: .get).responseData { response in
            if let error = response.error {
                callback(nil, error)
                return
            }
            guard let data = response.data, let json = try? JSON(data: data) else {
                callback([], nil)
                return;
            }
            var results = json["results"].arrayValue
            let left = pageSize * pageNumber
            let right = results.count
            results = results[left..<right].map { $0 }
            let appModels = results.map { AppModel($0) }
            callback(appModels, nil)
        }
    }
}
