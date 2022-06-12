//
//  APIClient.swift
//  ListProject
//
//  Created by shencong on 2022/6/12.
//

import Foundation
import Combine

struct APIClient {
    var allLists: [ItemModel] = allListData
    // 所有数据
    var responseModel: ResponseModel = allResponseData
    
    struct Response<T> {
        let value: T
        let response: URLResponse
    }
    
    func requestMock(_ request: URLRequest) -> [ItemModel] {
        return allLists
    }
}
