//
//  LCHomeListView.swift
//  LCSwitfDemo
//
//  Created by 梁杰 on 2022/3/23.
//

import SwiftUI

struct LCHomeListView: View {
    var body: some View {
        NavigationView{
            List{
                ForEach(listModels,id: \.self)
                {
                    listModel in
                    LCHomeListCell(model: listModel)
                        .padding(EdgeInsets(.zero))
                        .cornerRadius(12, antialiased: true)
                }
                .listRowSeparator(.hidden)
                .listRowInsets(EdgeInsets(.init(top: 0, leading: 0, bottom: 20, trailing: 0)))
                .background(Color.clear)
                .listRowBackground(Color.init(red: 0.94, green: 0.94, blue: 0.94))
            }.navigationTitle(Text("App"))
        }
    }
}

