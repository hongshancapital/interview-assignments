//
//  ListRowView.swift
//  TodoList_SwiftUI
//
//  Created by zengmuqiang on 2021/11/23.
//

import SwiftUI

struct ListRowView: View {
    
    @EnvironmentObject var listViewModel: ListViewModel
    var section: SectionModel
    var item: ItemModel
    
    var body: some View {
        let hStack = HStack {
            Image(systemName: item.isCompleted ? "checkmark.circle" : "circle")
                .padding(.leading, 3)
                .foregroundColor(item.isCompleted ? .green : .red)
            Text(item.title)
                .font(.subheadline)
            Spacer()
            if item.isEditing == true {
                Text("开始编辑啦😎")
                    .font(.headline)
                    .foregroundColor(Color.red)
            }
        }
        .padding()
        .cornerRadius(15)
        .shadow(color: Color.gray.opacity(0.7), radius: 5, x: 0, y: 5)
        
        if item.isEditing == true {
            hStack.overlay(
                RoundedRectangle(cornerRadius: 15, style: .circular)
                    .stroke(Color.red.opacity(0.5), lineWidth: 1)
            )
        } else {
            hStack.overlay(
                RoundedRectangle(cornerRadius: 15, style: .circular)
                    .stroke(Color.gray.opacity(0.5), lineWidth: 1)
            )
        }
    }
}

struct ListRowView_Previews: PreviewProvider {
    
    static var section1 = SectionModel(header: "First Section")
    static var section2 = SectionModel(header: "Second Section")
    
    static var item1 = ItemModel(title: "First item!", isCompleted: false)
    static var item2 = ItemModel(title: "Second Item.", isCompleted: true)
    
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
