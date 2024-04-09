//
//  AppModel.swift
//  swiftUI-demo
//
//  Created by guo changqing on 2023/3/19.
//

import UIKit
import SwiftyJSON
import SwiftUI

struct AppModel: Decodable,Identifiable,Hashable {

    public var id: String
    public var iconUrl: String
    public var title: String
    public var description: String
    public var isFavorite: Bool = false
    
    init(_ json: JSON) {
        self.id = json["artistId"].stringValue
        self.iconUrl = json["artworkUrl60"].stringValue
        self.title = json["trackName"].stringValue
        self.description = json["description"].stringValue
    }

}
