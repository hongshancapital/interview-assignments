//
//  SearchItemModel.swift
//  SearchBarDemo
//
//  Created by Jaydon Qin on 2021/9/28.
//

import Foundation
import SwiftUI
let djs1 =
    [[
        "searchId":"Dyson",
        "title":"Vacuum",
        "content":[
        ["title":"V11","subTit":"In-stock","money":"$599.99","style":"1"],
            ["title":"V10","subTit":"Out-of-stock","money":"$399.99","style":"0"]
        ]],
     [
            "searchId":"Dyson",
            "title":"Hair Dryer",
            "content":[
        ["title":"Supersonic","subTit":"In-stock","money":"$399.99","style":"1"],
            ]
     ]
]

var searchModels: [SearchModel]? = load(djs1)

struct SearchItemModel: Hashable, Codable {
    var title: String
    var subTit: String
    var money: String
    var style: String
}

struct SearchModel: Hashable,Codable {
    var title: String
    var searchId: String
    var content : [SearchItemModel]
  

}
func load<T: Decodable>(_ array:[[String:Any]]) -> [T]? {
    
    guard let data = getJsonData(with: array) else {
        return nil
    }
    guard let models = try? JSONDecoder().decode([T].self, from: data) else {
        return nil
    }
    print(models)

    return models
}

func getJsonData(with param: Any) -> Data? {
    if !JSONSerialization.isValidJSONObject(param) {
        return nil
    }
    guard let data = try? JSONSerialization.data(withJSONObject: param, options: []) else {
        return nil
    }
    return data
}

