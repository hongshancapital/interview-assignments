//
//  ResultModel.swift
//  AppList
//
//  Created by 王宁 on 2022/4/1.
//

struct ResultModel :Decodable{
    
    let resultCount: Int
    var results: [DataModel]
}
