//
//  ViewModel.swift
//  SearchDemo
//
//  Created by chx on 2021/4/17.
//

import Foundation
import SwiftUI
let lightGrayColor = Color(red: 0.9, green: 0.9, blue: 0.9)
let backColor = Color(red: 0.95, green: 0.95, blue: 0.95)

struct Categorys: Identifiable {
    var id: Int
    var goods : [Goods]?
    var name : String?
    

}

extension Categorys {
    var nameText : String{
        return name ?? ""
    }
    var goodsArray : [Goods] {
        return goods ?? []
    }
}


struct Goods:Identifiable {
    var id: Int    
    var desc : String?
    var name : String?
    var price : String?
    var isHave = false
    
}

extension Goods{
    var priceViewColor : Color{
        return  self.isHave ? Color.blue : Color.gray
    }
    var priceText : String {
        return price ?? ""
    }
    var nameText : String {
        return name ?? ""
    }
    var descText : String {
        return desc ?? ""
    }
    
    
    
    
    
    
    
}


