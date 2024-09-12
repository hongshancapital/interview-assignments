//
//  ListRowView.swift
//  ToDoList
//
//  Created by 炎檬 on 2022/1/13.
//

import SwiftUI

struct ListRowView: View {
    let itemModel: ItemModel
    var body: some View {
        HStack {
            Image(systemName: itemModel.isChecked ? "circle.inset.filled" : "circle")
            Text(itemModel.title).strikethrough(itemModel.isChecked).font(.system(size: 10).bold())
            Spacer()
        }
        .listRowSeparator(.hidden)
        .listRowInsets(EdgeInsets(top: 5, leading: 15, bottom: 5, trailing: 15))
        .listRowBackground(Color.clear)
        .foregroundColor(itemModel.isChecked ? .gray : .black)
        .padding(EdgeInsets(top: 10, leading: 10, bottom: 10, trailing: 10))
        .frame(minHeight: 40)
        .background(Color.white)
        .cornerRadius(8)
    }
}

struct ListRowView_Previews: PreviewProvider {
    static var previews: some View {
        ListRowView(
            itemModel: ItemModel.init("this is the first title", false)
        )
    }
}
