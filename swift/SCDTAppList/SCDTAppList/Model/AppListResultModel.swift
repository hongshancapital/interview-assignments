//
//  AppListRootModel.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/14.
//

import SwiftUI

struct AppListResultModel: Decodable{
    let resultCount: Int    //data total count
    let results: [AppProductModel]  //data list
}
