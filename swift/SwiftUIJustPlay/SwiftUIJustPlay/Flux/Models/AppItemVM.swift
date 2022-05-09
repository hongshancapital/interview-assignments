//
//  AppItemVM.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/9.
//

import Foundation

var appIdentifier : Int = 0;

struct AppItemVM: Codable, Identifiable {

    let appItem: AppItem

    init(appItem: AppItem) {
        orderId = appIdentifier
        appIdentifier += 1
        self.appItem = appItem
    }

    var id : Int { appItem.id }
    
    var orderId: Int
    
    var icon: String { appItem.imageURLString }
    
    var title: String { appItem.title }
    
    var content: String { appItem.body }
    
    var isFav: Bool { appItem.favorite }
    
    
}
