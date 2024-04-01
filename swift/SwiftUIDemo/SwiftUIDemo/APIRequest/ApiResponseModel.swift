//
//  ApiResponse.swift
//  SwiftUIDemo
//
//  Created by banban on 2022/5/20.
//

import Foundation


struct ListDataRes : Codable {
    
    var count : Int = 0
    
    var items : [ListItemModel] = []
    
    var success : Bool = true
    
    /// 返回的错误信息
    var msg : String = ""
    
    enum CodingKeys:String,CodingKey {
        case count = "resultCount"
        case items = "results"
    }
    
}
