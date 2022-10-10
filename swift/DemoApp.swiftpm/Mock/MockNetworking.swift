//
//  MockNetworking.swift
//  
//
//  Created by 黄磊 on 2022/4/10.
//

import Foundation
import ModuleNetworking
import MJModule

let s_defaultPageSize = 20

public class MockNetworking: ModuleNetworking {
    
    public static func startGet(
        _ urlString: String,
        data: URLEncodeWrapper?,
        header: [String : String]?,
        completion: @escaping (Result<Data, Error>) -> Void) {
            if urlString.hasSuffix(WebAppListResource.action) {
                // 获取 AppList，读取分页数据
                let fullUrl = data?.encode(into: urlString)
                let urlComponents = URLComponents(string: fullUrl!)!
                var startIndex = 0
                var pageSize = s_defaultPageSize
                urlComponents.queryItems?.forEach({ item in
                    switch item.name {
                    case "startIndex":
                        if let aStartIndex = Int(item.value ?? "") {
                            startIndex = aStartIndex
                        }
                    case "pageSize":
                        if let aPageSize = Int(item.value ?? "") {
                            pageSize = aPageSize
                        }
                    default:
                        break
                    }
                })
                let appList = MockAppList.shared.appList
                let needAppList = appList.suffix(from: startIndex).prefix(pageSize)
                let dataList = [AppInfo](needAppList);
                let response = TheAppListResponse(totalCount: appList.count, startIndex: startIndex, dataList: dataList)
                if let responseData = try? JsonEncodeWrapper.encoder.encode(response) {
                    DispatchQueue.global().asyncAfter(deadline: .now() + 1) {
                        completion(.success(responseData))
                    }
                    return
                }
            }
            DispatchQueue.global().asyncAfter(deadline: .now() + 1) {
                completion(.failure(URLError.badURL as! Error))
            }
    }
    
    public static func startPost(_ urlString: String, data: JsonEncodeWrapper?, header: [String : String]?, completion: @escaping (Result<Data, Error>) -> Void) {
        fatalError("not need for now")
    }
    
    public static func startDownload(_ urlString: String, fileUrl: URL, header: [String : String]?, progress: ((Progress) -> Void)?, completion: @escaping (Result<Any, Error>) -> Void) {
        fatalError("not need for now")
    }
    
    public static func startUpload(_ urlString: String, fileUrl: URL, data: JsonEncodeWrapper?, header: [String : String]?, progress: ((Progress) -> Void)?, completion: @escaping (Result<Data, Error>) -> Void) {
        fatalError("not need for now")
    }
}

struct TheAppListResponse : Codable {
    let totalCount: Int
    let startIndex: Int
    let dataList: [AppInfo]
}
