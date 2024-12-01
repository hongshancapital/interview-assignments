//
//  HSResultList.swift
//  HSSearchList
//
//  Created by dongxia zhu on 2021/10/8.
//

import SwiftUI

struct HSResultList: View {
    var results: [HSResultModel]
    var body: some View {
        if results.count > 0 {
            List {
                ForEach(results) { department in
                    HSListHeader(title: department.category.rawValue)
                        .frame(height: 30)
                        .listRowBackground(backgroundColor)
                    
                    ForEach(department.itemData) { item in
                        HSResultRow(item: item).frame(height: 60)
                    }
                }
            }
            .listRowInsets(EdgeInsets())
            .listStyle(PlainListStyle())
        } else {
            VStack {
                Text("No result")
                    .font(.title2)
                    .fontWeight(.regular)
                    .foregroundColor(titleColor)
            }
            .frame(maxWidth: .infinity, maxHeight: 100, alignment: .bottom)
        }
    }
}
