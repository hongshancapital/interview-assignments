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

    var id: String
    
    public var iconUrl: String = ""
    public var title: String = ""
    public var description: String = ""
    public var isFavorite: Bool = false
    
    public static func parseJSON(_ json: JSON) -> AppModel {
        var model = AppModel(id: json["artistId"].stringValue);
        model.iconUrl = json["artworkUrl60"].stringValue;
        model.title = json["trackName"].stringValue;
        model.description = json["description"].stringValue;
        return model;
    }
}
