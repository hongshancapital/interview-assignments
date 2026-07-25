//
// Homework
// AppListRequest.swift
//
// Created by wuyikai on 2022/4/29.
// 
// 

import Foundation

struct AppListRequest: HTTPRequest {
    let page: Int
    
    var path: String {
        return "/example/apps/"
    }

    var parameters: Parameters? {
        return [
            "page": "\(page)"
        ]
    }
}
