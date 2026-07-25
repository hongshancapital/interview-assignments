//
//  Select.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/10/21.
//

import Foundation

/**
 构建我们需要的数据
 
 可以在此任意调整我们需要如何构造数据
 
 现在任意构建了 较多的数据 为了展示分页的功能
 */

func buildSearchListData() -> [SearchModel] {
    return [
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Hair Dryer", items: buildHairDryer()),
        SearchModel(title: "Dxxxx", items: buildVacuumItems()),
        SearchModel(title: "Dyxxxx", items: buildHairDryer()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dxxxx", items: buildVacuumItems()),
        SearchModel(title: "Dyxxxx", items: buildHairDryer()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Hair Dryer", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Hair Dryer", items: buildHairDryer()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Hair Dryer", items: buildHairDryer()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Hair Dryer", items: buildHairDryer()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Hair Dryer", items: buildHairDryer()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Hair Dryer", items: buildHairDryer()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Hair Dryer", items: buildHairDryer()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Hair Dryer", items: buildHairDryer()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
        SearchModel(title: "Dysonxxxx", items: buildHairDryer()),
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Dysxxx", items: buildHairDryer()),
        SearchModel(title: "Dysoxx", items: buildHairDryer()),
    ]
}

func buildVacuumItems() -> [SearchItemModel] {
    return [
        SearchItemModel(name: "V11", stock: .instock, price: 599.99),
        SearchItemModel(name: "V10", stock: .outstock, price: 399.99)
    ]
}

func buildHairDryer() -> [SearchItemModel] {
    return [
        SearchItemModel(name: "Supersonic", stock: .instock, price: 399.99),
    ]
}
