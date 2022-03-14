//
//  HomeModel.swift
//  SwiftDemo
//
//  Created by AYX on 2022/3/12.
//

import UIKit
import HandyJSON

class HomeModel: HandyJSON {
    var artworkUrl60: String?
    var artworkImageURL: URL? {
        guard let artworkImageURLString = artworkUrl60?.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed), artworkImageURLString.count > 0 else {return nil}
        return URL(string: artworkImageURLString)
    }
    var trackCensoredName: String?
    var description: String?
    var isCollectionFlag: Bool?
    required init() {
        
    }
}
