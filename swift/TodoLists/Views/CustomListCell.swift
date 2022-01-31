//
//  CustomListCell.swift
//  TodoList
//
//  Created by Chr1s on 2021/10/28.
//

import SwiftUI

struct CustomListCell: View {
    
    @EnvironmentObject var vm: ViewModel
    
    var item: ToDoListItem
    @State var text: String = ""
    
    /// 待办项是否在编辑状态
    /// - true  编辑状态
    /// - false 非编辑状态
    @State var isEditing: Bool = false
    
    /// 待办项是否获得焦点
    /// - true  获得焦点
    /// - false 未获得焦点
    @FocusState var isFocused: Bool
    
    init(item: ToDoListItem) {
        self.item = item
        self._text = State(initialValue: item.wrappedContent)
    }
    
    var body: some View {
        HStack {
            Image(systemName: item.isDone ? "record.circle" : "circle")
                .foregroundColor(.gray)
                .onTapGesture {
                    self.item.isDone.toggle()
                    vm.updateItemSubject.send(self.item)
                }

            /// Text 和 TextField切换显示
            /// 正常显示 Text
            /// 当按住时切换为 TextField
            if self.isEditing {
                textFieldwithClear()
            } else {
                Text(text).bold()
                    .foregroundColor(item.isDone ? .secondary : .primary)
                    .strikethrough(item.isDone, color: Color.gray)
                    .lineLimit(1)
                    .fixedSize(horizontal: false, vertical: true)
            }
            Spacer()
        }
        .padding(.horizontal, 15)
        .padding(.vertical,15)
        .background(Color(UIColor.systemBackground))
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .padding(.vertical, 5)
        .onLongPressGesture {
            self.isEditing = true
        }
    }
}

extension CustomListCell {
    
    func textFieldwithClear() -> some View {
        
        TextField(text, text: $text)
            .clearButton($text)
            .focused($isFocused)
            .onChange(of: isFocused) {
                if !$0 {
                    self.isEditing = false
                    
                    /// 内容为空时，删除item
                    if text.isEmpty {
                        vm.deleteItemSubject.send(item)
                    } else {
                        item.content = text
                        vm.updateItemSubject.send(item)
                    }
                }
            }
            .onAppear {
                DispatchQueue.main.async {
                    self.isFocused = true
                }
            }
    }
}

// MARK: - 清除按钮
struct ClearButton: ViewModifier {
    
    @Binding var text: String
    
    public init(text: Binding<String>) {
        self._text = text
    }
    
    public func body(content: Content) -> some View {
        HStack {
            content
            Spacer()
            Image(systemName: "multiply.circle.fill")
                .foregroundColor(.secondary)
                .opacity(text == "" ? 0 : 1)
                .onTapGesture { self.text = "" }
        }
    }
}

extension View {
    func clearButton(_ text: Binding<String>) -> some View {
        self.modifier(ClearButton(text: text))
    }
}
