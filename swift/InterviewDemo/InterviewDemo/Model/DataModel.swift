//
//  DataModel.swift
//  InterviewDemo
//
//  Created by 陈凝 on 2022/8/1.
//

import Foundation

// MARK: DataModel
struct DataModel:Decodable {
    let resultCount: Int?
    let results: [Result]?
}

// MARK: Result
struct Result:Decodable{
    let artworkUrl512: String?
    let trackID: Int?
    let trackName: String?
    let description: String?
    let trackId: Int?
}
