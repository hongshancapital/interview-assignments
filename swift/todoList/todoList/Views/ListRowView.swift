//
//  ListRowView.swift
//  todoList
//
//  Created by deng on 2021/12/7.
//

import SwiftUI

struct ListRowView: View {
    @EnvironmentObject var listViewModel: ListViewModel
    var section: SectionModel
    var item: ItemModel
    var body: some View {
        let hs = HStack {
            Image(systemName: item.isCompeleted ? "circle.inset.filled" : "circle")
                .padding(.leading, 3)
            if item.isCompeleted {
                Text(item.title)
                    .font(.subheadline)
                    .strikethrough()
            } else {
                Text(item.title)
                    .font(.subheadline)
            }
            Spacer()
            if item.isEditing == true {
                Button(action: {
                    listViewModel.deleteSubItems(section: section, item: item)
                }) {
                    Text("删除")
                        .font(.headline)
                        .foregroundColor(Color.red)
                }
            }
        }
        .padding(15)
        .background(Color.white)
        .cornerRadius(10)
        if item.isEditing == true {
            hs.overlay(
                RoundedRectangle(cornerRadius: 5, style: .circular)
                    .stroke(Color.blue.opacity(0.5), lineWidth: 1)
            )
        } else {
            hs.overlay(
                RoundedRectangle(cornerRadius: 5, style: .circular)
                    .stroke(Color.white.opacity(0.5), lineWidth: 1)
            )
        }
    }
}

struct ListRowView_Previews: PreviewProvider {
    static var section1 = SectionModel(header: "one")
    static var section2 = SectionModel(header: "tow")
    static var item1 = ItemModel(title: "one item", isCompleted: false)
    static var item2 = ItemModel(title: "two item", isCompleted: true)
    static var previews: some View {
        Group {
            ListRowView(section: section1, item: item1)
            ListRowView(section: section2, item: item2)
        }
        .previewLayout(.sizeThatFits)
        .background(Color.gray.opacity(0.1))
        .environmentObject(ListViewModel())
    }
}
