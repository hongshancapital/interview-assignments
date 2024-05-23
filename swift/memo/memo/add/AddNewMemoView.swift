//
//  MemoAddNew.swift
//  memo
//
//  Created by LI on 2021/11/15.
//

import SwiftUI

fileprivate class NewMemo: ObservableObject {
    var section: MemoSection? = nil
    let listener = KeyboardListener()
    let backgroundColor = Color(red: 247/255.0, green: 247/255.0, blue: 248/255.0)
    
    /// 当前分组索引
    @Published var index: Int = -1
    
    /// 是否最后一个分组
    @Published var isLastSection: Bool = false
    
    /// 输入的新TODO内容
    @Published var content: String = ""
    /// 输入的新TODO分组名
    @Published var sectionName: String = ""
    
    /// 索引无效
    func indexInvalid() -> Bool {
        index == -1
    }
    
    // 下一组
    func next() {
        let _index = index + 1
        let sections = MemoDataSource.default.sections
        isLastSection = _index == MemoDataSource.default.sections.count
        if _index >= sections.count {
            index = -1
            section = nil
            return
        }
        section = sections[_index]
        // willChange
        index = _index
    }
}

struct AddNewMemoView: View {
    /// 添加新Memo
    @FocusState var adding: Bool
    /// 添加新分组
    @FocusState var section: Bool
    /// 添加时选中的组
    @StateObject private var memo = NewMemo()
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
            ZStack {
                backgroundGradient()
                VStack {
                    Spacer(minLength: 12)
                    memoInputLine()
                    Spacer(minLength: 3)
                    
                    if memo.isLastSection {
                        VStack {
                            Spacer(minLength: 12)
                            sectionInputLine()
                            Spacer(minLength: 3)
                        }
                        .frame(height: 64)
                        .animation(Animation.easeInOut, value: duration())
                    }
                }
            }
            .frame(height: height(), alignment: .bottom)
        }
        .onAppear {
            if memo.indexInvalid() {
                memo.next()
            }
        }
    }
    
    private func height() -> Double {
        memo.isLastSection ? 128 : 64
    }
    
    private func duration() -> Double {
        memo.listener.animationDuration
    }
    
    /// 新TODO输入行
    private func memoInputLine() -> some View {
        HStack(spacing: 0) {
            Spacer(minLength: adding ? 20 : 36)
                .animation(Animation.easeInOut, value: duration())
            ZStack(alignment: .center) {
                RoundedRectangle(cornerRadius: 9)
                    .foregroundColor(Color.white)
                    .padding(.trailing, adding ? 9 : 36)
                    .animation(Animation.easeInOut, value: duration())
                
                if adding == false {
                    RoundedRectangle(cornerRadius: 9)
                        .strokeBorder(style: StrokeStyle(lineWidth: 1, dash: [3]))
                        .lineLimit(2)
                        .foregroundColor(Color.gray)
                        .padding(.trailing, adding ? 9 : 36)
                        .animation(Animation.easeInOut, value: duration())
                }
                
                TextField("Add New...", text: $memo.content, prompt: nil)
                    .frame(height: 36)
                    .focused($adding)
                    .background(Color.white)
                    .padding(.leading, 9)
                    .padding(.trailing, adding ? 12 : 45)
                    .onSubmit(onSubmitInputMemo)
                    .animation(Animation.easeInOut, value: duration())
            }
            if memo.isLastSection == false {    // 分组选择按钮
                chooseSectionButton()
            }
        }
    }
    
    /// 新section输入行
    private func sectionInputLine() -> some View {
        HStack(spacing: 0) {
            Spacer(minLength: section ? 20 : 36)
                .animation(Animation.easeInOut, value: duration())
            ZStack(alignment: .center) {
                RoundedRectangle(cornerRadius: 9)
                    .foregroundColor(Color.white)
                    .padding(.trailing, section ? 9 : 36)
                    .animation(Animation.easeInOut, value: duration())
                
                if section == false {
                    RoundedRectangle(cornerRadius: 9)
                        .strokeBorder(style: StrokeStyle(lineWidth: 1, dash: [3]))
                        .lineLimit(2)
                        .foregroundColor(Color.gray)
                        .padding(.trailing, section ? 9 : 36)
                        .animation(Animation.easeInOut, value: duration())
                }
                
                TextField("Add New Section...", text: $memo.sectionName, prompt: nil)
                    .frame(height: 36)
                    .focused($section)
                    .background(Color.white)
                    .padding(.leading, 9)
                    .padding(.trailing, section ? 12 : 45)
                    .onSubmit(onSubmitInputSection)
                    .animation(Animation.easeInOut, value: duration())
            }
        }
    }
    
    private func onSubmitInputMemo() {
        if (memo.sectionName.isEmpty && memo.content.isEmpty) { // 退出编辑
            section = false
            if memo.isLastSection {
                memo.next()
            }
            return
        }
        
        if memo.content.isEmpty {   // 缩回键盘
            memo.content = ""
            return
        }
        
        if let sec = memo.section {
            MemoDataSource.default.add(memo: memo.content, in: sec)
            memo.content = ""
        }
        else if memo.sectionName.isEmpty == false {
            let section = MemoSection(title: memo.sectionName, items: [])
            MemoDataSource.default.add(memo: memo.content, in: section)
            memo.content = ""
            memo.sectionName = ""
        }
        memo.content = ""
        memo.sectionName = ""
        section = false
        adding = false
        if memo.isLastSection {
            memo.next()
        }
    }
    
    private func onSubmitInputSection() {
        section = false
        if memo.sectionName.isEmpty { // 分组名为空
            if memo.isLastSection {
                memo.next()
            }
            adding = true
            return
        }
        // 有分组名，但没有内容，输入内容
        if memo.content.isEmpty {
            adding = true
            return
        }
        // 有分组名，有内容，提交记录
        let _section = MemoSection(title: memo.sectionName, items: [])
        MemoDataSource.default.add(memo: memo.content, in: _section)
        memo.content = ""
        memo.sectionName = ""
        adding = false
        if memo.isLastSection {
            memo.next()
        }
    }

    /// 选择分组
    private func chooseSectionButton() -> some View {
        Button {
            memo.next()
        } label: {
            HStack(spacing: 3) {
                Spacer(minLength: 3)
                if memo.sectionName.isEmpty {
                    Text(memo.section?.title ?? "")
                        .font(.system(size: 12))
                        .foregroundColor(.black)
                        .multilineTextAlignment(.leading)
                    Image(systemName: "chevron.down")
                        .renderingMode(.original)
                        .foregroundColor(.gray)
                }
            }
        }
        .frame(width: adding ? 90: 0, height: 36, alignment: .center)
        .overlay(
            RoundedRectangle(cornerRadius: 24, style: .circular)
                .stroke(Color.gray, lineWidth: 1)
        )
    }
    
    /// 渐变图层
    private func backgroundGradient() -> some View {
        VStack(spacing: 0) {
            LinearGradient(
                gradient: Gradient(
                    colors: [
                        memo.backgroundColor,
                        memo.backgroundColor.opacity(0.1)
                    ]
                ),
                startPoint: .bottom,
                endPoint: .top
            )
            .frame(height: 24)
            Rectangle()
                .foregroundColor(memo.backgroundColor)
        }
        .ignoresSafeArea()
    }
    
}

/// MARK: Preview
struct AddNewMemoView_Previews: PreviewProvider {
    static var previews: some View {
        AddNewMemoView()
    }
}
