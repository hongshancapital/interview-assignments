//
//  ListView.swift
//  Example
//
//  Created by 聂高涛 on 2022/3/2.
//

import SwiftUI

//滚动列表
struct ListView : View {
    let dataArray : [CellData]
    
    var body: some View {
        VStack {
            ForEach(dataArray) { item in
                CellView(data:item)
                    .background(Color.white)
                    .cornerRadius(8)
                    .clipped()
                    .padding(EdgeInsets(top: 5, leading: 16, bottom: 5, trailing: 16))
            }
        }
    }
}
