//
//  Network.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/19.
//

import UIKit
import SwiftyJSON

class Network {
    public class func getData(_ pageNumber: Int, _ pageSize: Int, _ callback: @escaping (_ data: Array<AppModel>?, _ error: NSError?) -> Void) {
        AppMockManager.requestLocalData(pageNumber, pageSize) { result in
            DispatchQueue.main.async {
                let code = result["code"].intValue
                if (code == ApiCode.Success.rawValue) {
                    let datas = result.dictionaryValue["result"]?.arrayValue ?? [];
                    let models = datas.map { json in
                        return AppModel.parseJSON(json)
                    }.filterDuplicates { model in
                        return model.id
                    };
                    callback(models, nil)
                    return
                }
                callback(nil, apiError(ApiCode(rawValue: code) ?? ApiCode.DataNotFound))
            }
        }
    }
}
