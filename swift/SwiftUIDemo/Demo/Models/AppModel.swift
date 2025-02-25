//
//  App.swift
//  Demo
//
//  Created by 石超 on 2022/6/22.
//

import Foundation
import SwiftUI

struct AppModel: Codable, Hashable, Identifiable {

    var id: Int = 0
    var identifer: Int = 0
    var name: String = ""
    var description: String = ""
    var isFavorite: Bool = false
    
    var imageUrlStr: String?
    var image: Image? {
        guard let urlStr = imageUrlStr else { return nil }
        guard let url = URL(string: urlStr) else { return nil }
        guard let data = NSData(contentsOf: url) else { return nil }
        guard let image = UIImage(data: data as Data) else { return nil }
        
        return  Image(uiImage: image)
    }
}
