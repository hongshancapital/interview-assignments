//
//  APIManager.swift
//  SwiftUIApp
//
//  Created by Univex on 2022/5/2.
//

import Foundation

public struct APIManager {
    
    static func fetchHomeList(params:Dictionary<String,Any>) async -> (HomeList?, RequsetState) {
        let res = await RM.get(Config.requsetHost+APIPath.favoriteList, params)
        let homeList:HomeList = json2Model(res.data)
        return (homeList, res.state)
    }
    
    static func json2Model<T: Decodable>(_ data: Data) -> T {
        do {
            let decoder = JSONDecoder()
            return try decoder.decode(T.self, from: data)
        } catch {
            fatalError("Couldn't parse :\n\(error)")
        }
    }
}
