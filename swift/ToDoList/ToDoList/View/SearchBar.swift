//
//  SearchBar.swift
//  ToDoList
//
//  Created by wh on 2022/1/13.
//

import SwiftUI

struct ClearButton: ViewModifier {
    @Binding var text: String

    public func body(content: Content) -> some View {
        HStack {
            content
            if text.count > 0 {
                Button(action: {
                    self.text = ""
                }) {
                    Image(systemName: "multiply.circle.fill")
                        .foregroundColor(.secondary)
                        .padding(.trailing)
                }
            }
        }
    }
}

struct SearchBar: View {
    @Binding var searchText: String
    @Binding var searching: Bool
    var body: some View {
        ZStack {
            Rectangle()
                .foregroundColor(Color("LightGray"))
            HStack {
                Image(systemName: "magnifyingglass")
                TextField("请输入Todo关键字", text: $searchText) { startedEditing in
                    if startedEditing {
                        withAnimation {
                            searching = startedEditing
                        }
                    }
                } onCommit: {
                    withAnimation {
                        searching = false
                    }
                }
                .modifier(ClearButton(text: $searchText))
            }
            .foregroundColor(.gray)
            .padding(.leading, 13)
        }
        .onReceive(NotificationCenter.default.publisher(for: UIApplication.keyboardWillHideNotification)) { _ in
            // 添加键盘通知 当滚动内容scrollview关闭键盘时 退出搜索
            withAnimation {
                searching = false
            }
        }
        .frame(height: 40)
        .cornerRadius(13)
        .padding()
    }
}
