//
//  MylistObj.swift
//  MyHomework
//
//  Created by mao on 2022/6/10.
//

import Foundation
import SwiftUI

struct MylistObj: Hashable, Codable, Identifiable {
    
    var id: String
    var artworkUrl60: String
    var artistName: String
    var description: String
    var artworkUrl100: String

    var isLike: Int

    var image: Image {


        Image("")
    }

    
    func dowloadFromServer(url: URL){
        
          
    }
}
