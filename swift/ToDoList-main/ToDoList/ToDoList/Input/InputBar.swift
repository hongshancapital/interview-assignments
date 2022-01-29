//
//  InputBar.swift
//  ToDoList
//
//  Created by lilun.ios on 2022/1/27.
//

import Foundation
import SwiftUI

struct InputBar: View {
    
    @EnvironmentObject var store: DefaultStore<AppState>
    static let AddCategoryName = "Add category"
    @State var text: String
    
    func inputPlaceHolder() -> String {
        if store.getCurrent().addCategoryID == nil {
            return "请输入分类名称..."
        }
        return "请输入Todo..."
    }
    
    var textField: some View {
        TextField(inputPlaceHolder(), text: $text, onCommit:  {
            Log.d("input click with return \(text)")
            guard !text.isEmpty else {
                Log.d("input text is empty")
                return
            }
            commitTodo(content: text)
            text = ""
        })
            .font(Font.system(size: 12.0))
            .padding(EdgeInsets(top: 6, leading: 6, bottom: 6, trailing: 6))
            .background(Color.white)
            .cornerRadius(5.0)
            .overlay(
                RoundedRectangle(cornerRadius: 5.0)
                    .strokeBorder(
                        style: StrokeStyle(
                            lineWidth: 1.0,
                            dash: [2]
                        )
                    )
                    .foregroundColor(.gray)
            )
            .padding([.leading, .trailing], 10)
    }
    
    var body: some View {
        ZStack {
            Color("listbg")
                .opacity(0.2)
            HStack(alignment: .center, spacing: 5) {
                textField
                menu
            }
        }.background(.white.opacity(0.3))
    }
}

extension InputBar {
    /// switch category
    func switchCategory(categoryID: String?) -> (() -> ()) {
        return { [weak store] in
            Log.d("click switch category \(String(describing: categoryID))")
            store?.dispatch(action: ToDoAction.switchCategory(categoryID: categoryID))
        }
    }
    /// Add category or add item to specify category
    func commitTodo(content: String) {
        Log.d("commit todo \(content) start")
        if let category = store.getCurrent().categoryToAdd() {
            Log.d("commit todo \(content) item \(content) to category \(category.categoryName)")
            let ac = ToDoAction.addTodoItem(categoryID: category.categoryID, itemContent: content)
            store.dispatch(action: ac)
        } else {
            Log.d("commit category \(content)")
            let ac = ToDoAction.addCategory(categoryName: content)
            store.dispatch(action: ac)
        }
    }
}

extension InputBar {
    func menuTitle() -> String {
        if let name = store.getCurrent().categoryToAdd()?.categoryName {
            return name
        }
        return Self.AddCategoryName
    }

    var menu: some View {
        Menu {
            Button(Self.AddCategoryName,
                   action: self.switchCategory(categoryID: nil))
            ForEach(store.getCurrent().sortedCategorys(),
                    id: \.self.categoryID) { c in
                Button(c.categoryName,
                       action: self.switchCategory(categoryID: c.categoryID))
            }
        } label: {
            menuLabel
        }
        .padding(.bottom, 0)
        .ignoresSafeArea()
    }
    
    var menuLabel: some View {
        HStack(alignment: .center, spacing: 3) {
            Text(menuTitle())
                .font(Font.caption2)
                .lineLimit(2)
                .foregroundColor(.black)
            Image(systemName: "chevron.down")
                .resizable()
                .scaledToFit()
                .frame(width: 8, height: 8, alignment: .center)
                .padding(.trailing, 3.0)
                .foregroundColor(.black)
        }.frame(width: 55, height: 30)
            .padding(.trailing, 5)
            .background(.white)
            .cornerRadius(10)
            .padding([.leading, .trailing], 2)
    }
}
