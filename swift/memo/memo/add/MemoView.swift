//
//  MemoView.swift
//  memo
//
//  Created by LI on 2021/11/15.
//

import SwiftUI

struct MemoView: View {
    @StateObject var item: MemoItem
    
    var body: some View {
        HStack {
            checkedButton()
            if item.edit {
                inputField()
            }
            else {
                displayText()
            }
        }
        .background(Color.white)
        .cornerRadius(9)
        .clipShape(RoundedRectangle(cornerRadius: 9))
    }
    
    private func checkedButton() -> some View {
        Button {
            if item.edit { return }
            item.checked.toggle()
            MemoDataSource.default.sort(by: item.section())
        } label: {
            if item.checked {
                ZStack {
                    Circle()
                        .stroke(Color.gray)
                        .frame(width: 20, height: 20, alignment: .center)
                    Circle()
                        .fill(Color.gray)
                        .frame(width: 14, height: 14, alignment: .center)
                }
                .padding()
                .background(Color.white)
            } else {
                Circle()
                    .stroke(Color.gray)
                    .frame(width: 20, height: 20, alignment: .center)
                    .padding()
                    .background(Color.white)
            }
        }
        .buttonStyle(.plain)
        .frame(width: 52, height: 40, alignment: .center)
    }
    
    private func inputField() -> some View {
        HStack {
            InputField(placeholder: "", text: $item.content, edit: $item.edit, commit: { _ in
                item.edit.toggle()
                if item.content.isEmpty {
                    MemoDataSource.default.delete(item: item, in: item.section())
                }
            }, change: nil)
            .foregroundColor(Color.black)
            .padding([.top, .trailing, .bottom])
            Spacer()
        }
    }
    
    private func displayText() -> some View {
        HStack {
            Text(item.content)
                .foregroundColor(Color.gray)
                .strikethrough(item.checked, color: Color.gray)
                .padding([.top, .trailing, .bottom])
            Spacer()
        }
        .background(Color.white) // 有颜色确保整体可响应手势
        .onLongPressGesture {
            item.edit.toggle()
        }
    }
}

struct MemoView_Previews: PreviewProvider {
    static var previews: some View {
        MemoView(item: MemoDataSource.default.sections.first!.items.first!)
    }
}
