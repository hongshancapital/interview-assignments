//
//  Artist.swift
//  text
//
//  Created by Harden.L on 2023/5/23.
//

import Foundation

class Artist:Identifiable,ObservableObject {
    var id: Int
    
    var name:String         //标题
    var workUrl:String      //logo地址
    var description:String  //描述
    @Published var select:Bool
    
    init(dic:NSDictionary) {
        name = dic.value(forKey: "artistName") as!String
        workUrl = dic.value(forKey: "artworkUrl60") as!String
        description = dic.value(forKey: "description") as!String
        id = dic.value(forKey: "artistId") as!Int
        select = false
        print("name== \(name)")
    }
}
