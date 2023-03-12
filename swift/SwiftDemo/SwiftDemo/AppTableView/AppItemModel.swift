//
//  File.swift
//  SwiftDemo
//
//  Created by liuyang on 2023/3/5.
//

import Foundation
import HandyJSON

class AppItemModel: HandyJSON, ObservableObject {
    required init() {
        
    }
    
    var id = UUID()
    var artworkUrl60: String = ""
    var trackName: String = ""
    var description: String = ""
    @Published var like: Bool = false
}
