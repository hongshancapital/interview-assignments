//

//
//  File.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/2.
//  
//
    

import Foundation

struct AppDataResponse: Codable {
    // 数据数量
    let resultCount: Int
    // 数据
    var results: [AppData]
    
    enum CodingKeys: String, CodingKey {
        case resultCount
        case results
    }
}
