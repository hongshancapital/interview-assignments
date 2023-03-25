//
//  AppMockManager.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/18.
//

import UIKit
import SwiftyJSON

class AppMockManager {
    public class func requestLocalData(_ pageNumber: Int, _ pageSize: Int, _ callback: @escaping (_ result: JSON) -> Void) {
        getAppMockData { result in
            
            // Mock层如果非成功状态，直接回调
            if (result["code"].intValue != ApiCode.Success.rawValue) {
                callback(result)
                return
            }
            
            // 本地数据为空，报错
            let resultData = result["result"].dictionaryValue
            if (resultData["results"]?.arrayValue.count ?? 0 == 0) {
                let result = JSON([
                    "code": ApiCode.DataNotFound.rawValue,
                    "message": apiError(ApiCode.DataNotFound).domain
                ])
                callback(result)
                return
            }
            
            // 数据格式错误，报错
            guard let datas = resultData["results"]?.arrayValue else {
                let result = JSON([
                    "code": ApiCode.NoMoreData.rawValue,
                    "message": apiError(ApiCode.NoMoreData).domain
                ])
                callback(result)
                return
            }
            
            var resultDatas: Array<Any>
            
            let begin = pageNumber * pageSize
            var end = begin + pageSize
            if (end > datas.count) {
                end = datas.count
            }
            if end <= begin {
                resultDatas = []
            } else {
                resultDatas = datas[begin ..< end].map { $0 }
            }
            
            
            let result = JSON([
                "code": ApiCode.Success.rawValue,
                "message": apiError(ApiCode.Success).domain,
                "result": resultDatas
            ]);
            callback(result)
        }
    }
    
    public class func getAppMockData(callback: @escaping (_ result: JSON) -> Void) {
        
        DispatchQueue(label: "localData.queue").async {
            guard let path = Bundle.main.path(forResource: "AppMock", ofType: "json") else {
                let result = JSON([
                    "code": ApiCode.DataNotFound.rawValue,
                    "message": apiError(ApiCode.DataNotFound).domain
                ])
                callback(result)
                return
            }
            
            let url = URL.init(fileURLWithPath: path)
            
            guard let data = try? Data.init(contentsOf: url) else {
                let result = JSON([
                    "code": ApiCode.DataNotFound.rawValue,
                    "message": apiError(ApiCode.DataNotFound).domain
                ]);
                callback(result)
                return
            }
            
            guard let dataJson = try? JSON.init(data: data) else {
                let result = JSON([
                    "code": ApiCode.ParseFailed.rawValue,
                    "message": apiError(ApiCode.ParseFailed).domain
                ])
                callback(result)
                return
            }
            
            let result = JSON([
                "code": ApiCode.Success.rawValue,
                "message": apiError(ApiCode.Success).domain,
                "result": dataJson
            ])
            callback(result)
        }
    }
}
