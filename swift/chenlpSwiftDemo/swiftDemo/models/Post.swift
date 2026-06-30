//
//  Post.swift
//  swiftDemo
//
//  Created by chenlp on 2022/4/11.
//

import Foundation
import UIKit
import SwiftUI


struct PostList: Codable ,Hashable {
    let resultCount: Int
    var results: [Post]
}

struct Post: Codable ,Hashable{//Identifiable
    let trackId: Int
    let trackName: String
    let artistId: Int
    let description: String
    let artworkUrl60: String
    var isVppDeviceBasedLicensingEnabled: Bool
}


func loadPostListData(_ fileName: String) -> PostList {
    guard let url = Bundle.main.url(forResource: fileName, withExtension: nil) else {
        fatalError("Can not find \(fileName) in main bundle")
    }
    guard let data = try? Data(contentsOf: url) else {
        fatalError("Can not load \(url)")
    }
    guard let list = try? JSONDecoder().decode(PostList.self, from: data) else {
        fatalError("Can not parse post list json data!")
    }
    return list
}



struct Detail: Codable{
    var response: Response
}

struct Response: Codable{
    var docs: [Doc]
}
struct Doc: Codable,Identifiable{
    var id: String
    var eissn: String
    var publication_date: String
    var article_type: String
}
