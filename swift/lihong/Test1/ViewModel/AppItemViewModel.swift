//
//  AppItem.swift
//  Test1
//
//  Created by Stephen Li on 2022/10/18.
//

import Foundation
import SwiftUI

struct AppItemViewModel: Identifiable {
    let id: Int64
    let name: String
    let description: String
    var isFavorited: Bool
    let iconImageURL: String
    
    var iconImage: Image?
    
    init(_ id:Int64,
         _ name:String,
         _ description:String,
         _ isFavorited:Bool,
         _ iconImageURL:String) {
        self.id = id
        self.name = name
        self.description = description
        self.isFavorited = isFavorited
        self.iconImageURL = iconImageURL
    }
}
