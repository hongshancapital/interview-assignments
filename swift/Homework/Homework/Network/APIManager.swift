//
// Homework
// APIManager.swift
//
// Created by wuyikai on 2022/4/29.
// 
// 

import Foundation

public enum APIError: Error {
    public enum ResponseError: Error {
        case JSONError(String)
        case serverError(String)
        case decodeError(String)
        case nomoreData(String)
    }
}

class APIManager {
    
    //模拟网络请求
    func send<T: HTTPRequest>(_ request: T) async -> Result<Data, Error> {
        let parameters = request.parameters ?? [:]
        let pageNumber = Int((parameters["page"] as? String) ?? "0") ?? 0
        // 每页 10 条
        let start = pageNumber * 10
        let mockData = Mock.mockData
        try? await Task.sleep(nanoseconds: 1 * 1_000_000_000)
        guard start < mockData.count else {
            return .failure(APIError.ResponseError.nomoreData("没有更多数据"))
        }
        let rightIndex = min(start + 10, mockData.count)
        let result = Array(mockData[start..<rightIndex])
        let response: [String : Any] = [
            "results": result,
            "resultCount": "\(rightIndex - start)"
        ]
        guard let data = try? JSONSerialization.data(withJSONObject: response,
                                                     options: .init(rawValue: 0))
        else {
            return .failure(APIError.ResponseError.JSONError("数据格式不正确"))
        }
        return .success(data)
    }
    
}


